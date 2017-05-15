package com.example.amitshveber.finalprojbeta;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class searchService extends IntentService {

    public searchService() {
        super("searchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (!DBConstant.nearby) {
            String userSearch = intent.getStringExtra("userSearch");
            String userCitySearch = intent.getStringExtra("userCitySearch");
            String URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + userSearch + "%20in%20" + userCitySearch + "&key=AIzaSyCMR_zknin2aHNROgqn-wWR3byMOVM7VEY";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();

            String result = "";
            try {

                Response response = client.newCall(request).execute();
                result = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            PlaceList placeList = gson.fromJson(result, PlaceList.class);
            ArrayList<Place> allPlaces = placeList.results;
            mySqlLastSearch mySqlLastSearch = new mySqlLastSearch(this);
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i <allPlaces.size() ; i++) {

                contentValues.put(DBConstant.nameColumm,allPlaces.get(i).name);
                if (allPlaces.get(i).formatted_address!=null){
                    contentValues.put(DBConstant.addressColumm,allPlaces.get(i).formatted_address);
                }else{
                    contentValues.put(DBConstant.distanceColumm,allPlaces.get(i).vicinity);
                }
                contentValues.put(DBConstant.ImgColumm,allPlaces.get(i).icon);
                contentValues.put(DBConstant.latColumm,allPlaces.get(i).geometry.location.lat);
                contentValues.put(DBConstant.lngColumm,allPlaces.get(i).geometry.location.lng);

                mySqlLastSearch.getWritableDatabase().insert(DBConstant.tableNameLastSearch, null,contentValues );
            }
            Intent sendToBroadcastIntent = new Intent("allPlacesIntent");
            sendToBroadcastIntent.putParcelableArrayListExtra("allPlacsesFromServiceNearby", allPlaces);
            LocalBroadcastManager.getInstance(this).sendBroadcast(sendToBroadcastIntent);
        } else {

            double lat = intent.getDoubleExtra("lat", 0);
            double lon = intent.getDoubleExtra("lon", 0);
            String userSearch = intent.getStringExtra("userSearch");

            //String URL ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=32.0667481,34.7767533&radius=500&keyword=pizza&key=AIzaSyDl8biKf3RH-rNiJTGkhigj7v011XWTfTI";

            //String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=32.0531445,34.7555643&radius=500&keyword=" + userSearch + "&key=AIzaSyCMR_zknin2aHNROgqn-wWR3byMOVM7VEY";
            String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lon + "&radius=500&keyword=" + userSearch + "&key=AIzaSyDl8biKf3RH-rNiJTGkhigj7v011XWTfTI";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();

            String result = "";
            try {

                Response response = client.newCall(request).execute();
                result = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            PlaceList placeList = gson.fromJson(result, PlaceList.class);
            ArrayList<Place> allPlaces = placeList.results;


            mySqlLastSearch mySqlLastSearch = new mySqlLastSearch(this);
            //TODO add to lastSearch
            ContentValues contentValues = new ContentValues();

            for (int i = 0; i <allPlaces.size() ; i++) {

                contentValues.put(DBConstant.nameColumm,allPlaces.get(i).name);
                if (allPlaces.get(i).formatted_address!=null){
                    contentValues.put(DBConstant.addressColumm,allPlaces.get(i).formatted_address);
                }else{
                    contentValues.put(DBConstant.distanceColumm,allPlaces.get(i).vicinity);
                }
                contentValues.put(DBConstant.ImgColumm,allPlaces.get(i).icon);
                contentValues.put(DBConstant.latColumm,allPlaces.get(i).geometry.location.lat);
                contentValues.put(DBConstant.lngColumm,allPlaces.get(i).geometry.location.lng);

                mySqlLastSearch.getWritableDatabase().insert(DBConstant.tableNameLastSearch, null,contentValues );
            }

            Intent sendToBroadcastIntent = new Intent("allPlacesIntentNearby");
            sendToBroadcastIntent.putParcelableArrayListExtra("allPlacsesFromServiceNearby", allPlaces);
            LocalBroadcastManager.getInstance(this).sendBroadcast(sendToBroadcastIntent);


        }

    }

}



