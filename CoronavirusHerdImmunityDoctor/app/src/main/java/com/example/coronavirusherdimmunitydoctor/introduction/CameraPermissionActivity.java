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

import com.example.coronavirusherdimmunitydoctor.R;

public class CameraPermissionActivity extends AppCompatActivity {

    private final int REQUEST_ID_PERMISSION_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.intro1_camerapermission);

        Button button_next;

        button_next = findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestCameraPermission(); //requires camera permissions, if is granted then go to next activity

            }
        });

    }

    /**
     * Require Camera permission, go to next activity
     */
    private void requestCameraPermission() {

        //if camera permission is not granted then require camera permission
        if (ActivityCompat.checkSelfPermission(CameraPermissionActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_ID_PERMISSION_CAMERA);
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
            case REQUEST_ID_PERMISSION_CAMERA: {

                // if permission was granted then go to next Activity
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    startActivity(new Intent(CameraPermissionActivity.this, ContactsPermissionActivity.class)); //go to ContactPermissionActivity
                    finish();

                } else {    // permission denied, then requires permissions again

                    requestCameraPermission(); //requires permissions again
                }
            }
        }
    }

}

