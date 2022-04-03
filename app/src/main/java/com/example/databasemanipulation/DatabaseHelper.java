package com.example.databasemanipulation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String dbName = "School.db";
    public static final int version = 2;
    public static final String TABLE_NAME = "Students";
    public static final String COL1 = "id";
    public static final String COL2 = "first_name";
    public static final String COL3 = "last_name";
    public static final String COL4 = "course";
    public static final String COL5 = "marks";
    public static final String COL6 = "credits";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL2 + " TEXT NOT NULL, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT);";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE); //Drop if table exists
        onCreate(sqLiteDatabase);
    }

    public boolean InsertStudent (Student stud){
        SQLiteDatabase db = this.getWritableDatabase(); // instance of SQL Lite db
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, stud.getFirstName());
        contentValues.put(COL3, stud.getLastName());
        contentValues.put(COL4, stud.getCourse());
        contentValues.put(COL5, stud.getMarks());
        contentValues.put(COL6, stud.getCredits());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }

        return true;

    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("Select * from " + TABLE_NAME, null);


        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor viewDataId(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("Select * from " + TABLE_NAME + " where id=?", new String []{id});
        System.out.println("OUTPUT: " + cursor.getCount());

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }
}
