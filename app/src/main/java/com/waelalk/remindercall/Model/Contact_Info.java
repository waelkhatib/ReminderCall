package com.waelalk.remindercall.Model;

import ir.mirrajabi.searchdialog.core.Searchable;

public class Contact_Info implements Searchable {
    private String name;
    private String phoneNo;
    private boolean status;

    public Contact_Info(String name, String phoneNo, boolean status) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((Contact_Info)obj).getPhoneNo().equals(this.getPhoneNo());
    }
}
