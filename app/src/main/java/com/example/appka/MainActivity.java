package com.example.appka;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    private int CAMERA_PERMISSION_CODE = 1;
    public static TextView tvresult;
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://sekob.toliko.pl/Web/ScannerAPI.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/ValidateBarcodeEntry";
    private static final String METHOD_NAME = "ValidateBarcodeEntry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvresult = findViewById(R.id.tvresult);

        Button btnScan = findViewById(R.id.btnScan);
        Button btnCheck = findViewById(R.id.btnCheck);
        Button btnExit = findViewById(R.id.btnExit);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                PropertyInfo weightProp =new PropertyInfo();
                request.addProperty("scannerId", "test");// Parameter for Method
                request.addProperty("code", "sadfascas");// Parameter for Method

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                try {
                    MainActivity.tvresult.setText("dza ");
                    androidHttpTransport.call(SOAP_ACTION, envelope);


                    SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

                    System.out.println(resultsRequestSOAP.toString());
                    MainActivity.tvresult.setText("Response::"+resultsRequestSOAP.toString());
                    System.out.println("Response::"+resultsRequestSOAP.toString());




                } catch (Exception e) {
                    System.out.println("Error"+e);

                }
//
                }


           // }
        });



        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivity(intent);
                } else {
                    requestStoragePermission();
                }

            }
        });
//        btnCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
//                    startActivity(intent);
//                } else {
//                    requestStoragePermission();
//                }
//            }
//        });
//        btnExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
//                    startActivity(intent);
//                } else {
//                    requestStoragePermission();
//                }
//            }
//        });
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Wymagane pozwolenie")
                    .setMessage("Wymagane jest pozwolenie używania kamery")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
