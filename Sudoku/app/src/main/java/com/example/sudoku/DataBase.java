package com.example.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context, "SudokuData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Sudokudetails(timeTakenTextView TEXT primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Sudokudetails");
    }

    public Boolean insertuserdata(String timeTakenTextView) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timeTakenTextView", timeTakenTextView);
        long result = DB.insert("Sudokudetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean deletedata(String timeTakenTextView) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Sudokudetails WHERE timeTakenTextView = ?", new String[]{timeTakenTextView});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Sudokudetails", "timeTakenTextView=?", new String[]{timeTakenTextView});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Sudokudetails", null);
        return cursor;
    }

    public boolean deleteAllData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        int result = DB.delete("Sudokudetails", null, null);
        return result > 0;
    }
}