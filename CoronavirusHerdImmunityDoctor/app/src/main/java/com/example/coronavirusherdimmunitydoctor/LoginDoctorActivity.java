package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;


public class LoginDoctorActivity extends Activity {

    private Button bt_send_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);

        EditText et_phone_number = (EditText) findViewById(R.id.et_phone_number); //Edit Text where phone number is inserted
        String phone_num = et_phone_number.getText().toString();  //get phone number

        final TableRow tr_code = (TableRow) findViewById(R.id.tr_code); //View where the code is inserted

        final TextView tv_write_code = (TextView) findViewById(R.id.tv_write_code);

        bt_send_num = findViewById(R.id.bt_send_num);
        bt_send_num.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * When you insert phone number and click on "send" button
             * then the random code is received by SMS in order to login
             */
            public void onClick(View view)
            {  //when click "send" button, display the view in order to insert random code
                tv_write_code.setVisibility(View.VISIBLE);
                tr_code.setVisibility(View.VISIBLE);
            }
        });

        /*  GESTIONE DI INSERIMENTO UNO AD UNO DI UN NUMERO NELLE EDIT TEXT DELLA TABLE ROW
        * QUANDO HAI INSERITO TUTTI I NUMERI ALLORA CAMBIA ACTIVITY*/


    }
}