/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblDiscount;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblDiscountDTO implements Serializable {

    private int ID;
    private String description;
    private float amount;
    private int status;
    private long expiredDate;

    public TblDiscountDTO() {
    }

    public TblDiscountDTO(int ID, String description, float amount, int status, long expiredDate) {
        this.ID = ID;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.expiredDate = expiredDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(long expiredDate) {
        this.expiredDate = expiredDate;
    }

}
