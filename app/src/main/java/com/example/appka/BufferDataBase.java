package com.example.appka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BufferDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "buffer.db";
    public static final String Table_NAME = "buffer_table";
    public static final String COL_1 = "CODE";   //INTEGER NOT NULL

    public BufferDataBase(@Nullable Context context){
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_NAME + " (Code VARCHAR(50) NOT NULL)");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_NAME);
        onCreate(db);
        db.close();
    }

    public Boolean insertData(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,  code);
        long restult = db.insert(Table_NAME, null, contentValues);
        db.close();
        return restult != -1;
    }

    public List<String> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + Table_NAME, null);
        List<String> buffList = new ArrayList<>();
        result.close();
        db.close();

        if(result.getCount() == 0){

            return null;}
        while(result.moveToNext()) {
            String code = result.getString(0);
            buffList.add(code);
        }

        return buffList;
    }
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+ Table_NAME;
        db.execSQL(clearDBQuery);
        db.close();
    }
    public void delteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_NAME, "CODE = ?", new String[] {id});
        db.close();
    }

    public long iloscWBuforze() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, Table_NAME);
        db.close();
        return count;

    }
}
