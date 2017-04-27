package com.example.amitshveber.finalprojbeta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        TextView nameTV;
        TextView addressPlaceItem;
        TextView distancePlaceItem;
        ImageView imagePlace;

        public myViewHolder(final View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.namePlaceItem);
            addressPlaceItem = (TextView) itemView.findViewById(R.id.addressPlaceItem);
            distancePlaceItem = (TextView) itemView.findViewById(R.id.distancePlaceItem);
            imagePlace = (ImageView) itemView.findViewById(R.id.imagPlaceItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                //TODO press on RV

                }
            });

        }

        public void tayTheCurrentPlaceToItem(Place place) {

            nameTV.setText(place.name);
            addressPlaceItem.setText(place.formatted_address);
            distancePlaceItem.setText(place.vicinity);
            if (place.photos != null && place.photos.get(0) != null) {
                Picasso.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference=" + place.photos.get(0).photo_reference + "&key=AIzaSyCMR_zknin2aHNROgqn-wWR3byMOVM7VEY").into(imagePlace);
            } else {
                imagePlace.setImageResource(R.drawable.ic_error_outline_white_48dp);
            }

        }
    }


}