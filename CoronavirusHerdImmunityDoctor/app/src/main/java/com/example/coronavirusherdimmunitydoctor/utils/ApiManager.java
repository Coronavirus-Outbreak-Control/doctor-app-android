package com.example.coronavirusherdimmunitydoctor.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// https://www.javatpoint.com/java-json-example
// https://square.github.io/okhttp/

public class ApiManager {

    private static final String baseEndoint = "https://doctors.coronaviruscheck.org/v1";
    private static final MediaType JSONContentType = MediaType.parse("application/json; charset=utf-8");


    /**
     * Method: POST
     *
     * It is used to get Jwt Token from Authorized Token in order to refresh Jwt Token when is expired.
     * This request must be performed before each invitation and before to mark a patient as infected to get a timed jwt.
     *
     * @param authorization_token: authorization token is used to get jwt token
     * @return JSONObject is the Response to the request:
     *              - HTTP 200: ok {“token”:”<string:jwt>”}, where 'jwt token' is used to authenticated function
     */
    public static Response refreshJwtToken(String authorization_token){
        /****** Endpoint *****/
        String endpoint = baseEndoint + "/authenticate";   //Endpoint: https://doctors.coronaviruscheck.org/v1/authenticate

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’
        String h_auth_token = " Bearer " + authorization_token;    //Headers: ‘Authorization: Bearer <ReAuthToken>’

        /****** Body ********/
        Map body = new HashMap();  //body is empty

        OkHttpClient client = new OkHttpClient();

        /* create request to send */
        RequestBody rq = RequestBody.create(JSONContentType, JSONValue.toJSONString(body));
        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", h_auth_token)
                .post(rq)
                .build();

        /****** Response *******/
        Response response = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            response = client.newCall(request).execute();            // response

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on refreshJwtToken");
        }
        return response;
    }


    /**
     * Method: POST (Authorized Function)
     *
     * Doctor A sends phone number of doctor B to Server
     *
     * @param phone_number: doctor phone number to invite
     * @param jwt_token: jwt token to authorize the function
     * @return JSONObject is the Response to the request:
     *              - 200: ok
     */
    public static Response inviteDoctor(String phone_number, String jwt_token){

        /****** Endpoint *****/
        String endpoint = baseEndoint + "/activation/invite";   //Endpoint: https://doctors.coronaviruscheck.org/v1/activation/invite

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’
        String h_auth_token = " Bearer " + jwt_token;              //Headers: ‘Authorization: Bearer <JWT>’

        /****** Body ********/
        Map body = new HashMap();
        body.put("phone_number", phone_number);                    // “phone-number”: string -> phone number of the Doctor B

        OkHttpClient client = new OkHttpClient();

        /* create request to send */
        RequestBody rq = RequestBody.create(JSONContentType, JSONValue.toJSONString(body));
        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", h_auth_token)
                .post(rq)
                .build();

        /****** Response *******/
        Response response = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            response = client.newCall(request).execute();            // response

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on inviteDoctor");
        }
        return response;
    }


    /**
     * Method: POST
     *
     * Doctor B downloads the app and, at first boot, inserts his/her phone number in order to send it to Server.
     * Server checks if the number exists and is trusted.
     *
     * @param phone_number: Doctor phone number
     * @return JSONObject is the response to the request:
     *              - HTTP 202 Accepted
     *              - HTTP 502 Error Phone Number -> Number not trusted
     *              - HTTP 404 Not Found
     */
    public static Response requestActivation(String phone_number){

        /****** Endpoint *****/
        String endpoint = baseEndoint + "/activation/request";   //Endpoint: https://doctors.coronaviruscheck.org/v1/activation/request

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’

        /****** Body ********/
        Map body = new HashMap();
        body.put("phone_number", phone_number);                    // “phone_number” -> string  (the phone number of Doctor B)

        OkHttpClient client = new OkHttpClient();

        /* create request to send */
        RequestBody rq = RequestBody.create(JSONContentType, JSONValue.toJSONString(body));
        Request request = new Request.Builder()
                .url(endpoint)
                .post(rq)
                .build();

        /****** Response *******/
        Response response = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
             response = client.newCall(request).execute();            // response

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on requestActivation");
        }
        return response;
    }


    /**
     * Method: GET
     *
     * Doctor B receives 'verification code' by SMS, inserts it and it is sent to Server.
     * Server checks 'verification code' and accepts the invite by giving back User Id and Authorization Token
     *
     * @param verification_code: code received by SMS
     * @return JSONObject is the response to the request {id: long, token: string } where:
     *         - 200: ok + body: {id: long, token: string }:
     *                  - id of the device of the NEW doctor B
     *                  - The ReAuthToken is used to get JWT Token with refreshJwtToken function
     */
    public static Response acceptInvite(String verification_code){

        /****** Endpoint *****/
        String endpoint =   baseEndoint+
                            "/activation/confirm/"+
                            verification_code;   //Endpoint: https://doctors.coronaviruscheck.org/v1/activation/confirm/<string:received-sms-code>

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’

        OkHttpClient client = new OkHttpClient();

        /* create request to send */
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        /****** Response *******/
        Response response = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            response = client.newCall(request).execute();            // response

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on acceptInvite");
        }
        return response;
    }


    /**
     * Method: POST (Authorized Function)
     *
     * QRcode scanned (User Id) is used to update health patient status where:
     *    - “user-id”: long -> id of the patient, take it from the user QR code or by ID if the device already knows the ID
     *    - “new-status”: int -> new status of the user {0: normal, 1: infected, 2:suspect, 3: healed, 4: quarantine_light, 5: quarantine_warning, 6:quarantine_alert}
     *
     * @param user_id: doctor id
     * @param new_status: health patient status {0: normal, 1: infected, 2: quarantine, 3: healed, 4: exposed}
     * @param jwt_token: jwt token to authorize the function
     * @return JSONObject is the response to the request:
     *              - 200: ok
     */
    public static Response updateUserStatus(Long user_id, Integer new_status, String jwt_token){

        /****** Endpoint *****/
        String endpoint = baseEndoint +
                          "/mark/" +
                          user_id.toString() + "/" +
                          new_status.toString();   //Endpoint: https://doctors.coronaviruscheck.org/v1/mark/<long:user-id>/<int:new-status>

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’
        String h_auth_token = " Bearer " + jwt_token;              //Headers: ‘Authorization: Bearer <JWT>’

        /****** Body ********/
        Map body = new HashMap();  //body is empty

        OkHttpClient client = new OkHttpClient();

        /* create request to send */
        RequestBody rq = RequestBody.create(JSONContentType, JSONValue.toJSONString(body));
        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", h_auth_token)
                .post(rq)
                .build();

        /****** Response *******/
        Response response = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            response = client.newCall(request).execute();            // response

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on updateUserStatus");
        }
        return response;
    }

}
