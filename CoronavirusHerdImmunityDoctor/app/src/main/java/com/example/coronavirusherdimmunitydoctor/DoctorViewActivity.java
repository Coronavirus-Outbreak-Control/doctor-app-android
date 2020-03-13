package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DoctorViewActivity extends Activity {

    private Button bt_scan_qrcode;
    private Button bt_invite_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view);

        bt_scan_qrcode = findViewById(R.id.bt_scan_qrcode);
        bt_scan_qrcode.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Scan QR code" button, open camera and try to recognize QR Code
             */
            public void onClick(View view)
            {
                Intent intent=new Intent(DoctorViewActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });

        bt_invite_doc = findViewById(R.id.bt_invite_doc);
        bt_invite_doc.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Invite new Doctors" button, open Contacts and select phone number of Doctors
             **/
            public void onClick(View view)
            {
                Intent intent=new Intent(DoctorViewActivity.this, ChangeStatusActivity.class);
                startActivity(intent);
            }
        });

    }
}