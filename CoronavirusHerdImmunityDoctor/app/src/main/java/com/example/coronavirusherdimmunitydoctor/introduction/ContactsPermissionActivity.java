package com.example.coronavirusherdimmunitydoctor.introduction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.coronavirusherdimmunitydoctor.LoginAcceptedActivity;
import com.example.coronavirusherdimmunitydoctor.LoginDoctorActivity;
import com.example.coronavirusherdimmunitydoctor.R;


public class ContactsPermissionActivity extends AppCompatActivity {

    private final int REQUEST_ID_PERMISSION_CONTACT = 2;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.intro2_contactspermission);

        Button button_next, button_skip;

        bundle = getIntent().getExtras(); //Retrieves data from the intent


        button_next = findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestContactsPermission();  //require read_contacts permission, if it is granted then go to next activity

            }
        });

        button_skip = findViewById(R.id.button_skip);
        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                go_nextActivity();

            }
        });

    }


    /**
     * if this activity has been re-called in order to enable permission then go to LoginDoctorActivity
     * else if this activity has been called for the first time then go LoginDoctorActivity
     */
    private void go_nextActivity(){

        if ( bundle != null &&
                bundle.getBoolean("permission_request")){ // if this activity has been recalled then go to LoginDoctorActivity

            startActivity(new Intent(this, LoginDoctorActivity.class));
            finish();

        }else{ //if this activity has been called for the first time then go to ContactPermissionActivity

            startActivity(new Intent(this, LoginDoctorActivity.class));
            finish();
        }

    }

    /**
     * Require Read_Contacts permission, go to next activity
     */
    private void requestContactsPermission() {

        //if read_contact permission is not granted then require permission
        if (ActivityCompat.checkSelfPermission(ContactsPermissionActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_ID_PERMISSION_CONTACT);

        }else{ //if read_contact permission is granted

            go_nextActivity();
        }
    }

    /**
     * When the user responds to your app's permission request, the system invokes this function.
     * This function check if the permissions are granted or not.
     * If they are granted then go to next activity otherwise requires permissions again
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_PERMISSION_CONTACT: {

                // if permission was granted then go to next Activity
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    go_nextActivity();

                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() { } // disable back button
}
