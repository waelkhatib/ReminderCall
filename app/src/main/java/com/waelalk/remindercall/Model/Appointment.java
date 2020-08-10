package com.waelalk.remindercall.Model;

import android.graphics.Point;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Appointment {
    private String time;
    private boolean is_periodic;
    private boolean is_run;
    private Point geoCoordinates;
    private String message_text;
    private List<Contact_Info> contact_infoList;


    public Appointment() {
        this.contact_infoList = new ArrayList<>();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIs_periodic() {
        return is_periodic;
    }

    public void setIs_periodic(boolean is_periodic) {
        this.is_periodic = is_periodic;
    }

    public boolean isIs_run() {
        return is_run;
    }

    public void setIs_run(boolean is_run) {
        this.is_run = is_run;
    }

    public Point getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(Point geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public List<Contact_Info> getContact_infoList() {
        return contact_infoList;
    }

    public void setContact_infoList(List<Contact_Info> contact_infoList) {
        this.contact_infoList = contact_infoList;
    }
    public Calendar getCalendar() throws ParseException {
        String pattern=is_periodic?"HH:mm":"yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern,Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(time));
        return calendar;
    }
}
