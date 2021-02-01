package com.agendor.testeagendor.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Create by Robson Freitas 28/01/2021
 */
public class CreateSQLiteDatabase extends SQLiteOpenHelper {


    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "agendor.db";
    public static final String TABLE = "assignments";
    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String CLIENT = "client";
    public static final String DESCRIPTION = "description";
    public static final String DONE = "done";

    public CreateSQLiteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String commandLine = "CREATE TABLE " + TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE + " TEXT, " + DATE + " TEXT," + CLIENT + " TEXT, " + DESCRIPTION + " TEXT, "
                + DONE + " INTEGER" +")";
        db.execSQL(commandLine);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String commandLine = "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(commandLine);
        onCreate(db);
    }

}
