package com.waelalk.remindercall.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.waelalk.remindercall.External.RingTonePlayer;
import com.waelalk.remindercall.External.RingtonePickerDialog;
import com.waelalk.remindercall.External.RingtonePickerListener;
import com.waelalk.remindercall.Helper.Application;
import com.waelalk.remindercall.R;

import java.io.IOException;

public class ConfigurationActivity extends AppCompatActivity {
private Uri defaultRingtoneUri;
private Ringtone ringtone;
private ImageButton play_btn;
private EditText time_tolerance_min;
private EditText spatial_tolerance_m;
private EditText spinner;
private AppCompatCheckBox audio_alarm;
private AppCompatCheckBox sms_message;

private final RingTonePlayer player=new RingTonePlayer(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.mainView).requestFocus();
        time_tolerance_min=findViewById(R.id.time_tolerance_min);
        spatial_tolerance_m=findViewById(R.id.spatial_tolerance_m);
        spinner=findViewById(R.id.spinner);
        audio_alarm=findViewById(R.id.audio_alarm);
        sms_message=findViewById(R.id.sms_message);
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlayed()){
                    try {
                        player.play(defaultRingtoneUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                RingtonePickerDialog.Builder ringtonePickerBuilder = new RingtonePickerDialog
                        .Builder(ConfigurationActivity.this, getSupportFragmentManager())

                        //Set title of the dialog.
                        //If set null, no title will be displayed.
                        .setTitle(getString(R.string.select_ringtone))

                        //set the currently selected uri, to mark that ringtone as checked by default.
                        //If no ringtone is currently selected, pass null.
                        .setCurrentRingtoneUri(defaultRingtoneUri)

                        //Set true to allow allow user to select default ringtone set in phone settings.
                        //        .displayDefaultRingtone(true)

                        //Set true to allow user to select silent (i.e. No ringtone.).
                        //      .displaySilentRingtone(true)

                        //set the text to display of the positive (ok) button.
                        //If not set OK will be the default text.
                        .setPositiveButtonText(getString(android.R.string.yes))

                        //set text to display as negative button.
                        //If set null, negative button will not be displayed.
                        .setCancelButtonText(getString(android.R.string.no))

                        //Set flag true if you want to play the sample of the clicked tone.
                        .setPlaySampleWhileSelection(true)

                        //Set the callback listener.
                        .setListener(new RingtonePickerListener() {
                            @Override
                            public void OnRingtoneSelected(@NonNull String ringtoneName, Uri ringtoneUri) {
                                //Do someting with selected uri...
                                Application.getSystemSetting().setRingtoneURI(ringtoneUri.toString());
                                initViews();

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
                AlertDialog dialog=  new AlertDialog.Builder(ConfigurationActivity.this).setTitle(R.string.save_chnge_lbl)
                        .setMessage(R.string.save_chnge_question)

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

        play_btn=findViewById(R.id.play_btn);
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
        initViews();
    }

    private void initViews() {
        defaultRingtoneUri = Uri.parse(Application.getSystemSetting().getRingtoneURI());
        ringtone = RingtoneManager.getRingtone(this, defaultRingtoneUri);
        spinner.setText(ringtone.getTitle(this));
        time_tolerance_min.setText(""+Application.getSystemSetting().getTimeTolerance());
        spatial_tolerance_m.setText(""+Application.getSystemSetting().getSpatialTolerance());
        audio_alarm.setChecked(Application.getSystemSetting().isAudioAlarmEnabled());
        sms_message.setChecked(Application.getSystemSetting().isAudioAlarmEnabled());
    }

    public void changeImageBtn() {
        play_btn.setImageResource(player.isPlayed()?R.drawable.ic_pause_black_24dp:R.drawable.ic_play_arrow_black_24dp);
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
