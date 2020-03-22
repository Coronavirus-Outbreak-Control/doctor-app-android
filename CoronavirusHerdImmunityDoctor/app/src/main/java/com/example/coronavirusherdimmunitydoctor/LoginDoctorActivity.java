package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronavirusherdimmunitydoctor.utils.ApiManager;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;

import org.json.JSONObject;

import java.util.concurrent.Callable;
import bolts.Continuation;
import bolts.Task;

public class LoginDoctorActivity extends Activity {

    /******************** Global Variables *********************************************/

    private Integer DIGITS_CODE = 4;                                //Number of verification code digits (4)

    private Button bt_send_num;                                     //When is clicked, sends phone number to Server

    private EditText et_phone_number;                               //Edit Text where phone number is inserted

    private TextView tv_write_code;                                 //TextView
    private TableRow tr_code;                                       //View where the code is inserted
    private EditText et_code1;                                      //EditText where inserting 1 digit of verification code
    private EditText et_code2;                                      //EditText where inserting 2 digit of verification code
    private EditText et_code3;                                      //EditText where inserting 3 digit of verification code
    private EditText et_code4;                                      //EditText where inserting 4 digit of verification code

    private String token_jwt;                                       //token jwt received by requestActivation

    /**
     * This TextWatcher manages text changed on EditText:
     * - when a digit is inserted then change to next EditText
     * - when the 4th digit is inserted then change activity
     */
    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch(view.getId())
            {
                case R.id.et_code1:
                    if(text.length()==1)
                        et_code2.requestFocus();
                    break;
                case R.id.et_code2:
                    if(text.length()==1)
                        et_code3.requestFocus();
                    else if(text.length()==0)
                        et_code1.requestFocus();
                    break;
                case R.id.et_code3:
                    if(text.length()==1)
                        et_code4.requestFocus();
                    else if(text.length()==0)
                        et_code2.requestFocus();
                    break;
                case R.id.et_code4:
                    if (text.length()==1) { //change activity when the 4th digit is inserted

                        String verification_code = et_code1.getText().toString() +
                                                   et_code2.getText().toString() +
                                                   et_code3.getText().toString() +
                                                   et_code4.getText().toString();

                        task_acceptInvite(verification_code, token_jwt);  //call acceptInvite
                    }
                    else if(text.length()==0)
                        et_code3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    /**
     * Write 4 digits of verification code on 4 EditText, when a digit is inserted then change to next EditText.
     * When all digits are inserted, then check verification code and go to next Activity
     */
    private void write_verification_code(){
        et_code1.addTextChangedListener(new GenericTextWatcher(et_code1));
        et_code2.addTextChangedListener(new GenericTextWatcher(et_code2));
        et_code3.addTextChangedListener(new GenericTextWatcher(et_code3));
        et_code4.addTextChangedListener(new GenericTextWatcher(et_code4));
    }


    /******************** Activity Functions *********************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_doctor);

        PreferenceManager pm = new PreferenceManager(this);
        if ( !pm.isFirstLogin()){  // if it is not the first login of this doctor go directly to DoctorViewActivity

            Intent intent = new Intent(LoginDoctorActivity.this, DoctorViewActivity.class); //change activity
            startActivity(intent);
            finish();
        }

        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        final String phone_num = et_phone_number.getText().toString();  //get phone number

        tv_write_code = (TextView) findViewById(R.id.tv_write_code);

        tr_code = (TableRow) findViewById(R.id.tr_code);
        et_code1 = (EditText) findViewById(R.id.et_code1);
        et_code2 = (EditText) findViewById(R.id.et_code2);
        et_code3 = (EditText) findViewById(R.id.et_code3);
        et_code4 = (EditText) findViewById(R.id.et_code4);

        bt_send_num = findViewById(R.id.bt_send_num);
        bt_send_num.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * When you insert phone number and click on "send" button
             * then the random code is received by SMS in order to login
             */
            public void onClick(View view)
            {  //when click "send" button, display the view in order to insert random code

                if (et_phone_number.getText().toString().isEmpty()){   //if phone number is not inserted
                    Toast.makeText(getApplicationContext(), R.string.toast_insert_num, Toast.LENGTH_SHORT).show();
                }
                else{
                    token_jwt = new String();
                    task_requestActivation(phone_num); //call requestActivation

                    tv_write_code.setVisibility(View.VISIBLE);
                    tr_code.setVisibility(View.VISIBLE);
                    et_code1.requestFocus();
                    write_verification_code();
                }

            }
        });



    }




    /******************** Task Functions *********************************************/


    /**
     * Run task in order to call acceptInvite API and manage the response
     *
     * @param verification_code
     * @param token_jwt
     */
    private void task_acceptInvite(final String verification_code, final String token_jwt){

        Task.callInBackground(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return ApiManager.acceptInvite(verification_code, token_jwt);  //call acceptInvite
            }
        }).onSuccess(new Continuation<JSONObject, Object>() {
            @Override
            public String then(Task<JSONObject> task) throws Exception {

                JSONObject object = task.getResult();;             //get response of acceptInvite
                if (object != null) {
                    PreferenceManager pm = new PreferenceManager(getApplicationContext());
                    pm.setDoctorId(object.getLong("id"));                //save user(doctor) id in SharedPreferences
                    pm.setAuthorizationToken(object.getString("token")); //save authorization token in SharedPreferences

                    Intent intent = new Intent(LoginDoctorActivity.this, LoginAcceptedActivity.class); //change activity
                    startActivity(intent);
                    finish();

                } else{
                    //
                }
                return null;
            }
        });
    }

    /**
     * Run task in order to call requestActivation API and manage the response
     * @param phone_number
     */
    private void task_requestActivation(final String phone_number){

        Task.callInBackground(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return ApiManager.requestActivation(phone_number);  //call requestActivation
            }
        }).onSuccess(new Continuation<JSONObject, Object>() {
            @Override
            public String then(Task<JSONObject> task) throws Exception {

                JSONObject object = task.getResult();;             //get response of requestActivation
                if (object != null) {
                    switch (object.getInt("code")) {         //check response status(code)
                        case 202: //Accepted
                            token_jwt = object.getString("token");
                            break;
                        case 403: //Forbidden
                            Toast.makeText(LoginDoctorActivity.this, "Number forbidden", Toast.LENGTH_SHORT);
                            break;
                        case 404: //Not Found
                            Toast.makeText(LoginDoctorActivity.this, "Number not found", Toast.LENGTH_SHORT);
                            break;
                        default:
                            break;
                    }
                }
                return null;
            }
        });
    }



}