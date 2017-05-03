package com.example.amitshveber.finalprojbeta;

import com.orm.SugarRecord;

/**
 * Created by amit shveber on 03/05/2017.
 */

public class sugar extends SugarRecord {

    String Name;
    String Adress;
    String distance;
    String Image;
    double lat;
    double lng;

    public sugar(String name, double lng, String adress, String image, String distance, double lat) {
        this.Name = name;
        this.Adress = adress;
        this.Image = image;
        this.distance = distance;
        this.lat = lat;
        this.lng = lng;

    }
}
