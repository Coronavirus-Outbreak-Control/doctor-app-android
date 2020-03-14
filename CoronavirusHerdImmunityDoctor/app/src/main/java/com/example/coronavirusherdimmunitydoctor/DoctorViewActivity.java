package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;


public class DoctorViewActivity extends Activity {

    private Button bt_scan_qrcode;
    private Button bt_invite_doc;

    private ArrayList<String> contacts_list = new ArrayList<>();

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
             * Click on "Invite new Doctors" button, go to another activity in order to select Contacts
             * and invite them to Server
             **/
            public void onClick(View view)
            {
                Intent intent=new Intent(DoctorViewActivity.this, InviteContactsActivity.class);
                startActivity(intent);
            }
        });

    }
}