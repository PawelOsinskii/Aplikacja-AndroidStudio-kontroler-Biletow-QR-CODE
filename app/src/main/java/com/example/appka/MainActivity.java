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

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    public static final String androidID = Settings.Secure.getString(MyApplication.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    public static final String DEVICEID = "test";
    public static final Integer FRONT_CAMERA_ID = 1;
    public static final Integer REAR_CAMERA_ID = 0;
    public static DataBaseHelper myDB;
    public static BufferDataBase buffer;
    public static TextView tvresult;
    public static TextView status;
    public static Boolean online = true;
    public static int checkorscan = 0;
    private int CAMERA_PERMISSION_CODE = 1;
    public long iloscKodow = 0;

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
        Button btnExit = findViewById(R.id.btnExit);
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
                WebActivity.getBarcodes(DEVICEID);
                Toast.makeText(this, "Pobrano kody z bazy danych", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteData:
                myDB.clearDatabase();
                Toast.makeText(this, "Usunięto kody z lokalnej bazy danych", Toast.LENGTH_LONG).show();
                return true;
            case R.id.iloscWBuforze:
                iloscKodow = buffer.iloscWBuforze();
                MainActivity.status.setText("ilosc kodow w buforze: " + iloscKodow);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
