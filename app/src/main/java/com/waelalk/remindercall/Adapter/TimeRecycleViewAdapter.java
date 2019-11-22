package com.waelalk.remindercall.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.waelalk.remindercall.R;
import com.waelalk.remindercall.View.TimesActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.gms.location.places.ui.PlacePicker;

public class TimeRecycleViewAdapter extends RecyclerView.Adapter<TimeRecycleViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private Context context;


    // data is passed into the constructor
    public TimeRecycleViewAdapter(Context context, List<String> data) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.content_main, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=  new AlertDialog.Builder(context).setTitle("Delete Time")
                        .setMessage("Are you sure you want to delete this Time?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getData().remove(position);
                                notifyDataSetChanged();
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)

                        .show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context. getResources(). getColor( R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources(). getColor( R.color.colorAccent));
            }
        });
        holder.addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*Intent locationPickerIntent =new LocationPickerActivity.Builder()
                      .withGeolocApiKey("AIzaSyDs2H5xV71lB0URhfinVAQ6U1-83dmG5Fk")
                      .withSearchZone("es_ES")
                      .withGooglePlacesEnabled()
                      .build(context);*/
                      //  .withLocation(41.4036299, 2.1743558)

                        //.withSearchZone(new SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))

                       /* .shouldReturnOkOnBackPressed()
                        .withStreetHidden()
                        .withCityHidden()
                        .withZipCodeHidden()

                        .withSatelliteViewHidden()*/

                //        .withGoogleTimeZoneEnabled()
              //          .withVoiceSearchHidden()
              //          .withUnnamedRoadHidden()
                ((TimesActivity)context).checkPermissions();
                /*locationPickerIntent.putExtra(LocationPickerActivity.LOCATION_SERVICE, true);

                ((Activity) context).startActivityForResult(locationPickerIntent, TimesActivity.PLACE_PICKER_REQUEST);*/
  /*              PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    ((Activity) context). startActivityForResult(builder.build(((Activity) context)), TimesActivity.PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView myTextView;
        ImageButton deleteItem;
        ImageButton addLocation;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.timeVal);
            deleteItem=itemView.findViewById(R.id.dltItem);
            addLocation=itemView.findViewById(R.id.addLocation);
        }


    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    public List<String> getData() {
        return mData;
    }
}
