package com.example.coronavirusherdimmunitydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronavirusherdimmunitydoctor.enums.PatientStatus;
import com.example.coronavirusherdimmunitydoctor.utils.ApiManager;
import com.example.coronavirusherdimmunitydoctor.utils.Checksum;
import com.example.coronavirusherdimmunitydoctor.utils.PreferenceManager;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import okhttp3.Response;


/**
 * NOTE:
 * The Mobile Vision API supports the following formats of the barcode.
 *    - 1D barcodes: EAN-8, UPC-A, EAN-13, EAN-8, UPC-E, Code-93, Code-128, Code-39, Codabar, ITF.
 *    - 2D barcodes: QR Code, Data Matrix, AZTEC, PDF-417.
 */

public class ScannerActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private BarcodeDetector codeDetector;
    private CameraSource camera;
    private String patient_id = "";  //code recognized

    private boolean task_actived = true; //used to call just one by one the task "task_updateUserStatus"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_scanner_view);
        surfaceView = findViewById(R.id.surfaceView);
    }

    /**
     * Start camera and try to scan QR code
     */
    private void startScanner() {

        Toast.makeText(getApplicationContext(), R.string.toast_scanner_start, Toast.LENGTH_SHORT).show();
        codeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        camera = new CameraSource.Builder(this, codeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        //add camera to SurfaceView
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {  //add camera to Surface
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camera.stop();
            }
        });

        //manage code scanner
        codeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //when scanner is released then it is stopped if patient_id is empty else QR code is recognized
                if (patient_id.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), R.string.toast_scanner_stop, Toast.LENGTH_SHORT).show();
                }
                else{
                    patient_id = "";
                }
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> code = detections.getDetectedItems();
                if (code.size() != 0) {
                    patient_id = code.valueAt(0).displayValue;  //get qrcode recognized

                    try {
                        long pat_id = Long.parseLong(patient_id.replaceAll("\\D+","")); //convert QR code in Long number by removing chars

                        Integer new_status = PatientStatus.valueOf("SUSPECTED").ordinal();    //change new status to "Suspected/Pending"
                        if (task_actived) { //used to call just one by one the task "task_updateUserStatus"
                            task_actived = false; //become false so that task "task_updateUserStatus" cannot be called again
                            task_updateUserStatus(pat_id, new_status);  // Update status of patient Id to pending/suspect
                        }
                    } catch (NumberFormatException e) { //when patient id is not convertable in a long
                        Toast.makeText(getApplicationContext(), R.string.toast_wrong_scan, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        camera.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startScanner();
    }

    /******************** Task Functions *********************************************/


    /**
     * Compute checksum and add it to patient Id, it can be displayed to Doctor in order to write it on medical record
     * Update status of patient Id to pending/suspect
     * Run task in order to call updateUserStatus API and manage the response
     *
     * @param pat_id: patient id
     * @param new_status: health patient status (pending/suspect)
     */
    private void task_updateUserStatus(final long pat_id, final Integer new_status){

        long checksum = Checksum.computeChecksum(pat_id);              //compute checksum by patient id in order to add it to patient id
        final long pat_id_cs = Long.parseLong(Long.toString(pat_id) + Long.toString(checksum)); //add checksum as last digit of patient id

        Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {

                PreferenceManager pm = new PreferenceManager(getApplicationContext());
                Boolean updated =  false;
                String ret_value = "";

                while ( updated == false){

                    Response response_updateUS = ApiManager.updateUserStatus(pat_id, new_status, pm.getJwtToken());  //call updateUserStatus

                    if (response_updateUS != null) {
                        switch (response_updateUS.code()) {//check response status(code)
                            case 200:     // if response is 'ok' -> status has been changed
                                Log.d("task_updateUserStatus","status has been changed");
                                ret_value = "chg_st";
                                updated = true;
                                break;
                            case 403:     // if jwt token is not sent -> call refreshJwtToken and recall task_updateUserStatus
                            case 401:     // if jwt token is expired -> call refreshJwtToken and recall task_updateUserStatus
                                Log.d("task_updateUserStatus","Jwt Token expired");

                                Response response_refreshJwtToken = ApiManager.refreshJwtToken(pm.getAuthorizationToken());  //call refreshJwtToken
                                if (response_refreshJwtToken != null &&
                                        response_refreshJwtToken.code() == 200){ //check response status(code)

                                    try{
                                        String strResponse_body = response_refreshJwtToken.body().string();  //get body of Response
                                        JSONObject response_body = new JSONObject(strResponse_body);
                                        pm.setJwtToken(response_body.getString("token"));              //save new Jwt Token in shared preferences

                                    }catch (Exception e){
                                        Log.d("task_updateUserStatus", "Error to read jwt token received");
                                    }
                                }
                                break;
                            default:
                                Log.d("task_updateUserStatus", "Code not recognized:"+response_updateUS.code());
                                ret_value = "not_rec";
                                updated = true;
                                break;
                        }
                    } else{  // no response from Backend (like: internet disabled)
                        Log.d("task_updateUserStatus","No response by updateUserStatus");
                        ret_value = "no_resp";
                        updated = true;

                    }
                }

                return ret_value;
            }
        }).onSuccess(new Continuation<String, Object>() {
            @Override
            public String then(Task<String> task) throws Exception {

                switch (task.getResult()) {
                    case "chg_st":  //QR code recognized and healths status changed -> go to next activity (ScannerSuccessActivity)

                        Toast.makeText(getApplicationContext(), R.string.toast_scanner_recognized, Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getApplicationContext(), ScannerSuccessActivity.class);
                        intent.putExtra("patient id", pat_id_cs);   // give 'patientId+checksum' to next activity in order to display it
                        startActivity(intent);
                        finish();

                        break;
                    case "not_rec": //patient id (QR code) not recognized
                        task_actived = true;  //become true so that task "task_updateUserStatus" can be called again
                        Toast.makeText(getApplicationContext(), R.string.toast_scanner_code_not_recognized, Toast.LENGTH_LONG).show();
                        break;
                    case "no_resp": // no response from Backend (like: internet disabled)
                        task_actived = true;  //become true so that task "task_updateUserStatus" can be called again
                        Toast.makeText(getApplicationContext(), R.string.toast_scanner_no_resp_change_status, Toast.LENGTH_LONG).show();
                        break;
                    default: //some errors
                        task_actived = true;  //become true so that task "task_updateUserStatus" can be called again
                        Toast.makeText(getApplicationContext(), R.string.toast_scanner_err_status_change, Toast.LENGTH_LONG).show();
                        break;
                }

                return null;
            }
        },  Task.UI_THREAD_EXECUTOR);
    }


    /* Manage back button when is pressed in order to go to DoctorViewActivity */
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), DoctorViewActivity.class);
        startActivity(intent);
        finish();
    }
}