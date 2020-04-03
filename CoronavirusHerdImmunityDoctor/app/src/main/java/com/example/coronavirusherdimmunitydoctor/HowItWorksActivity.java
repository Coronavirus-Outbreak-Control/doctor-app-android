package com.example.coronavirusherdimmunitydoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.coronavirusherdimmunitydoctor.introduction.CameraPermissionActivity;
import com.example.coronavirusherdimmunitydoctor.introduction.WelcomeActivity;

public class HowItWorksActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.how_it_works);

        Button back_button = findViewById(R.id.button_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /* Manage back button when is pressed in order to go to WelcomeActivity */
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
