package com.example.coronavirusherdimmunitydoctor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
    private String patient_code = "";  //code recognized

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                if (patient_code.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), R.string.toast_scanner_stop, Toast.LENGTH_SHORT).show();
                }
                else{
                    patient_code = "";
                    Toast.makeText(getApplicationContext(), R.string.toast_scanner_recognized, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> code = detections.getDetectedItems();
                if (code.size() != 0) {
                    patient_code = code.valueAt(0).displayValue;  //get qrcode recognized

                    Intent intent=new Intent(ScannerActivity.this, ChangeStatusActivity.class);
                    intent.putExtra("patient code",patient_code);
                    startActivity(intent);

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
}