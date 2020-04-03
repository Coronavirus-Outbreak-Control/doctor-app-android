package com.example.coronavirusherdimmunitydoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


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
                    Toast.makeText(getApplicationContext(), R.string.toast_scanner_recognized, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> code = detections.getDetectedItems();
                if (code.size() != 0) {
                    patient_id = code.valueAt(0).displayValue;  //get qrcode recognized

                    try {
                        Long pat_id = Long.parseLong(patient_id.replaceAll("\\D+","")); //convert QR code in Long number by removing chars
                        Intent intent=new Intent(ScannerActivity.this, ChangeStatusActivity.class);
                        intent.putExtra("patient id", pat_id);
                        startActivity(intent);
                        finish();
                    } catch (NumberFormatException e) { //when patient id is not convertable in a Long
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


    /* Manage back button when is pressed in order to go to DoctorViewActivity */
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), DoctorViewActivity.class);
        startActivity(intent);
        finish();
    }
}