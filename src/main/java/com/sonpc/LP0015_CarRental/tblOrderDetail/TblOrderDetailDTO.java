/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblOrderDetail;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblOrderDetailDTO implements Serializable{
    
    private int id;
    private int orderID;
    private int carID;
    private int quantity;
    private float price;

    public TblOrderDetailDTO() {
    }

    public TblOrderDetailDTO(int id, int orderID, int carID, int quantity, float price) {
        this.id = id;
        this.orderID = orderID;
        this.carID = carID;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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
