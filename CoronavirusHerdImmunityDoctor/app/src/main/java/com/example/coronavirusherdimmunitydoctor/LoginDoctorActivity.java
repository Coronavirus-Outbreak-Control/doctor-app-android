package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


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
    /*
    private EditText [] et_code_array = new EditText[DIGITS_CODE];  //Array of EditText which contains 4 EditText
    private KeyListener [] kl_code_array = new KeyListener[DIGITS_CODE]; //Array of KeyListener
    private Integer index_et_code_array = 0;                        //Index of EditText Array (et_code_array)

    private TextWatcher TextWatcher_code = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           et_code1.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };
    */

    /******************** Activity Functions *********************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);

        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        String phone_num = et_phone_number.getText().toString();  //get phone number

        tv_write_code = (TextView) findViewById(R.id.tv_write_code);

        tr_code = (TableRow) findViewById(R.id.tr_code);
        et_code1 = (EditText) findViewById(R.id.et_code1);
        et_code2 = (EditText) findViewById(R.id.et_code2);
        et_code3 = (EditText) findViewById(R.id.et_code3);
        et_code4 = (EditText) findViewById(R.id.et_code4);
        //et_code_array = new EditText[]{et_code1, et_code2, et_code3, et_code4};
        //kl_code_array = new KeyListener[]{et_code1.getKeyListener(), et_code2.getKeyListener(), et_code3.getKeyListener(), et_code4.getKeyListener()};

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
                    Toast.makeText(getApplicationContext(), "Insert number", Toast.LENGTH_SHORT).show();
                }
                else{
                    tv_write_code.setVisibility(View.VISIBLE);
                    tr_code.setVisibility(View.VISIBLE);

                    write_verification_code();

                    Intent intent=new Intent(LoginDoctorActivity.this, LoginAcceptedActivity.class);
                    startActivity(intent);
                }

            }
        });



    }

    /**
     * Write 4 digits of verification code on 4 EditText, when a digit is inserted then change to next EditText.
     * When all digits are inserted, then check verification code and go to next Activity
     */
    private void write_verification_code(){
        //et_code1.addTextChangedListener(TextWatcher_code);
        //et_code2.addTextChangedListener(TextWatcher_code);
        //et_code3.addTextChangedListener(TextWatcher_code);
        //et_code4.addTextChangedListener(TextWatcher_code);
    }
}