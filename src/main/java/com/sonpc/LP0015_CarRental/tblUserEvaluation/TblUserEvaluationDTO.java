/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblUserEvaluation;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblUserEvaluationDTO implements Serializable{
    private int ID;
    private int carID;
    private String userEmail;
    private int scale;

    public TblUserEvaluationDTO() {
    }

    public TblUserEvaluationDTO(int ID, int carID, String userEmail, int scale) {
        this.ID = ID;
        this.carID = carID;
        this.userEmail = userEmail;
        this.scale = scale;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
    
    
}
