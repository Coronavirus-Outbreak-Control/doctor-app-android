package com.example.coronavirusherdimmunitydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronavirusherdimmunitydoctor.introduction.WelcomeActivity;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;


public class SplashScreenActivity extends AppCompatActivity {

    private PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_splashscreen);

        launchNextActivity();  // launch introduction activity

    }

    /**
     *  if it is the first time launch then go to welcome and permission activity
     *  else go to Main Activity
    * */
    private void launchNextActivity() {

        prefManager = new PreferenceManager(this);
        if (!prefManager.isFirstTimeLaunch()) {  //if it is not first time launch go to main activity after 2 secs

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, LoginDoctorActivity.class));
                    finish();
                }
            },3000);

        }else{ //if it is first time launch then change status of FirstTimeLaunch and go to Welcome activity

            prefManager.setFirstTimeLaunch(false);  //it is not the first time launch

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
                    finish();
                }
            },3000);
        }

    }
}