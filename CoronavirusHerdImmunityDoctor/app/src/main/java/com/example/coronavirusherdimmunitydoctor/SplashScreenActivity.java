package com.example.coronavirusherdimmunitydoctor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;



public class SplashScreenActivity extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        requestAllPermissions();

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
        final int REQUEST_ID_MULTIPLE_PERMISSIONS  = 1;

        ArrayList<String> listPermissionsNeeded = new ArrayList<>();

        //if camera permission is not granted then request camera permission
        if (ActivityCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        //if contact permission is not granted then request to read contacts permission
        if (ActivityCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
        }

    }
}