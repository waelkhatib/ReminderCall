package com.waelalk.remindercall.Model;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private List<Appointment> appointments;

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


}
