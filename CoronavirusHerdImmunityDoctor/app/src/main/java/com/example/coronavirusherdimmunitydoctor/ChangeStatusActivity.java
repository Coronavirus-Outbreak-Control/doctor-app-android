package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class ChangeStatusActivity extends Activity {

    private Button bt_confirm_covid;
    private Button bt_recover_covid;
    private Button bt_other;
    private ImageButton bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_patient_status);

        Intent intent = getIntent();
        String patient_code = intent.getStringExtra("patient code"); //get patient code recognized by QR code scanner

        bt_confirm_covid = findViewById(R.id.bt_confirm_covid);
        bt_confirm_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Confirm Covid 19" button, change patient's status to 'positive'
             */
            public void onClick(View view)
            {
                Intent intent=new Intent(ChangeStatusActivity.this, DoctorViewActivity.class);
                startActivity(intent);
            }
        });

        bt_recover_covid = findViewById(R.id.bt_recover_covid);
        bt_recover_covid.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Recover" button, change patient's status to 'negative'
             */
            public void onClick(View view)
            {
                Intent intent=new Intent(ChangeStatusActivity.this, DoctorViewActivity.class);
                startActivity(intent);
            }
        });

        bt_other = findViewById(R.id.bt_other_status);
        bt_other.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Other" button
             */
            public void onClick(View view)
            {
                Intent intent=new Intent(ChangeStatusActivity.this, DoctorViewActivity.class);
                startActivity(intent);
            }
        });

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
            }
        });
    }
}