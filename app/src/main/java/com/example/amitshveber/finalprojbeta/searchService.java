package com.example.amitshveber.finalprojbeta;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.orm.SugarContext;

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

            Intent sendToBroadcastIntent = new Intent("allPlacesIntent");
            sendToBroadcastIntent.putParcelableArrayListExtra("allPlacsesFromService", allPlaces);
            LocalBroadcastManager.getInstance(this).sendBroadcast(sendToBroadcastIntent);
        } else {

            double lat = intent.getDoubleExtra("lat", 0);
            double lon = intent.getDoubleExtra("lon", 0);
            String userSearch = intent.getStringExtra("userSearch");


            String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lon + "&radius=500&keyword=" + userSearch + "&key=AIzaSyCMR_zknin2aHNROgqn-wWR3byMOVM7VEY";
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

            Intent sendToBroadcastIntent = new Intent("allPlacesIntentNearby");
            sendToBroadcastIntent.putParcelableArrayListExtra("allPlacsesFromServiceNearby", allPlaces);
            LocalBroadcastManager.getInstance(this).sendBroadcast(sendToBroadcastIntent);
            sugarLastSearch.deleteAll(sugarLastSearch.class);

            for (int i = 0; i < allPlaces.size(); i++) {

                SugarContext.init(this);
                sugarLastSearch sugarLastSearch = new sugarLastSearch(allPlaces.get(i).name, allPlaces.get(i).formatted_address, allPlaces.get(i).vicinity, allPlaces.get(i).icon, allPlaces.get(i).geometry.location.lat, allPlaces.get(i).geometry.location.lng);
                sugarLastSearch.save();


            }


        }

    }

}



