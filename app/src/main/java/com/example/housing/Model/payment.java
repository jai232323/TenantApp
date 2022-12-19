package com.example.housing.Model;

import android.content.Intent;

public class payment {

    private Intent id;
    private Double paymode;
    private Double paidamount;
    private Integer dueid;
    private String payStatus;

    public payment() {
    }

    public payment(Intent id, Double paymode, Double paidamount, Integer dueid, String payStatus) {
        this.id = id;
        this.paymode = paymode;
        this.paidamount = paidamount;
        this.dueid = dueid;
        this.payStatus = payStatus;
    }

    public Intent getId() {
        return id;
    }

    public void setId(Intent id) {
        this.id = id;
    }

    public Double getPaymode() {
        return paymode;
    }

    public void setPaymode(Double paymode) {
        this.paymode = paymode;
    }

    public Double getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(Double paidamount) {
        this.paidamount = paidamount;
    }

    public Integer getDueid() {
        return dueid;
    }

    public void setDueid(Integer dueid) {
        this.dueid = dueid;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
