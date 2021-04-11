/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.validators;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ACER
 */
public abstract class Validator<T> {
    
    
    Map<String, String> errorMap = new HashMap<String, String>();
    T object;

    public Validator(T object) {
        this.object = object;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
    
    public boolean hasError(){
        return !errorMap.isEmpty();
    }
    
    public void addError(String errorName, String errorMessage){
        errorMap.put(errorName, errorMessage);
    }
    
    public abstract void validateObject();
}
