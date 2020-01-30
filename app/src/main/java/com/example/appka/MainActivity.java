package com.example.appka;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
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

import java.util.Timer;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String androidID = Settings.Secure.getString(MyApplication.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    public static DataBaseHelper myDB;
    public static BufferDataBase buffer;
    public static TextView tvresult;
    public static TextView status;
    public static Boolean online = true;
    public static int checkorscan = 0;
    private int CAMERA_PERMISSION_CODE = 1;
    public long iloscKodow = 0;
    public static String deviceId;

    public static String getDeviceId() {
        return deviceId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DataBaseHelper(this);
        buffer = new BufferDataBase(this);
        status = findViewById(R.id.trybOnOf);
        tvresult = findViewById(R.id.tvresult);

        Timer timer = new Timer();
        timer.schedule(new ValidateCode(), 5000, 5000);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        tvresult.setText(androidID);

        StrictMode.setThreadPolicy(policy);
        Button btnScan = findViewById(R.id.btnScan);
        Button btnCheck = findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkorscan = 1;
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivity(intent);
                } else {
                    requestStoragePermission();
                }
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkorscan = 0;
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivity(intent);
                } else {
                    requestStoragePermission();
                }
            }
        });
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

            case R.id.Aktualizuj:
                if(deviceId != null){
                WebActivity.getBarcodes(deviceId);
                Toast.makeText(this, "Pobrano kody z bazy danych", Toast.LENGTH_LONG).show();
                return true;}
                else{
                    Toast.makeText(this, "Musisz najpierw z synchronizować urządzenie", Toast.LENGTH_LONG).show();
                    return true;
                }
            case R.id.deleteData:
                myDB.clearDatabase();
                Toast.makeText(this, "Usunięto kody z lokalnej bazy danych", Toast.LENGTH_LONG).show();
                return true;
            case R.id.iloscWBuforze:
                iloscKodow = buffer.iloscWBuforze();
                MainActivity.status.setText("ilosc kodow w buforze: " + iloscKodow);
            case R.id.synchro:
                deviceId  =  getIMEINumber();
                WebActivity.sendUUIDToApi(deviceId);
                if(deviceId != null){
                    Toast.makeText(this, "Ustawiono device ID", Toast.LENGTH_LONG).show();
                }



            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @SuppressWarnings("deprecation")
    private String getIMEINumber() {
        String IMEINumber = "";
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IMEINumber = telephonyMgr.getImei();
            } else {
                IMEINumber = telephonyMgr.getDeviceId();
            }
        }
        return IMEINumber;
    }

}
//todo trzeba sprawdzic czy dziala tajmer na wysylanie w ValidaateCode, połączyć skanowanie tak aby jednoczesnie laczylo sie z api i na lokalnej bazie danych. zrobic haslo do admina zeby usuniecie kodow i pobranie bylo tylko z adm, zrobic tak aby automatycznie aktualizowalo sie z baza danych jak bedzie polaczenie internetowe.