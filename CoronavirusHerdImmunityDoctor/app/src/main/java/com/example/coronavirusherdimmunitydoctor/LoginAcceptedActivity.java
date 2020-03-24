package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;


public class LoginAcceptedActivity extends Activity {

    private Handler han_logacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_accepted);

        PreferenceManager prefManager = new PreferenceManager(this);
        prefManager.setFirstTimeLaunch(false);
        //ImageView img_checkbox = (ImageView) findViewById(R.id.img_checkbox);

        han_logacc=new Handler();
        han_logacc.postDelayed(new Runnable() {
            /**
             * Show LoginAcceptedActivity for 3 secs, then go to DoctorViewActivity
             */
            @Override
            public void run() {
                Intent intent=new Intent(LoginAcceptedActivity.this, DoctorViewActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}