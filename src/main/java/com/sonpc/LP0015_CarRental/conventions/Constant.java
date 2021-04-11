/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.conventions;

/**
 *
 * @author ACER
 */
public interface Constant {
    String DATE_PATTERN = "dd/MM/yyyy";
    String DELIMETER = "=";
    int ROWS_IN_PAGE = 3;
    float VERIFICATION_TOKEN_EXPIRATION = (float) (60 * 24); //1 day
    int RANDOM_ACTIVATION_TOKEN_LENGTH = 5;
}
