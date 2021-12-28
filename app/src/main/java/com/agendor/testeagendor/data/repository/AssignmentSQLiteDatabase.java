package com.agendor.testeagendor.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.agendor.testeagendor.domain.constants.DatabaseConstants;

/**
 * Create by Robson Freitas 28/01/2021
 */
public class AssignmentSQLiteDatabase extends SQLiteOpenHelper {

    public AssignmentSQLiteDatabase(@Nullable Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String commandLine = "CREATE TABLE " + DatabaseConstants.TABLE + "(" + DatabaseConstants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseConstants.TYPE + " TEXT, " + DatabaseConstants.DATE + " TEXT," + DatabaseConstants.CLIENT + " TEXT, " + DatabaseConstants.DESCRIPTION + " TEXT, "
                + DatabaseConstants.DONE + " INTEGER" +")";
        db.execSQL(commandLine);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        String commandLine = "DROP TABLE IF EXISTS " + DatabaseConstants.TABLE;
        db.execSQL(commandLine);
        onCreate(db);
    }

}
