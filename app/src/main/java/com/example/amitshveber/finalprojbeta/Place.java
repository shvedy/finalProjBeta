package com.example.amitshveber.finalprojbeta;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by amit shveber on 25/04/2017.
 */
public class Place implements Parcelable{

    String name;
    String icon;
    String formatted_address;
    String vicinity;
    geometry geometry;
    ArrayList<MyPhoto>photos;

    protected Place(Parcel in) {
        name = in.readString();
        icon = in.readString();
        formatted_address = in.readString();
        vicinity = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeString(formatted_address);
        dest.writeString(vicinity);
    }
}
