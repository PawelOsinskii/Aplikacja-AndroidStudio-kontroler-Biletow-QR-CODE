package com.example.appka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BufferDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "buffer.db";
    public static final String Table_NAME = "buffer_table";
    public static final String COL_1 = "ID";   //INTEGER NOT NULL
    public static final String COL_2 = "Code"; //VARCHAR(50) NOT NULL
    public static final String COL_3 = "Type"; //INTEGER NOT NULL
    public static final String COL_4 = "OperationTime"; //VARCHAR(50) NOT NULL
//TODO change columns

    public BufferDataBase(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_NAME + " (Code VARCHAR(50) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_NAME);
        onCreate(db);
    }

    public Boolean insertData(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,  code);
        long restult = db.insert(Table_NAME, null, contentValues);
        return restult != -1;
    }

    public List<BufferRecord> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + Table_NAME, null);
        List<BufferRecord> buffList = new ArrayList<>();

        if(result.getCount() == 0)
            return null;
        while(result.moveToNext()) {
            int id = result.getInt(0);
            String code = result.getString(1);
            int type = result.getInt(2);
            String operationTime = result.getString(3);
            buffList.add(new BufferRecord(id, code, type, operationTime));
        }
        return buffList;
    }
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+ Table_NAME;
        db.execSQL(clearDBQuery);
    }


}
