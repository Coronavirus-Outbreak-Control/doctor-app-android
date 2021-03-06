package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.Toast;

import com.example.coronavirusherdimmunitydoctor.enums.PatientStatus;
import com.example.coronavirusherdimmunitydoctor.utils.ApiManager;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import okhttp3.Response;


public class ChangeStatusActivity extends Activity {

    private Button bt_confirm_covid, bt_negative_covid, bt_recover_covid;
    private FloatingActionButton bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_patient_status);

        Intent intent = getIntent();
        final long patient_id = intent.getLongExtra("patient id", -1); //get patient id recognized by QR code scanner

        /****************** Confirm Button *******************/

        bt_confirm_covid = findViewById(R.id.bt_confirm_covid);
        bt_confirm_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Positive to Covid 19" button, change patient's status to 'infected'
             */
            public void onClick(View view)
            {

                Integer new_status = PatientStatus.valueOf("INFECTED").ordinal();       //"infected"

                task_updateUserStatus(patient_id, new_status);

            }
        });

        /****************** Negative Button *******************/

        bt_negative_covid = findViewById(R.id.bt_negative_covid);
        bt_negative_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Negative to Covid-19" button, change patient's status to 'negative'
             */
            public void onClick(View view)
            {
                Integer new_status = PatientStatus.valueOf("NEGATIVE").ordinal();      //"negative"

                task_updateUserStatus(patient_id, new_status);
            }
        });


        /****************** Recover Button *******************/

        bt_recover_covid = findViewById(R.id.bt_recover_covid);
        bt_recover_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Healed" button, change patient's status to 'healed'
             */
            public void onClick(View view)
            {
                Integer new_status = PatientStatus.valueOf("HEALED").ordinal();        //"healed"

                task_updateUserStatus(patient_id, new_status);
            }
        });

        /****************** Back Button *******************/

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Other" button
             */
            public void onClick(View view)
            {
                Intent intent=new Intent(ChangeStatusActivity.this, DoctorViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    /******************** Task Functions *********************************************/


    /**
     * Run task in order to call updateUserStatus API and manage the response
     *
     * @param user_id: patient id
     * @param new_status: health patient status
     */
    private void task_updateUserStatus(final long user_id, final Integer new_status){

        Toast.makeText(getApplicationContext(), R.string.toast_click_status_changed, Toast.LENGTH_SHORT).show();

        Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {

                PreferenceManager pm = new PreferenceManager(getApplicationContext());
                Boolean updated =  false;
                String ret_value = "";

                while ( updated == false){

                    Response response_updateUS = ApiManager.updateUserStatus(user_id, new_status, pm.getJwtToken());  //call updateUserStatus

                    if (response_updateUS != null) {
                        switch (response_updateUS.code()) {//check response status(code)
                            case 200:     // if response is 'ok' -> status has been changed
                                Log.d("task_updateUserStatus","status has been changed");
                                ret_value = "chg_st";
                                updated = true;
                                break;
                            case 403:     // if jwt token is not sent -> call refreshJwtToken and recall task_updateUserStatus
                            case 401:     // if jwt token is expired -> call refreshJwtToken and recall task_updateUserStatus
                                Log.d("task_updateUserStatus","Jwt Token expired");

                                Response response_refreshJwtToken = ApiManager.refreshJwtToken(pm.getAuthorizationToken());  //call refreshJwtToken
                                if (response_refreshJwtToken != null &&
                                        response_refreshJwtToken.code() == 200){ //check response status(code)

                                    try{
                                        String strResponse_body = response_refreshJwtToken.body().string();  //get body of Response
                                        JSONObject response_body = new JSONObject(strResponse_body);
                                        pm.setJwtToken(response_body.getString("token"));              //save new Jwt Token in shared preferences

                                    }catch (Exception e){
                                        Log.d("task_updateUserStatus", "Error to read jwt token received");
                                    }
                                }
                                break;
                            default:       // for example, if patient id is not recognized
                                Log.d("task_updateUserStatus", "Code not recognized:"+response_updateUS.code());
                                ret_value = "not_rec";
                                updated = true;
                                break;
                        }
                    } else{  // no response from Backend (like: internet disabled)
                        Log.d("task_updateUserStatus","No response by updateUserStatus");
                        ret_value = "no_resp";
                        updated = true;

                    }
                }

                return ret_value;
            }
        }).onSuccess(new Continuation<String, Object>() {
            @Override
            public String then(Task<String> task) throws Exception {

                switch (task.getResult()) {
                    case "chg_st":  //healths status changed
                        Toast.makeText(getApplicationContext(), R.string.toast_status_changed, Toast.LENGTH_SHORT).show();
                        break;
                    case "not_rec": //patient id (QR code) not recognized
                        Toast.makeText(getApplicationContext(), R.string.toast_code_not_recognized, Toast.LENGTH_LONG).show();
                        break;
                    case "no_resp": // no response from Backend (like: internet disabled)
                        Toast.makeText(getApplicationContext(), R.string.toast_no_resp_change_status, Toast.LENGTH_LONG).show();
                        break;
                    default: //some errors
                        Toast.makeText(getApplicationContext(), R.string.toast_err_status_change, Toast.LENGTH_LONG).show();
                        break;
                }
                return null;
            }
        },  Task.UI_THREAD_EXECUTOR);
    }



    /* Manage back button when is pressed in order to go to DoctorViewActivity */
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), DoctorViewActivity.class);
        startActivity(intent);
        finish();
    }
}