package com.example.calendarapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final int Version = 1;
    private static final String DATABASE_NAME = "day_memo";
    private static final String CREATE_RSS_FEED_TABLE = "CREATE TABLE RSS_FEED("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "TITLE TEXT,"
            + "MEMO TEXT,"
            + "DAY TEXT);";


    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RSS_FEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
