package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.coronavirusherdimmunitydoctor.utils.PermissionRequest;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;

import java.util.ArrayList;


public class DoctorViewActivity extends Activity {

    private Button bt_scan_qrcode;
    private Button bt_invite_doc;

    private ArrayList<String> contacts_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                finish();
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
                finish();
            }
        });
        

        PermissionRequest permissions = new PermissionRequest(DoctorViewActivity.this);
        permissions.checkPermissions(); //check if camera and read_contacts are enabled else go to activity in order to enable them

    }



    /* Manage back button when is pressed in order to exit from application*/
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setCancelable(false);
        builder.setTitle(R.string.alert_exit_title);
        builder.setMessage(R.string.alert_exit_msg);
        builder.setPositiveButton(R.string.alert_exit_pos_bt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.alert_exit_neg_bt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.show();
    }
}