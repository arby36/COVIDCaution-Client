package com.hack3atx.covidcaution;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.nfc.tech.NfcBarcode;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//CAMERA-CODE
//cameracode

public class MainActivity3 extends AppCompatActivity {


    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //Debug
        try {
            storeLocation("652 Croswell Ave SE, East Grand Rapids, MI, 49506");
        } catch (IOException e) {
            e.printStackTrace();
        }

        surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
        textView = (TextView) findViewById(R.id.textView);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }
//Important Part of The Code
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                    if(qrCodes.size()!=0) {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);
                                address = qrCodes.valueAt(0).displayValue;
                                textView.setText(qrCodes.valueAt(0).displayValue);
                                try {
                                    storeLocation(address);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
            }
        });
    };
    public void storeLocation(String location) throws IOException {
        FileOutputStream locationStoreOut = openFileOutput("locationStore", Context.MODE_PRIVATE);
        Date date = new Date();
        SimpleDateFormat dayFormatter = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String weekDay = dayFormatter.format(date);
        String timeDay = timeFormatter.format(date);
        Log.i(null, weekDay + ";" + timeDay);
        String locationData = location + ";" + weekDay + ";" + timeDay;
        locationStoreOut.write(locationData.getBytes());
        locationStoreOut.close();
    };
}
