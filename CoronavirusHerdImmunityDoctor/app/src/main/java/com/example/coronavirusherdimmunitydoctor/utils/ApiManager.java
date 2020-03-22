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

    private static final String baseEndoint = "https://doctors.api.coronaviruscheck.org/v1";
    private static final MediaType JSONContentType = MediaType.parse("application/json; charset=utf-8");

    /**
     * Method: POST (with Authorization Token)
     *
     * Doctor A sends phone number of doctor B to Server
     *
     * @param phone_number: doctor phone number
     * @param token: authorization token
     * @return JSONObject is the Response to the request:
     *              - 200: ok
     *              - null: if there is an exception
     */
    public static JSONObject inviteDoctor(String phone_number, String token){

        /****** Endpoint *****/
        String endpoint = baseEndoint + "/activation/invite";   //Endpoint: https://doctors.api.coronaviruscheck.org/v1/activation/invite

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’
        String h_auth_token = token;                               //Headers: ‘Authorization: Bearer <Token>’


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
        JSONObject obj = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            Response response = client.newCall(request).execute();            // response
            String strResponse = response.body().string();
            obj = new JSONObject(strResponse);

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on inviteDoctor");
        }
        return obj;
    }


    /**
     * Method: POST
     *
     * Doctor B downloads the app and, at first boot, inserts his/her phone number in order to send it to Server.
     * Server checks if the number exists and is trusted.
     *
     * @param phone_number: Doctor phone number
     * @return JSONObject is the response to the request:
     *              - HTTP 202 Accepted {“token”:”<string:jwt>”}
     *              - HTTP 403 Forbidden
     *              - HTTP 404 Not Found
     *              - null: if there is an exception
     */
    public static JSONObject requestActivation(String phone_number){

        /****** Endpoint *****/
        String endpoint = baseEndoint + "/activation/request";   //Endpoint: https://doctors.api.coronaviruscheck.org/v1/activation/request

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
        JSONObject obj = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            Response response = client.newCall(request).execute();            // response
            String strResponse = response.body().string();
            obj = new JSONObject(strResponse);

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on requestActivation");
        }
        return obj;
    }


    /**
     * Method: GET
     *
     * Doctor B receives 'verification code' by SMS, inserts it and it is sent to Server.
     * Server checks 'verification code' and accepts the invite by giving back User Id and Authorization Token
     *
     * @param verification_code: code received by SMS
     * @param token_jwt: token received by requestActivation
     * @return JSONObject is the response to the request {id: long, token: string } where:
     *             - id of the device of the NEW doctor B
     *             - The token should be sent for every next call to the API as Headers: ‘Authorization: Bearer <Token>’
     *             - null: if there is an exception
     */
    public static JSONObject acceptInvite(String verification_code, String token_jwt){

        /****** Endpoint *****/
        String endpoint =   baseEndoint+
                            "/activation/confirm/"+
                            verification_code + "/" +
                            token_jwt;   //Endpoint: https://doctors.api.coronaviruscheck.org/v1/activation/confirm/<string:received-sms-code>/<string:jwt>>

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’


        OkHttpClient client = new OkHttpClient();

        /* create request to send */
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        /****** Response *******/
        JSONObject obj = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            Response response = client.newCall(request).execute();            // response
            String strResponse = response.body().string();
            obj = new JSONObject(strResponse);

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on acceptInvite");
        }
        return obj;
    }


    /**
     * Method: POST (with Authorization Token)
     *
     * QRcode scanned (User Id) is used to update health patient status where:
     *          - “user-id”: long -> id of the patient, take it from the user QR code or by ID if the device already knows the ID
     *          - “new-status”: int -> new status of the user {0: normal, 1: infected, 2: quarantine, 3: healed, 4: exposed}
     *
     * @param new_status: health patient status {0: normal, 1: infected, 2: quarantine, 3: healed, 4: exposed}
     * @param token: authorization token
     * @return JSONObject is the response to the request:
     *              - 200: ok
     *             - null: if there is an exception
     */
    public static JSONObject updateUserStatus(String user_id, String new_status, String token){

        /****** Endpoint *****/
        String endpoint = baseEndoint +
                          "/mark/" +
                          user_id + "/" +
                          new_status;   //Endpoint: https://doctors.api.coronaviruscheck.org/v1/mark/<long:user-id>/<int:new-status>

        /****** Header *******/
        //String h_contenttype = JSONContentType.toString();       //Headers: ‘Content-Type: application/json’
        String h_auth_token = token;                               //Headers: ‘Authorization: Bearer <Token>’


        OkHttpClient client = new OkHttpClient();

        /* create request to send */
        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", h_auth_token)
                .build();

        /****** Response *******/
        JSONObject obj = null;
        try {
            //Invokes the request immediately, and blocks until the response can be processed or is in error
            Response response = client.newCall(request).execute();            // response
            String strResponse = response.body().string();
            obj = new JSONObject(strResponse);

        }catch(Exception e){
            Log.d("API Rest", "EXCEPTION on updateUserStatus");
        }
        return obj;
    }



}
