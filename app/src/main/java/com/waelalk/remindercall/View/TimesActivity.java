package com.waelalk.remindercall.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.libraries.places.api.model.Place;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.rtchagas.pingplacepicker.PingPlacePicker;
import com.waelalk.remindercall.Adapter.TimeRecycleViewAdapter;
import com.waelalk.remindercall.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TimesActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    TimeRecycleViewAdapter adapter;

    public final static int PLACE_PICKER_REQUEST = 0x3;

    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);
        // data to populate the RecyclerView with
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        setUpGClient();
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
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
        /*times.add("12:34");
        times.add("03:56");
        times.add("14-06-2019 11:05");
        times.add("16-11-2019 10:00");
        times.add("14:20");*/

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
                Intent intent=new Intent(TimesActivity.this,ContactsActivity.class);
                intent.putStringArrayListExtra("times",(ArrayList<String>) adapter.getData());
                startActivity(intent);
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
                             adapter.getData().add(""+selectedHour+":"+selectedMinute);
                             adapter.notifyDataSetChanged();
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
                            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            adapter.getData().add(format.format(date));
                            adapter.notifyDataSetChanged();
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

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        //finish();
                        break;
                }
                break;
            case  PLACE_PICKER_REQUEST:
/*

                if (resultCode == Activity.RESULT_OK && data != null) {
                    Log.d("RESULT****", "OK");

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
            */
              if(resultCode == Activity.RESULT_OK){
                  Place place = PingPlacePicker.getPlace(data);
                  if (place != null) {
                      Toast.makeText(this, "You selected the place: " + place.getName(), Toast.LENGTH_SHORT).show();
                  }
              }else
                if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d("RESULT****", "CANCELLED");
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }
    public void checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void getMyLocation(){
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback() {

                        @Override
                        public void onResult(@NonNull Result result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(TimesActivity
                                    .this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
                                        builder.setAndroidApiKey("AIzaSyDs2H5xV71lB0URhfinVAQ6U1-83dmG5Fk")
                                                .setMapsApiKey("AIzaSyDs2H5xV71lB0URhfinVAQ6U1-83dmG5Fk");

                                        // If you want to set a initial location rather then the current device location.
                                        // NOTE: enable_nearby_search MUST be true.
                                        // builder.setLatLng(new LatLng(37.4219999, -122.0862462))

                                        try {
                                            Intent placeIntent = builder.build(TimesActivity.this);
                                            startActivityForResult(placeIntent, PLACE_PICKER_REQUEST);
                                        }
                                        catch (Exception ex) {
                                            // Google Play services is not available...
                                        }
                                        /*Intent locationPickerIntent =new LocationPickerActivity.Builder()
                                                //  .withLocation(41.4036299, 2.1743558)
                                                .withGeolocApiKey("AIzaSyDs2H5xV71lB0URhfinVAQ6U1-83dmG5Fk")
                                                .withSearchZone("es_ES")
                                                //.withSearchZone(new SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))

                                                /* .shouldReturnOkOnBackPressed()
                                                 .withStreetHidden()
                                                 .withCityHidden()
                                                 .withZipCodeHidden()

                                                 .withSatelliteViewHidden()*/

                                                //        .withGoogleTimeZoneEnabled()
                                                //          .withVoiceSearchHidden()
                                                //          .withUnnamedRoadHidden()
                                              /*.withGooglePlacesEnabled()
                                                .build(TimesActivity.this);
                                        locationPickerIntent.putExtra(LocationPickerActivity.LOCATION_SERVICE, true);

                                        startActivityForResult(locationPickerIntent, TimesActivity.PLACE_PICKER_REQUEST);*/
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(TimesActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have no way to fix the
                                    // settings so we won't show the dialog.
                                    //finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }
}
