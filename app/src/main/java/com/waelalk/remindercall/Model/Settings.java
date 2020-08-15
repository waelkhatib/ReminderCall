package com.waelalk.remindercall.Model;

import android.media.RingtoneManager;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private List<Appointment> appointments;
    private int spatialTolerance=100;
    private int timeTolerance=1;
    private String ringtoneURI="content://media/internal/audio/media/50";
    private boolean audioAlarmEnabled=false;
    private boolean SMSMessageEnabled=false;
    private boolean serviceStopToday=false;

    public Settings() {
        this.appointments = new ArrayList<>();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public boolean isFirstPhaseFinished(){
        return !getAppointments().isEmpty();
    }
    public boolean isSecondPhaseFinished(){
        boolean finish=isFirstPhaseFinished();
        for(Appointment appointment:getAppointments()){
            if(appointment.getContact_infoList().isEmpty()){
                finish=false;
                break;
            }
        }
        return finish;

    }

    public int getSpatialTolerance() {
        return spatialTolerance;
    }

    public void setSpatialTolerance(int spatialTolerance) {
        this.spatialTolerance = spatialTolerance;
    }

    public int getTimeTolerance() {
        return timeTolerance;
    }

    public void setTimeTolerance(int timeTolerance) {
        this.timeTolerance = timeTolerance;
    }

    public String getRingtoneURI() {
        return ringtoneURI;
    }

    public void setRingtoneURI(String ringtoneURI) {
        this.ringtoneURI = ringtoneURI;
    }

    public boolean isAudioAlarmEnabled() {
        return audioAlarmEnabled;
    }

    public void setAudioAlarmEnabled(boolean audioAlarmEnabled) {
        this.audioAlarmEnabled = audioAlarmEnabled;
    }

    public boolean isSMSMessageEnabled() {
        return SMSMessageEnabled;
    }

    public void setSMSMessageEnabled(boolean SMSMessageEnabled) {
        this.SMSMessageEnabled = SMSMessageEnabled;
    }

    public boolean isServiceStopToday() {
        return serviceStopToday;
    }

    public void setServiceStopToday(boolean serviceStopToday) {
        this.serviceStopToday = serviceStopToday;
    }
    public void returnToDefault(){
        setAudioAlarmEnabled(false);
        setRingtoneURI("content://media/internal/audio/media/50");
        setServiceStopToday(false);
        setSMSMessageEnabled(false);
        setSpatialTolerance(100);
        setTimeTolerance(5);
    }
}
