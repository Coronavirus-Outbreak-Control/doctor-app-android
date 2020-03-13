package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class LoginAcceptedActivity extends Activity {

    private Handler han_logacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_accepted);

        ImageView img_checkbox = (ImageView) findViewById(R.id.img_checkbox);
        img_checkbox.setImageResource(R.drawable.checkbox);

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