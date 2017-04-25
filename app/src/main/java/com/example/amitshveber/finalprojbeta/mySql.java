package com.example.amitshveber.finalprojbeta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amit shveber on 24/04/2017.
 */

public class mySql extends SQLiteOpenHelper {
    public mySql(Context context) {

        super(context, "location.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLCreate = "CREATE TABLE"+DBConstant.tableName+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + DBConstant.nameColumm + " TEXT , " + DBConstant.addressColumm + " TEXT , " + DBConstant.distanceColumm + " TEXT , " + DBConstant.ImgColumm + " TEXT)";

        db.execSQL(SQLCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
