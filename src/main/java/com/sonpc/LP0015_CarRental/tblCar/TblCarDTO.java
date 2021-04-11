/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblCar;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblCarDTO implements Serializable {

    private int ID;
    private String name;
    private String description;
    private String color;
    private float price;
    private long createYear;
    private int currentQuantity;
    private int categoryID;
    private int status;

    public TblCarDTO() {
    }

    public TblCarDTO(int ID, String name, String description, String color, float price, long createYear, int currentQuantity, int categoryID, int status) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.color = color;
        this.price = price;
        this.createYear = createYear;
        this.currentQuantity = currentQuantity;
        this.categoryID = categoryID;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getCreateYear() {
        return createYear;
    }

    public void setCreateYear(long createYear) {
        this.createYear = createYear;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
