/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.validators;

import com.sonpc.LP0015_CarRental.requestobjects.UserRequestObject;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDAO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDTO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author ACER
 */
public class UserValidator extends Validator<UserRequestObject> {
    
    public UserValidator(UserRequestObject object) {
        super(object);
    }
    
    public void checkUserEmail() {
        String email = object.getEmail();
        if (!email.matches("^[A-Za-z0-9_.]{2,}@[A-Za-z0-9]{2,}(.[A-Za-z0-9]{2,}){1,2}$")) {
            addError("EMAIL_REGEX_ERR", "Wrong email format(Ex: example@gmail.com)");
        }
    }
    
    public void checkPassword() {
        String password = object.getPassword();
        if (password.trim().length() == 0 || password.trim().length() < 8) {
            addError("PASSWORD_LENGTH_ERR", "Password must be at least 8 characters");
        }
    }
    
    public void checkConfirmPassword() {
        String confirmPassword = object.getConfirmPassword();
        String password = object.getPassword();
        
        if (!password.equals(confirmPassword)) {
            addError("CONFIRM_PASS_ERR", "Confirm password must match password");
        }
    }
    
    public void checkFullName() {
        String fullName = object.getFullName();
        if (fullName.trim().length() == 0) {
            addError("FULL_NAME_ERR", "Full name must not null");
        }
    }
    
    public void checkDuplicateEmail() throws NamingException, SQLException {
        String email = object.getEmail();
        TblUserDAO dao = new TblUserDAO();
        List<TblUserDTO> list = dao.getAllUsers();
        if (list != null) {
            for (TblUserDTO tblUserDTO : list) {
                if (tblUserDTO.getEmail().equals(email)) {
                    addError("DUPLICATE_EMAIL", "The email you input is already existed.");
                }
            }
        }
    }
    
    @Override
    public void validateObject() {
        try {
            this.checkUserEmail();
            this.checkPassword();
            this.checkConfirmPassword();
            this.checkFullName();
            this.checkDuplicateEmail();
        } catch (NamingException ex) {
            Logger.getLogger(UserValidator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
