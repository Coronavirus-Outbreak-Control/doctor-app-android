package com.example.coronavirusherdimmunitydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coronavirusherdimmunitydoctor.utils.Checksum;

public class InsertPatientIdActivity extends AppCompatActivity {

    private AutoCompleteTextView ACTV_patientID;
    private Button bt_check_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_patient_id);

        ACTV_patientID = findViewById(R.id.ACTV_patientID);
        bt_check_id = findViewById(R.id.bt_check_id);

        bt_check_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ACTV_patientID.getText().length() == 0){
                    Toast.makeText(InsertPatientIdActivity.this, R.string.toast_err_empty_id, Toast.LENGTH_SHORT).show();

                } else if (Checksum.verifyChecksum(Long.parseLong(ACTV_patientID.getText().toString()))){ //if checksum is verified then go to change status
                    try {
                            long pat_id_cs = Long.parseLong(ACTV_patientID.getText().toString()); //patient id + checksum
                            long pat_id = pat_id_cs / 10;             //patient id without checksum (removing last digit)
                            Intent intent=new Intent(InsertPatientIdActivity.this, ChangeStatusActivity.class);
                            intent.putExtra("patient id", pat_id);
                            startActivity(intent);
                            finish();

                    } catch (NumberFormatException e) { //when patient id is not convertable in a long
                        Toast.makeText(getApplicationContext(), R.string.toast_wrong_scan, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new AlertDialog.Builder(InsertPatientIdActivity.this)
                            .setTitle("ERROR")
                            .setMessage("Il codice inserito non è valido. Ripetere l'inserimento")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });

    }

    /* Manage back button when is pressed in order to go to DoctorViewActivity */
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), DoctorViewActivity.class);
        startActivity(intent);
        finish();
    }
}
