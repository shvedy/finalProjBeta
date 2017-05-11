package com.example.amitshveber.finalprojbeta;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amit shveber on 11/05/2017.
 */

public class mySqlLastSearch extends SQLiteOpenHelper {

    public mySqlLastSearch(Context context) {
        super(context, "Movies.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQLCreate = "CREATE TABLE " + DBConstant.tableNameLastSearch + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + DBConstant.nameColumm + " TEXT , " + DBConstant.addressColumm + " TEXT , " + DBConstant.distanceColumm + " TEXT , " + DBConstant.ImgColumm + " TEXT , " + DBConstant.latColumm + " TEXT , " + DBConstant.lngColumm + " TEXT)";

        db.execSQL(SQLCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
