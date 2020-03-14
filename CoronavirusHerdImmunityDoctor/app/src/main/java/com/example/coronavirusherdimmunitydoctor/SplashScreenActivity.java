package com.example.coronavirusherdimmunitydoctor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;



public class SplashScreenActivity extends Activity {

    private final int REQUEST_ID_MULTIPLE_PERMISSIONS  = 1;       // READ_CONTACTS, CAMERA
    private int lenght_listPermissionsNeeded = 0;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        requestAllPermissions();
    }

    /**
     * Start the handler in order to open Welcome Activity after 3 seconds
     */
    private void startHandler(){
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    /**
     * Request all permissions required by App
     */
    private void requestAllPermissions(){

        ArrayList<String> listPermissionsNeeded = new ArrayList<>();

        //if camera permission is not granted then request camera permission
        if (ActivityCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        //if contact permission is not granted then request to read contacts permission
        if (ActivityCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }

        lenght_listPermissionsNeeded = listPermissionsNeeded.size();
        //if the permission list is not empty then requires the permissions
        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);

        } else{ //if the permission list is empty then permissions were granted, therefore go to next Activity after 3 secs

            startHandler();

        }

    }

    /*+
    * If every permission is granted  by user then return true,
    * else return false
    */
    private boolean check_permission_granted(int[] grantResults){
        for (int i = 0; i < lenght_listPermissionsNeeded; i++ ) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
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
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                // if permission was granted then start Handler in order to go next Activity after 3 secs
                if (grantResults.length > 0 &&
                        check_permission_granted(grantResults)) {

                    startHandler();

                } else {    // permission denied, then requires permissions again

                    requestAllPermissions(); //requires permissions again
                }
                return;
            }
        }
    }
}