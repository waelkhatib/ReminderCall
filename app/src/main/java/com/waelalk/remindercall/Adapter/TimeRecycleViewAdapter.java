package com.waelalk.remindercall.Adapter;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.waelalk.remindercall.Helper.Application;
import com.waelalk.remindercall.Model.Appointment;
import com.waelalk.remindercall.R;
import com.waelalk.remindercall.View.TimesActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.gms.location.places.ui.PlacePicker;

public class TimeRecycleViewAdapter extends RecyclerView.Adapter<TimeRecycleViewAdapter.ViewHolder> {

    private List<Appointment> mData;
    private LayoutInflater mInflater;
    private Context context;


    // data is passed into the constructor
    public TimeRecycleViewAdapter(Context context, List<Appointment> data) {
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
        String time = mData.get(position).getTime();
        holder.myTextView.setText(time);
        holder.location.setText(mData.get(position).getGeoCoordinates()!=null?context.getString(R.string.location_selected):"");
        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                Appointment appointment=mData.get(position);
                Calendar calendar;
                try {
                    calendar=appointment.getCalendar();
                } catch (ParseException e) {
                    calendar=Calendar.getInstance();
                }
                if(appointment.isIs_periodic()){
                    Application.showTimePickerDialog(context,calendar , new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                          mData.get(position).setTime("" + hourOfDay + ":" + minute);
                          v.setClickable(true);
                          notifyDataSetChanged();
                        }
                    },v);
                }else {
                    Application.showSwitchDateTimeDialogFragment(context,(AppCompatActivity)context , calendar, new SwitchDateTimeDialogFragment.OnButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick(Date date) {
                            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.ENGLISH);
                            mData.get(position).setTime(format.format(date));
                            v.setClickable(true);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onNegativeButtonClick(Date date) {
                         v.setClickable(true);
                        }
                    });
                }
            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=  new AlertDialog.Builder(context).setTitle(R.string.delete_time)
                        .setMessage(R.string.delete_time_question)

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
              ((TimesActivity)context).setAdapterPosition(position);
              ((TimesActivity)context).checkPermissions();
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
        TextView location;
        ImageButton deleteItem;
        ImageButton editItem;
        ImageButton addLocation;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.timeVal);
            deleteItem=itemView.findViewById(R.id.dltItem);
            editItem=itemView.findViewById(R.id.editItem);
            addLocation=itemView.findViewById(R.id.addLocation);
            location=itemView.findViewById(R.id.location);
        }


    }

    // convenience method for getting data at click position
    Appointment getItem(int id) {
        return mData.get(id);
    }

    public List<Appointment> getData() {
        return mData;
    }
}
