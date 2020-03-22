package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coronavirusherdimmunitydoctor.utils.ApiManager;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;


public class ChangeStatusActivity extends Activity {

    private Button bt_confirm_covid, bt_suspect_covid, bt_recover_covid, bt_other;
    private ImageButton bt_back;

    final Integer[] states = {0, 1, 2, 3, 4, 5, 6};     // {0: normal,
                                                        //  1: infected,
                                                        //  2: suspect,
                                                        //  3: healed,
                                                        //  4: quarantine_light,
                                                        //  5: quarantine_warning,
                                                        //  6: quarantine_alert}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_patient_status);

        Intent intent = getIntent();
        final Long patient_id = intent.getLongExtra("patient id", -1); //get patient id recognized by QR code scanner

        /****************** Confirm Button *******************/

        bt_confirm_covid = findViewById(R.id.bt_confirm_covid);
        bt_confirm_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Confirm Covid 19" button, change patient's status to 'positive'
             */
            public void onClick(View view)
            {

                Integer new_status = states[1];                        //"infected"

                PreferenceManager pm = new PreferenceManager(ChangeStatusActivity.this);
                task_updateUserStatus(patient_id, new_status, pm.getAuthorizationToken());

            }
        });

        /****************** Suspect Button *******************/

        bt_suspect_covid = findViewById(R.id.bt_suspect_covid);
        bt_suspect_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Suspect Covid-19" button, change patient's status to 'negative'
             */
            public void onClick(View view)
            {
                Integer new_status = states[2];                        //"suspect"

                PreferenceManager pm = new PreferenceManager(ChangeStatusActivity.this);
                task_updateUserStatus(patient_id, new_status, pm.getAuthorizationToken());
            }
        });


        /****************** Recover Button *******************/

        bt_recover_covid = findViewById(R.id.bt_recover_covid);
        bt_recover_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Recover" button, change patient's status to 'negative'
             */
            public void onClick(View view)
            {
                Integer new_status = states[3];                        //"recover"

                PreferenceManager pm = new PreferenceManager(ChangeStatusActivity.this);
                task_updateUserStatus(patient_id, new_status, pm.getAuthorizationToken());
            }
        });

        /****************** Back Button *******************/

        bt_back = findViewById(R.id.bt_back);
        bt_back.setImageResource(R.drawable.left);
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
     * @param token: authorization token
     */
    private void task_updateUserStatus(final Long user_id, final Integer new_status, final String token){

        Task.callInBackground(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return ApiManager.updateUserStatus(user_id, new_status, token);  //call updateUserStatus
            }
        }).onSuccess(new Continuation<JSONObject, Object>() {
            @Override
            public String then(Task<JSONObject> task) throws Exception {

                JSONObject object = task.getResult();;             //get response of updateUserStatus
                if (object != null) {
                    if (object.getInt("code") == 202){      // if response is 'ok'
                        new Handler().post(new Runnable(){
                            public void run(){
                                Toast.makeText(ChangeStatusActivity.this, R.string.toast_status_changed, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else{
                    new Handler().post( new Runnable(){
                        public void run(){
                            Toast.makeText(ChangeStatusActivity.this, "DEBUG CALL UPDATE USER STATUS", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return null;
            }
        });
    }
}