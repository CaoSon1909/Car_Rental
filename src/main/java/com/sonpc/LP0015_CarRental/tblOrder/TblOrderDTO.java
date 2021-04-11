/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblOrder;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblOrderDTO implements Serializable {

    private int ID;
    private float subTotal;
    private int status;
    private String email;
    private long startDate;
    private int discountID;
    private String dateString;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public TblOrderDTO() {
    }

    public TblOrderDTO(int ID, float subTotal, int status, String email, long startDate, int discountID) {
        this.ID = ID;
        this.subTotal = subTotal;
        this.status = status;
        this.email = email;
        this.startDate = startDate;
        this.discountID = discountID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }


}
