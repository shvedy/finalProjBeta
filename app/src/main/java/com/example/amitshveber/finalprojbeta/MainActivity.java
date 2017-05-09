package com.example.amitshveber.finalprojbeta;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import layout.BlankFragment;

public class MainActivity extends AppCompatActivity implements ChangeFragMaster {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstFrag firstFrag = new firstFrag();


        //new transaction (replace/ add/ remove)
        getFragmentManager().beginTransaction().replace(R.id.myRelative, firstFrag).commit();


    }

    @Override
    public void changeFragments(Place currentPlace) {
        Bundle data = new Bundle();
        data.putDouble("lat", currentPlace.geometry.location.lat);
        data.putDouble("lng", currentPlace.geometry.location.lng);

        BlankFragment mapFragContain = new BlankFragment();
        mapFragContain.setArguments(data);
        getFragmentManager().beginTransaction().addToBackStack("mapFFrag").replace(R.id.myRelative, mapFragContain).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.goFavotriteOM:
                Intent intent=new Intent(MainActivity.this, favoriteScreen.class);
                startActivity(intent);
                //



                break;




        }


        return super.onOptionsItemSelected(item);
    }
}
