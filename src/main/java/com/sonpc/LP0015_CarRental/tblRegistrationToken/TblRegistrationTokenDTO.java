/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblRegistrationToken;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblRegistrationTokenDTO implements Serializable{
    
    private int ID; 
    private long createDate;
    private long expiredDate;
    private String tokenString;
    private String userEmail;
    private int status;

    public TblRegistrationTokenDTO() {
    }

    public TblRegistrationTokenDTO(int ID, long createDate, long expiredDate, String tokenString, String userEmail, int status) {
        this.ID = ID;
        this.createDate = createDate;
        this.expiredDate = expiredDate;
        this.tokenString = tokenString;
        this.userEmail = userEmail;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(long expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

  

   
    
    
}
