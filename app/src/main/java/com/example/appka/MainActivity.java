package com.example.appka;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDB;
    private int CAMERA_PERMISSION_CODE = 1;
    public static TextView tvresult;
    public static TextView status;
    public static Boolean online = true;
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://sekob.toliko.pl/Web/ScannerAPI.asmx?wsdl";
    private static final String SOAP_ACTION = "http://tempuri.org/ValidateBarcodeEntry";
    private static final String METHOD_NAME = "ValidateBarcodeEntry";

    public static final String androidID = Settings.Secure.getString(MyApplication.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    public static final String FRONT_CAMERA_ID = "1";
    public static final String REAR_CAMERA_ID = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DataBaseHelper(this);
        status = findViewById(R.id.trybOnOf);
        tvresult = findViewById(R.id.tvresult);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

//Kod do wyłapywania ID przedniej i tylniej kamery ale powinno się obejść bez tego
//        CameraManager cManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            for(final String cameraId : cManager.getCameraIdList()){
//                CameraCharacteristics characteristics = cManager.getCameraCharacteristics(cameraId);
//                int cOrientation = characteristics.get(CameraCharacteristics.LENS_FACING);
//                if(cOrientation == CameraCharacteristics.LENS_FACING_FRONT) FRONT_CAMERA =  cameraId;
//            }
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
        tvresult.setText(androidID);

        StrictMode.setThreadPolicy(policy);
        Button btnScan = findViewById(R.id.btnScan);
        Button btnCheck = findViewById(R.id.btnCheck);
        Button btnExit = findViewById(R.id.btnExit);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.skanujKod("GUM79SD7");


//
                }


           // }
        }

        );


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
//
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
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
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
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Offline:

                if (!online) MainActivity.tvresult.setText("Jest już ustawiony tryb offline");
                else {
                    MainActivity.tvresult.setText("Zmienione na tryb offline");
                    MainActivity.status.setText("Status offline");
                    online = false;
                }
                return true;
            case R.id.Online:
                if (online) MainActivity.tvresult.setText("Jest już ustawiony tryb online");
                else {
                    MainActivity.tvresult.setText("Zmienione na tryb online");
                    MainActivity.status.setText("Status online");
                    online = true;
                }
                return true;
            case R.id.Aktualizuj:
                if (!online)
                    MainActivity.tvresult.setText("Nie możesz wykonać aktualizacji w trybie offline");

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
