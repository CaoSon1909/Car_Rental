/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblCartDetail;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblCartDetailDTO implements Serializable{
    
    private int ID;
    private String email;
    private int carID;
    private int quantity;
    private float price;

    public TblCartDetailDTO() {
    }

    public TblCartDetailDTO(int ID, String email, int carID, int quantity, float price) {
        this.ID = ID;
        this.email = email;
        this.carID = carID;
        this.quantity = quantity;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    
    
}
