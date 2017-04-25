package com.example.amitshveber.finalprojbeta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amit shveber on 20/04/2017.
 */

class Recycler0ViewAdapter extends RecyclerView.Adapter<Recycler0ViewAdapter.myViewHolder> {
    ArrayList<Place> allPlace;
    Context context;

    public Recycler0ViewAdapter(ArrayList<Place> allPlace, Context context) {
        this.allPlace = allPlace;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.place_item, parent, false);
        myViewHolder VH = new myViewHolder(v);
        return VH;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Place place = allPlace.get(position);
        holder.tayTheCurrentPlaceToItem(place);
    }

    @Override
    public int getItemCount() {


        return allPlace.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {


        public myViewHolder(View itemView) {
            super(itemView);
            TextView nameTV= (TextView) itemView.findViewById(R.id.namePlaceItem);
            TextView addressPlaceItem= (TextView) itemView.findViewById(R.id.addressPlaceItem);
            TextView distancePlaceItem= (TextView) itemView.findViewById(R.id.distancePlaceItem);
            ImageView imagePlace= (ImageView) itemView.findViewById(R.id.imagPlaceItem);


        }

        public void tayTheCurrentPlaceToItem(Place place) {


        }
    }


}