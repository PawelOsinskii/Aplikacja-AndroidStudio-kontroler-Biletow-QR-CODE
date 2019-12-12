package com.example.appka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BufferDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "buffer.db";
    public static final String Table_NAME = "bufor_table";
    public static final String COL_1 = "I"; //VARCHAR(50) NOT NULL
    public static final String COL_2 = "C"; //VARCHAR(50) NOT NULL
    public static final String COL_3 = "T"; //VARCHAR(50) NOT NULL
    public static final String COL_4 = "D"; //VARCHAR(50) NOT NULL
//TODO change columns

    public BufferDataBase(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_NAME + " (Code VARCHAR(50) NOT NULL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_NAME);
        onCreate(db);
    }

    public Boolean insertData(int I, String C, int T, String D) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,  I);
        contentValues.put(COL_2,  C);
        contentValues.put(COL_3,  T);
        contentValues.put(COL_4,  D);
        long restult = db.insert(Table_NAME, null, contentValues);
        return restult != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + Table_NAME, null);
        return result;
    }


}
