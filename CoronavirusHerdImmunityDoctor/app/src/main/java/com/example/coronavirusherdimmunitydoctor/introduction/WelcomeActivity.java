package com.example.coronavirusherdimmunitydoctor.introduction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coronavirusherdimmunitydoctor.R;

import com.example.coronavirusherdimmunitydoctor.HowItWorksActivity;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.intro0_welcome);


        this.writeTitle();


        Button start_button = (Button) findViewById(R.id.button_next);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, CameraPermissionActivity.class));
                finish();
            }
        });

        Button how_it_works_button = (Button) findViewById(R.id.how_it_works);
        how_it_works_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, HowItWorksActivity.class));
                finish();
            }
        });

    }



    private void writeTitle() {
        String first = getResources().getString(R.string.welcome_first);
        String blue = getResources().getString(R.string.welcome_next);
        String last = getResources().getString(R.string.welcome_last);
        TextView t = (TextView) findViewById(R.id.welcome_to);

        t.setText(Html.fromHtml(first +
                "<font color='#16ACEA'>"+blue+"</font>"
                + last));
    }
}