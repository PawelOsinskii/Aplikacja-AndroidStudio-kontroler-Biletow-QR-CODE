package com.example.appka;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

import static com.example.appka.MainActivity.myDB;


public class ScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    //camera permission is needed.

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        // Do something with the result here
        Log.v("kkkk", result.getContents()); // Prints scan results
        Log.v("uuuu", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        if(MainActivity.checkorscan == 0) {
            String code = result.getContents();
            if(myDB.validEntry(code)){
                MainActivity.tvresult.setText(Html.fromHtml("<html><body><font color=green> "+ "MOŻNA WCHODZIĆ"+"</font> </body><html>"));
            }
            else MainActivity.tvresult.setText(Html.fromHtml(myDB.lastEntry(code)));

            onBackPressed();
        }
        else{
            MainActivity.tvresult.setText(Html.fromHtml(myDB.checkTicket(result.getContents())));
            onBackPressed();

        }
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}