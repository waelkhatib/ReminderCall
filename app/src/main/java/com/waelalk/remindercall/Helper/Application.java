package com.waelalk.remindercall.Helper;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TimePicker;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.waelalk.remindercall.R;
import com.waelalk.remindercall.View.TimesActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class Application extends android.app.Application {
    public static int getDisplayFactor() {
        return displayFactor;
    }

    private static int displayFactor=1;

    public static void showTimePickerDialog(Context context, Calendar mcurrentTime, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker= new TimePickerDialog(context, R.style.MyTimePickerDialogStyle, onTimeSetListener, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("");
        mTimePicker.show();
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
            dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", context.getResources().getConfiguration().locale));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("", e.getMessage());
        }

// Set listener
        dateTimeDialogFragment.setOnButtonClickListener(onButtonClickListener);

// Show
        dateTimeDialogFragment.show(fragment.getSupportFragmentManager(), "dialog_time");


    }

    @Override
    public void onCreate() {
        if(Locale.getDefault().getLanguage().equals("ar")){
            displayFactor=-1;
        }
        super.onCreate();
    }

}
