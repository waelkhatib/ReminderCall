package com.waelalk.remindercall.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.waelalk.remindercall.Adapter.TimeRecycleViewAdapter;
import com.waelalk.remindercall.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;

public class TimesActivity extends AppCompatActivity {

    TimeRecycleViewAdapter adapter;

    public static int PLACE_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);
        // data to populate the RecyclerView with
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.help:
                startActivity(new Intent(this,About_us.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initViews() {
        ArrayList<String> times = new ArrayList<>();
        times.add("12:34");
        times.add("03:56");
        times.add("14-06-2019 11:05");
        times.add("16-11-2019 10:00");
        times.add("14:20");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.timeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimeRecycleViewAdapter(this, times);
        recyclerView.setAdapter(adapter);
        Button save_btn=findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=  new AlertDialog.Builder(TimesActivity.this).setTitle("Save changes")
                        .setMessage("Are you sure you want to save these changes?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)

                        .show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( getResources(). getColor( R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources(). getColor( R.color.colorAccent));
            }
        });
        Button next_btn=findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimesActivity.this,ContactsActivity.class));
            }
        });
        final Switch switch1 = findViewById(R.id.is_periodic);
        ImageButton imageButton = findViewById(R.id.addTime);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                if (switch1.isChecked()) {

                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(TimesActivity.this, R.style.MyTimePickerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("");
                    mTimePicker.show();
                    mTimePicker.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                    mTimePicker.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));

                } else {


                    SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                            "",
                            "OK",
                            "Cancel"
                    );

// Assign values
                    dateTimeDialogFragment.startAtCalendarView();
                    dateTimeDialogFragment.set24HoursMode(true);
                    dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH)).getTime());
                    dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
                    dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH), mcurrentTime.get(Calendar.HOUR_OF_DAY), mcurrentTime.get(Calendar.MINUTE)).getTime());

// Define new day and month format
                    try {
                        dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
                    } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                        Log.e("", e.getMessage());
                    }

// Set listener
                    dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick(Date date) {
                            // Date is get on positive button click
                            // Do something
                        }

                        @Override
                        public void onNegativeButtonClick(Date date) {
                            // Date is get on negative button click
                        }
                    });

// Show
                    dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");


                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RESULT****", "OK");
            if (requestCode == 1) {
                double latitude = data.getDoubleExtra(LATITUDE, 0.0);
                Log.d("LATITUDE****", latitude+"");
                double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
                Log.d("LONGITUDE****", longitude+"");
                String address = data.getStringExtra(LOCATION_ADDRESS);
                Log.d("ADDRESS****", address);
                /*val postalcode = data.getStringExtra(ZIPCODE)
                Log.d("POSTALCODE****", postalcode.toString())
                val bundle = data.getBundleExtra(TRANSITION_BUNDLE)
                Log.d("BUNDLE TEXT****", bundle.getString("test"))
                val fullAddress = data.getParcelableExtra<Address>(ADDRESS)
                if (fullAddress != null) {
                    Log.d("FULL ADDRESS****", fullAddress.toString())
                }
                val timeZoneId = data.getStringExtra(TIME_ZONE_ID)
                if (timeZoneId != null) {
                    Log.d("TIME ZONE ID****", timeZoneId)
                }
                val timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME);
                if (timeZoneDisplayName != null) {
                    Log.d("TIME ZONE NAME****", timeZoneDisplayName)
                }
            } else if (requestCode == 2) {
                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("LATITUDE****", latitude.toString())
                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("LONGITUDE****", longitude.toString())
                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val lekuPoi = data.getParcelableExtra<LekuPoi>(LEKU_POI)
                        Log.d("LekuPoi****", lekuPoi.toString())
            */}
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
        }
    }

}
