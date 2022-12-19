package com.example.housing.Model;

import android.content.Intent;

import java.util.Date;

public class Due {

    private RentalData rentalData;

    private Integer id;
    private Double dueamount;
    private String duestatus;
    private Date duedate;
    private Double paidamount;
    private Double balanceamount;
    private Double paymentmode;



    public Due() {
    }

    public Due(Integer id, Double dueamount,
               String duestatus, Date duedate, Double paidamount, Double balanceamount, Double paymentmode) {
        this.id = id;
        this.dueamount = dueamount;
        this.duestatus = duestatus;
        this.duedate = duedate;
        this.paidamount = paidamount;
        this.balanceamount = balanceamount;
        this.paymentmode = paymentmode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDueamount() {
        return dueamount;
    }

    public void setDueamount(Double dueamount) {
        this.dueamount = dueamount;
    }

    public String getDuestatus() {
        return duestatus;
    }

    public void setDuestatus(String duestatus) {
        this.duestatus = duestatus;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public Double getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(Double paidamount) {
        this.paidamount = paidamount;
    }

    public Double getBalanceamount() {
        return balanceamount;
    }

    public void setBalanceamount(Double balanceamount) {
        this.balanceamount = balanceamount;
    }

    public Double getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(Double paymentmode) {
        this.paymentmode = paymentmode;
    }
}
