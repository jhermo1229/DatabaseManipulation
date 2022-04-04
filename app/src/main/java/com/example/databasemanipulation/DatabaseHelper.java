package com.example.databasemanipulation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Database handler for the project.
 * Uses SQLite
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Initializing needed objects for database handler
    public static final String dbName = "School.db";
    public static final int version = 2;
    public static final String TABLE_NAME = "Students";
    public static final String ID = "id";
    public static final String COL_FIRSTNAME = "first_name";
    public static final String COL_LASTNAME = "last_name";
    public static final String COL_COURSE = "course";
    public static final String COL_MARKS = "marks";
    public static final String COL_CREDITS = "credits";

    //Create table constant. ID is primary key and auto-incremented
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FIRSTNAME + " TEXT NOT NULL, " + COL_LASTNAME + " TEXT, " + COL_COURSE + " TEXT, " + COL_MARKS + " TEXT, " + COL_CREDITS + " TEXT);";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String GET_ALL = "getAll";
    public static final String PROGRAM_CODE = "programCode";


    //Constructor for initializing database handler
    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, version);
    }

    //Creation of table if not existing or upgrading
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    //Call for upgrading the table. Will Drop first and create a new table
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE); //Drop if table exists
        onCreate(sqLiteDatabase);
    }

    //Database call for inserting object
    public boolean InsertStudent(Student stud) {
        SQLiteDatabase db = this.getWritableDatabase(); // instance of SQL Lite db
        ContentValues contentValues = new ContentValues();

        //COL1 is ID and it is auto-incremented
        contentValues.put(COL_FIRSTNAME, stud.getFirstName());
        contentValues.put(COL_LASTNAME, stud.getLastName());
        contentValues.put(COL_COURSE, stud.getCourse());
        contentValues.put(COL_MARKS, stud.getMarks());
        contentValues.put(COL_CREDITS, stud.getCredits());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        return true;

    }

    //Database call for selecting data in table
    public Cursor viewData(String searchType, String param) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        //if parameter passed is "getAll" then it gets all the data in the table.
        //Otherwise, it gets based on param.
        if (searchType.equalsIgnoreCase(GET_ALL)) {
            cursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        } else if (searchType.equalsIgnoreCase(ID)) {
            cursor = db.rawQuery("Select * from " + TABLE_NAME + " where id=?", new String[]{param});
        } else if (searchType.equalsIgnoreCase(PROGRAM_CODE)) {
            cursor = db.rawQuery("Select * from " + TABLE_NAME + " where course=?", new String[]{param});
        }
        //if data gathered is not null, then will move to first record.
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }
}
