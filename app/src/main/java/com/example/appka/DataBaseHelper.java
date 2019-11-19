package com.example.appka;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Kody.db";
    public static final String Table_NAME1 = "kody_table";
    public static final String Table_NAME2 = "bufor_table";
    public static final String COL_1 = "Code"; //VARCHAR(50) NOT NULL
    public static final String COL_2 = "MaxEntryCount"; //INTEGER NOT NULL DEFAULT '1'
    public static final String COL_3 = "CurrentEntryCount"; //INTEGER NOT NULL DEFAULT '0'
    public static final String COL_4 = "Active"; //BOOLEAN NOT NULL DEFAULT '1'
    public static final String COL_5 = "LastEntry"; //TIMESTAMP DEFAULT NULL
    public static final String COL_6 = "ValidFrom"; //TIMESTAMP DEFAULT (strftime('%Y-%m-%d %H:%M:%f', '1900-01-01 00:00:00.001', 'localtime'))
    public static final String COL_7 = "ValidTo"; //TIMESTAMP DEFAULT (strftime('%Y-%m-%d %H:%M:%f', '1900-01-01 00:00:00.001', 'localtime'))
    public static final String COL_8 = "Sektor"; //VARCHAR(50) DEFAULT
    public static final String COL_9 = "Rzad"; //VARCHAR(50) DEFAULT
    public static final String COL_10 = "Miejsce"; //VARCHAR(50) DEFAULT
    public static final String COL_11 = "PESEL"; //VARCHAR(50) DEFAULT
    public static final String COL_12 = "Imie"; //VARCHAR(50) DEFAULT
    public static final String COL_13 = "Nazwisko"; //VARCHAR(50) DEFAULT
    public static final String COL_14 = "Opis"; //VARCHAR(50) DEFAULT
    public static final String COL_15 = "Typ"; //VARCHAR(50) DEFAULT
    public static final String COL_16 = "Numer"; //INTEGER NOT NULL DEFAULT '0'
    public static final String COL_17 = "Status"; //INTEGER NOT NULL DEFAULT '0'
    public static final String COL_18 = "Event"; //VARCHAR(250) DEFAULT '',PRIMARY KEY(Code))";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     //   db.execSQL();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
