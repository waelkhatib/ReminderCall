package com.waelalk.remindercall.View;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.waelalk.remindercall.External.RingTonePlayer;
import com.waelalk.remindercall.External.RingtonePickerDialog;
import com.waelalk.remindercall.External.RingtonePickerListener;
import com.waelalk.remindercall.R;

import java.io.IOException;

import afu.org.checkerframework.checker.nullness.qual.NonNull;

public class ConfigurationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EditText spinner=findViewById(R.id.spinner);
        spinner.setFocusable(false);
        spinner.setFocusableInTouchMode(false);
        final RingTonePlayer player=new RingTonePlayer(this);
        final Uri defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE);
        Ringtone defaultRingtone = RingtoneManager.getRingtone(this, defaultRingtoneUri);

        spinner.setText(defaultRingtone.getTitle(this));
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RingtonePickerDialog.Builder ringtonePickerBuilder = new RingtonePickerDialog
                        .Builder(ConfigurationActivity.this, getSupportFragmentManager())

                        //Set title of the dialog.
                        //If set null, no title will be displayed.
                        .setTitle("Select ringtone")

                        //set the currently selected uri, to mark that ringtone as checked by default.
                        //If no ringtone is currently selected, pass null.
                        .setCurrentRingtoneUri(null)

                        //Set true to allow allow user to select default ringtone set in phone settings.
                        //        .displayDefaultRingtone(true)

                        //Set true to allow user to select silent (i.e. No ringtone.).
                        //      .displaySilentRingtone(true)

                        //set the text to display of the positive (ok) button.
                        //If not set OK will be the default text.
                        .setPositiveButtonText("SET RINGTONE")

                        //set text to display as negative button.
                        //If set null, negative button will not be displayed.
                        .setCancelButtonText("CANCEL")

                        //Set flag true if you want to play the sample of the clicked tone.
                        .setPlaySampleWhileSelection(true)

                        //Set the callback listener.
                        .setListener(new RingtonePickerListener() {
                            @Override
                            public void OnRingtoneSelected(@NonNull String ringtoneName, Uri ringtoneUri) {
                                //Do someting with selected uri...
                            }
                        });

//Add the desirable ringtone types.
                ringtonePickerBuilder.addRingtoneType( RingtonePickerDialog.Builder.TYPE_MUSIC);
                ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_NOTIFICATION);
                ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_RINGTONE);
                ringtonePickerBuilder.addRingtoneType(RingtonePickerDialog.Builder.TYPE_ALARM);

//Display the dialog.
                ringtonePickerBuilder.show();
            }
        });
        Button run_btn=findViewById(R.id.run_btn);
        run_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button reset_btn=findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=  new AlertDialog.Builder(ConfigurationActivity.this).setTitle("Change settings")
                        .setMessage("Are you sure you want to return to default settings?")

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

        ImageButton play_btn=findViewById(R.id.play_btn);
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    player.play(defaultRingtoneUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
}
