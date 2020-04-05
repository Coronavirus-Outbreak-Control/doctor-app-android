package com.example.coronavirusherdimmunitydoctor;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InsertPatientIdActivity extends AppCompatActivity {

    private AutoCompleteTextView ACTV_patientID;
    private Button bt_check_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_patient_id);

        ACTV_patientID = findViewById(R.id.ACTV_patientID);
        bt_check_id = findViewById(R.id.bt_check_id);

    }
}
