package com.waelalk.remindercall.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.waelalk.remindercall.Model.Settings;
import com.waelalk.remindercall.R;
import com.waelalk.remindercall.View.TimesActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class Application extends android.app.Application {
    private static final String CONFIG ="config" ;
    private static final String SETTINGS ="SETTINGS" ;

    public static Settings getSystemSetting() {
        if(systemSetting==null)
            systemSetting=new Settings();
        return systemSetting;
    }

    private static Settings systemSetting;
    public static int getDisplayFactor() {
        return Locale.getDefault().getLanguage().equals("ar")?-1:1;
    }
    public static void makeSimpleDialog(Context context,int titleId,int messageId){
        AlertDialog dialog=  new AlertDialog.Builder(context).setTitle(titleId)
                .setMessage(messageId)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context. getResources(). getColor( R.color.colorAccent));
    }

    public static void showTimePickerDialog(Context context, Calendar mcurrentTime, TimePickerDialog.OnTimeSetListener onTimeSetListener, View clickView) {
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker= new TimePickerDialog(context, R.style.MyTimePickerDialogStyle, onTimeSetListener, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("");
        mTimePicker.show();
        mTimePicker.getButton(TimePickerDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickView.setClickable(true);
                mTimePicker.dismiss();
            }
        });
        mTimePicker.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(context. getResources().getColor(R.color.colorAccent));
        mTimePicker.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    public static void showSwitchDateTimeDialogFragment(Context context, AppCompatActivity fragment, Calendar mcurrentTime, SwitchDateTimeDialogFragment.OnButtonClickListener onButtonClickListener) {
        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "",
                context.getString(android.R.string.yes),
                context.getString(android.R.string.no)
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
        dateTimeDialogFragment.setOnButtonClickListener(onButtonClickListener);

// Show
        dateTimeDialogFragment.show(fragment.getSupportFragmentManager(), "dialog_time");


    }
    public static void SaveSharedPrefence(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SETTINGS,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(CONFIG,new Gson().toJson(getSystemSetting()));
        editor.apply();
    }

    public static void hideKeyboard(Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

}
