package com.example.amitshveber.finalprojbeta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by amit shveber on 04/05/2017.
 */

public class mySql extends SQLiteOpenHelper {
    public mySql(Context context) {
        super(context, "Movies.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQLCreate = "CREATE TABLE " + DBConstant.tableNameFav + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + DBConstant.nameColumm + " TEXT , " + DBConstant.addressColumm + " TEXT , " + DBConstant.distanceColumm + " TEXT , " + DBConstant.ImgColumm + " TEXT , " + DBConstant.latColumm + " TEXT , " + DBConstant.lngColumm + " TEXT)";

        db.execSQL(SQLCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Place> makeArreListFromCursor(Cursor cursor) {
        ArrayList<Place> ArrayFromSql = new ArrayList();
      while (cursor.moveToNext()) {
          String name = cursor.getString(cursor.getColumnIndex(DBConstant.nameColumm));
          String icon = cursor.getString(cursor.getColumnIndex(DBConstant.ImgColumm));
          String address = cursor.getString(cursor.getColumnIndex(DBConstant.addressColumm));
          String distance = cursor.getString(cursor.getColumnIndex(DBConstant.distanceColumm));
          if (distance == null) {
              distance = "";
          }
          double lat = cursor.getDouble(cursor.getColumnIndex(DBConstant.latColumm));
          double lng = cursor.getDouble(cursor.getColumnIndex(DBConstant.lngColumm));
          location loc = new location(lat, lng);
          geometry geo = new geometry(loc);
          String photo = cursor.getString(cursor.getColumnIndex(DBConstant.ImgColumm));
          ArrayFromSql.add(new Place(name, icon, address, distance, geo, photo));
      }
        return ArrayFromSql;
    }
}
