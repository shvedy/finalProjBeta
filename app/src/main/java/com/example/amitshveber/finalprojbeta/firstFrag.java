package com.example.amitshveber.finalprojbeta;


import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static android.content.Context.LOCATION_SERVICE;


public class firstFrag extends Fragment implements LocationListener {
    RecyclerView recyclerView;
    CheckBox nearbyCHB;
    double lon;
    double lat;
    Button searchBtn;
    EditText searchET;
    EditText citySearchET;
    LocationManager locationManager;

    public firstFrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_first, container, false);
        // locationManager = (LocationListener) getActivity().getSystemService(LOCATION_SERVICE);
        searchET = (EditText) view.findViewById(R.id.searchET);
        citySearchET = (EditText) view.findViewById(R.id.citySearchET);
        nearbyCHB = (CheckBox) view.findViewById(R.id.nearbyCHB);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);

        mySqlLastSearch mySqlLastSearch=new mySqlLastSearch(getActivity());
       //Cursor cursor=mySqlLastSearch.getReadableDatabase().query(DBConstant.tableNameLastSearch,null,null,null,null,null,null);


        locationManager = (LocationManager) getActivity().getSystemService(Service.LOCATION_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //requestLocationUpdates(the provider used to get location - gps/network , refresh time milliseconds ,minimum refresh distance,
            //location listener)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
        } else {
            //request permission 12 is the request number
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
        }


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new CheckConnection(getActivity()).isNetworkAvailable()) {

                    Intent intent = new Intent(getActivity(), searchService.class);

                    if (DBConstant.nearby) {


                        intent.putExtra("lat", lat);
                        intent.putExtra("lon", lon);
                        intent.putExtra("userSearch", searchET.getText().toString());

                    } else {

                        intent.putExtra("userSearch", searchET.getText().toString());
                        intent.putExtra("userCitySearch", citySearchET.getText().toString());
                    }
                    getActivity().startService(intent);
                } else {
                    Toasty.error(getActivity(), "no Connaction bra'", Toast.LENGTH_SHORT, true).show();

                }
            }
        });

        nearbyCHB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    citySearchET.setVisibility(View.VISIBLE);
                    DBConstant.nearby = false;
                } else {
                    citySearchET.setVisibility(View.INVISIBLE);
                    DBConstant.nearby = true;

                }

            }
        });

        IntentFilter intentFilter = new IntentFilter("allPlacesIntent");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new myBroadCast(), intentFilter);

        IntentFilter intentFilter1 = new IntentFilter("allPlacesIntentNearby");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new myBroadCast(), intentFilter1);

        recyclerView = (RecyclerView) view.findViewById(R.id.searchRV);
        return view;
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public class myBroadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Place> eliranPlaces = intent.getParcelableArrayListExtra("allPlacsesFromService");
            if (eliranPlaces == null) {

                Toasty.error(getActivity(), " No Location Found!!", Toast.LENGTH_SHORT, true).show();
            } else {


                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                Recycler0ViewAdapter myAdap = new Recycler0ViewAdapter(eliranPlaces, getActivity());

                recyclerView.setAdapter(myAdap);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//TODO need todo somthing here.
        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }


}
