package com.example.amitshveber.finalprojbeta;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

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
        View itemView;
        Place currentPlace;

        mySql mySql;
        public myViewHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            nameTV = (TextView) itemView.findViewById(R.id.namePlaceItem);
            addressPlaceItem = (TextView) itemView.findViewById(R.id.addressPlaceItem);
            distancePlaceItem = (TextView) itemView.findViewById(R.id.distancePlaceItem);
            imagePlace = (ImageView) itemView.findViewById(R.id.imagPlaceItem);


        }

        public void tayTheCurrentPlaceToItem(final Place place) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    ChangeFragMaster ChFM = (ChangeFragMaster) context;
                    ChFM.changeFragments(allPlace.get(getAdapterPosition()));


                }
            });
              mySql = new mySql(context);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(context, v);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.shareITEM:

                                    //TODO Shareeee
                                    currentPlace= (Place) allPlace.get(getAdapterPosition());
                                    String location="https://www.google.co.il/maps/@"+currentPlace.geometry.location.lat+","+currentPlace.geometry.location.lng+",18.79z?hl=en";
                                    //
                                    Intent sharingIntent=new Intent(android.content.Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "place Details");
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,location );
                                    context.startActivity(sharingIntent);

                                    break;

                                case R.id.favoriteItem:
                                    currentPlace = allPlace.get(getAdapterPosition());

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(DBConstant.nameColumm,currentPlace.name);

                                  if (currentPlace.formatted_address!=null){

                                      contentValues.put(DBConstant.addressColumm,currentPlace.formatted_address);


                                  }else{

                                      contentValues.put(DBConstant.distanceColumm,currentPlace.vicinity);

                                  }

                                    contentValues.put(DBConstant.ImgColumm,currentPlace.icon);
                                    contentValues.put(DBConstant.latColumm,currentPlace.geometry.location.lat);
                                    contentValues.put(DBConstant.lngColumm,currentPlace.geometry.location.lng);
                                    mySql.getWritableDatabase().insert(DBConstant.tableNameFav, null,contentValues );

                                    Toasty.success(context, "Saved:))", Toast.LENGTH_SHORT, true).show();
                                    break;
                            }


                            return true;
                        }
                    });
                    return true;

                }
            });

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