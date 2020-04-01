package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronavirusherdimmunitydoctor.utils.ApiManager;
import com.example.coronavirusherdimmunitydoctor.utils.CountryCodeSpinner;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;

import org.json.JSONObject;

import java.util.concurrent.Callable;
import bolts.Continuation;
import bolts.Task;
import okhttp3.Response;

public class LoginDoctorActivity extends Activity {

    /******************** Global Variables *********************************************/

    private Integer DIGITS_CODE = 4;                                //Number of verification code digits (4)

    private Button bt_send_num;                                     //When is clicked, sends phone number to Server

    private EditText et_phone_number;                               //Edit Text where phone number is inserted
    private CountryCodeSpinner ccs;                                 //Spinner where there is a list of number prefix

    private RelativeLayout progBar;                                 //Relative Layout (ProgressBar + TextView)
    private TextView tv_write_code;                                 //TextView
    private TableRow tr_code;                                       //View where the code is inserted
    private EditText et_code1;                                      //EditText where inserting 1 digit of verification code
    private EditText et_code2;                                      //EditText where inserting 2 digit of verification code
    private EditText et_code3;                                      //EditText where inserting 3 digit of verification code
    private EditText et_code4;                                      //EditText where inserting 4 digit of verification code
    private EditText et_code5;                                      //EditText where inserting 5 digit of verification code
    private EditText et_code6;                                      //EditText where inserting 6 digit of verification code

    private String phone_num;                                       //phone number (without prefix)
    private String prefix_num;                                      //number prefix
    private String prefix_phone_num;                                //phone number with number prefix


    /**
     * This TextWatcher manages text changed on EditText:
     * - when a digit is inserted then change to next EditText
     * - when the 6th digit is inserted then change activity
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
                    if(text.length()==1)
                        et_code5.requestFocus();
                    else if(text.length()==0)
                        et_code3.requestFocus();
                    break;
                case R.id.et_code5:
                    if(text.length()==1)
                        et_code6.requestFocus();
                    else if(text.length()==0)
                        et_code4.requestFocus();
                    break;
                case R.id.et_code6:
                    if (text.length()==1) { //change activity when the 6th digit is inserted

                        String verification_code = et_code1.getText().toString() +
                                                   et_code2.getText().toString() +
                                                   et_code3.getText().toString() +
                                                   et_code4.getText().toString() +
                                                   et_code5.getText().toString() +
                                                   et_code6.getText().toString();

                        PreferenceManager pm = new PreferenceManager(getApplicationContext());
                        task_acceptInvite(verification_code, prefix_phone_num);  //call acceptInvite
                    }
                    else if(text.length()==0)
                        et_code5.requestFocus();
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
     * Write 6 digits of verification code on 4 EditText, when a digit is inserted then change to next EditText.
     * When all digits are inserted, then check verification code and go to next Activity
     */
    private void write_verification_code(){
        et_code1.addTextChangedListener(new GenericTextWatcher(et_code1));
        et_code2.addTextChangedListener(new GenericTextWatcher(et_code2));
        et_code3.addTextChangedListener(new GenericTextWatcher(et_code3));
        et_code4.addTextChangedListener(new GenericTextWatcher(et_code4));
        et_code5.addTextChangedListener(new GenericTextWatcher(et_code5));
        et_code6.addTextChangedListener(new GenericTextWatcher(et_code6));
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

        tv_write_code = (TextView) findViewById(R.id.tv_write_code);

        tr_code = (TableRow) findViewById(R.id.tr_code);
        et_code1 = (EditText) findViewById(R.id.et_code1);
        et_code2 = (EditText) findViewById(R.id.et_code2);
        et_code3 = (EditText) findViewById(R.id.et_code3);
        et_code4 = (EditText) findViewById(R.id.et_code4);
        et_code5 = (EditText) findViewById(R.id.et_code5);
        et_code6 = (EditText) findViewById(R.id.et_code6);

        progBar = (RelativeLayout) findViewById(R.id.rel_progbar);
        progBar.setVisibility(View.GONE);  //set invisible the relative layout (progress bar + text view)

        ccs = findViewById(R.id.ccs);
        ccs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryCodeSpinner.CountryModel model = (CountryCodeSpinner.CountryModel) parent.getAdapter().getItem(position);
                prefix_num = model.getPhoneCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                    phone_num = et_phone_number.getText().toString();  //get phone number
                    prefix_phone_num = prefix_num+phone_num;
                    task_requestActivation(prefix_phone_num); //call requestActivation

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
     * Run task in order to call requestActivation API and manage the response
     * @param phone_number
     */
    private void task_requestActivation(final String phone_number){

        Task.callInBackground(new Callable<Response>() {
            @Override
            public Response call() throws Exception {

                Response response = ApiManager.requestActivation(phone_number);  //call requestActivation

                if (response != null) {
                    switch (response.code()) {         //check response status(code)
                        case 202: //if response is 'ok' -> Number Accepted
                            Log.d("task_requestActivation", "Phone Number accepted, please insert verification code");
                            break;
                        case 502: //Error Phone Number -> Not trusted
                            Log.d("task_requestActivation", "Phone Number not accepted, please insert a number again");
                            break;
                        case 404: //Not Found
                            Log.d("task_requestActivation", "Phone Number not found, please insert a number again");
                            break;
                        default:
                            Log.d("task_requestActivation", "Code not recognized:"+response.code());
                            break;
                    }
                }else{
                    Log.d("task_requestActivation", "No response by requestActivation");

                }

                return null;
            }
        }).onSuccess(new Continuation<Response, Object>() {
            @Override
            public String then(Task<Response> task) throws Exception {
                return null;
            }
        },  Task.UI_THREAD_EXECUTOR);
    }


    /**
     * Run task in order to call acceptInvite API and manage the response
     *
     * @param verification_code
     * @param doc_phone_num: phone number of the doctor that tries to login
     */
    private void task_acceptInvite(final String verification_code, final String doc_phone_num){

        progBar.setVisibility(View.VISIBLE);  //set visible the relative layout (progress bar + text view)

        Task.callInBackground(new Callable<Response>() {
            @Override
            public Response call() throws Exception {

                Response response_acceptInvite = ApiManager.acceptInvite(verification_code);  //call acceptInvite
                Response response_refreshjwtToken = null;
                PreferenceManager pm = new PreferenceManager(getApplicationContext());

                if (response_acceptInvite != null) {
                    switch (response_acceptInvite.code()){  //check response status(code)
                        case 200:      //Verification code is accepted
                            try {
                                Log.d("task_acceptInvite", "Verification code is accepted");

                                String strResponse_body = response_acceptInvite.body().string();      //get body of Response
                                JSONObject response_body = new JSONObject(strResponse_body);

                                String auth_token = response_body.getString("token");           //get Authorization Token from response of acceptInvite
                                Long doc_id = response_body.getLong("id");                      //get Doctor Id from response of acceptInvite
                                pm.setDoctorId(doc_id);                                               //save user(doctor) id in SharedPreferences
                                pm.setAuthorizationToken(auth_token);                                 //save authorization token in SharedPreferences
                                pm.setPhoneNumber(doc_phone_num);                                     //save phone number of doctor in SharedPreferences

                                response_refreshjwtToken = ApiManager.refreshJwtToken(auth_token);    //call refreshJwtToken in order to return a Jwt Token from authorization token

                            }catch (Exception e){
                                Log.d("task_acceptInvite", "Error to read response body");
                            }
                            break;
                        case 401:      //verification code is expired
                            Log.d("task_acceptInvite", "Verification code expired");
                            break;
                        case 404:     //Authorization token has already been requested
                            /*PER DEBUG: following 3 rows are used to try login many times with the same phone number*/
                            //pm.setDoctorId(Long.valueOf(2));                                               // PER DEBUG: save user(doctor) id in SharedPreferences
                            //pm.setAuthorizationToken("d4967209a8faf0ad1805ab5e32ef73e2efc6567aa295c7bc66245027ccf59ad3");  // PER DEBUG: save authorization token in SharedPreferences
                            //response_refreshjwtToken = ApiManager.refreshJwtToken(pm.getAuthorizationToken());    // PER DEBUG: call refreshJwtToken in order to return a Jwt Token from authorization token
                            Log.d("task_acceptInvite", "Authorization token has already been requested");
                            break;
                        default:
                            Log.d("task_acceptInvite", "Code not recognized:"+response_acceptInvite.code());
                            break;
                    }
                } else{
                    Log.d("task_acceptInvite", "No response by acceptInvite");
                }
                return response_refreshjwtToken;

            }

        }).onSuccess(new Continuation<Response, Object>() {
            @Override
            public String then(Task<Response> task) throws Exception {

                Response response_refreshjwtToken = task.getResult();                              //get response of refreshJwtToken
                if (response_refreshjwtToken != null) {
                    switch (response_refreshjwtToken.code()){                                       //check response status(code)
                        case 200:  //if response is 'ok' -> save JwtToken in shared preferences and change activity
                            try{
                                Log.d("task_acceptInvite", "Jwt Token obtained");
                                PreferenceManager pm = new PreferenceManager(getApplicationContext());
                                String strResponse_body = response_refreshjwtToken.body().string();     //get body of Response
                                JSONObject response_body = new JSONObject(strResponse_body);

                                pm.setJwtToken(response_body.getString("token"));                 //save Jwt Token in SharedPreferences

                                progBar.setVisibility(View.GONE);  //set invisible the relative layout (progress bar + text view)

                                Intent intent = new Intent(LoginDoctorActivity.this, LoginAcceptedActivity.class);  //change activity
                                startActivity(intent);
                                finish();

                            }catch (Exception e){
                                Log.d("task_acceptInvite", "Error to read jwt token received");
                            }

                            break;
                        default:
                            Log.d("task_acceptInvite","Code not recognized:"+response_refreshjwtToken.code());
                            break;
                    }
                } else{
                    Log.d("task_acceptInvite","No response by refreshJwtToken");
                }

                return null;
            }
        },  Task.UI_THREAD_EXECUTOR);
    }


}