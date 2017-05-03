package com.example.amitshveber.finalprojbeta;

import com.orm.SugarRecord;

/**
 * Created by amit shveber on 03/05/2017.
 */

public class sugarLastSearch extends SugarRecord {

    String Name;
    String Adress;
    String distance;
    String Image;
    double lat;
    double lng;

    public sugarLastSearch(String name, String adress, String distance, String image, double lat, double lng) {
        this.Name = name;
        this.Adress = adress;
        this.distance = distance;
        this.Image = image;
        this.lat = lat;
        this.lng = lng;
    }
}
