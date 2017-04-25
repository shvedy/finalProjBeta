package com.example.amitshveber.finalprojbeta;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFrag firstFrag = new firstFrag();


        //new transaction (replace/ add/ remove)
        getFragmentManager().beginTransaction().replace(R.id.myRelative, firstFrag).commit();


    }
}
