package com.example.coronavirusherdimmunitydoctor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScannerSuccessActivity extends AppCompatActivity {

    private TextView tv_show_patient_id;
    private Button bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_success);

        tv_show_patient_id = findViewById(R.id.tv_show_patient_id);
        bt_next = findViewById(R.id.bt_next);

    }
}
