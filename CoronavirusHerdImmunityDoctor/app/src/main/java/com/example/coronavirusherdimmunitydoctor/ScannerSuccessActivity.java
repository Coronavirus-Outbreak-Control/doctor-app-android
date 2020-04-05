package com.example.coronavirusherdimmunitydoctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

public class ScannerSuccessActivity extends AppCompatActivity {

    private TextView tv_show_patient_id;
    private Button bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_success);

        tv_show_patient_id = findViewById(R.id.tv_show_patient_id);
        bt_next = findViewById(R.id.bt_next);

        Intent intent = getIntent();
        final long patient_id_cs = intent.getLongExtra("patient id", -1); //get 'patient id' with checksum recognized by QR code scanner
        final long patient_id = patient_id_cs / 10;  //get 'patient Id' without 'checksum' (removing last digit)

        String text = String.format(getString(R.string.tv_show_patientid), patient_id_cs);   //add patient id on TextView
        tv_show_patient_id.setText(Html.fromHtml(text));

        /****************** Next Step Button *******************/
        bt_next.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * Click on "Next Step" button, open dialog which ask if Doctor knows swab results
             */
            public void onClick(View view)
            {
                String al_msg = String.format(getString(R.string.alert_results_msg), patient_id_cs); //add patient id on alert msg

                AlertDialog.Builder builder = new AlertDialog.Builder(ScannerSuccessActivity.this);
                builder.setTitle(R.string.alert_results_title);
                builder.setMessage(Html.fromHtml(al_msg));
                builder.setPositiveButton(R.string.alert_results_pos_bt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if Doctor knows swab results then go to ChangeStatusActivity
                        Intent intent=new Intent(getApplicationContext(), ChangeStatusActivity.class);
                        intent.putExtra("patient id", patient_id);   // give 'patientId' to next activity in order to change status of patient
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.alert_results_neg_bt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if Doctor does not know swab results then go to DoctorViewActivity
                        Intent intent=new Intent(getApplicationContext(), DoctorViewActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
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
