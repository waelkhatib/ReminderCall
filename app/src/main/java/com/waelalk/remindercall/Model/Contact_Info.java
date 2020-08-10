package com.waelalk.remindercall.Model;

import ir.mirrajabi.searchdialog.core.Searchable;

public class Contact_Info implements Searchable {
    private String name;
    private String phoneNo;

    public Contact_Info(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;

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
