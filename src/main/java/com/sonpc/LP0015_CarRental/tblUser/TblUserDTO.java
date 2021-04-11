/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblUser;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class TblUserDTO implements Serializable {

    private String email;
    private String password;
    private String fullname;
    private int role;
    private int status;

    public TblUserDTO() {
    }

    public TblUserDTO(String email, String password, String fullname, int role, int status) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
