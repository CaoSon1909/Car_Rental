/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblRegistrationToken;

import com.sonpc.LP0015_CarRental.conventions.Constant;
import com.sonpc.LP0015_CarRental.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
import net.bytebuddy.utility.RandomString;

/**
 *
 * @author ACER
 */
public class TblRegistrationTokenDAO implements Serializable {

    public boolean generateNewTokenString(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        RandomString random = new RandomString(Constant.RANDOM_ACTIVATION_TOKEN_LENGTH);
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tbl_RegistrationToken (create_date, expire_date, token_string, user_email, status) "
                        + "VALUES (?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setLong(1, new Date().getTime());
                ps.setLong(2, (long) (new Date().getTime() + Constant.VERIFICATION_TOKEN_EXPIRATION));
                ps.setString(3, random.nextString());
                ps.setString(4, email);
                ps.setInt(5, 1);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    private boolean updateStatusTokenString(String email) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "UPDATE tbl_RegistrationToken SET status = 0 "
                        + "WHERE email = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                int row = ps.executeUpdate();
                if (row > 0){
                    return true;
                }
            }
        }
        finally{
            if (ps != null){
                ps.close();
            }
            if (con != null){
                con.close();
            }
        }
        return false;
    }

    public TblRegistrationTokenDTO getTheLastestTokenStringOfUser(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "SELECT TOP 1 ID, create_date, expire_date, token_string, user_email, status FROM tbl_RegistrationToken "
                        + "WHERE user_email = ? "
                        + "ORDER BY ID DESC";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()){
                    int id = rs.getInt("ID");
                    long createDate = rs.getLong("create_date");
                    long expireDate = rs.getLong("expire_date");
                    String tokenString = rs.getString("token_string");
                    String userEmail = rs.getString("user_email");
                    int status = rs.getInt("status");
                    
                    TblRegistrationTokenDTO dto = new TblRegistrationTokenDTO(id, createDate, expireDate, tokenString, userEmail, status);
                    
                    return dto;
                }
            }
        }
        finally{
            if (rs != null){
                rs.close();
            }
            if (ps != null){
                ps.close();
            }
        }
        return null;
    }

    public TblRegistrationTokenDTO getTokenString(String email, String activationCode) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "SELECT ID, create_date, expire_date, token_string, user_email, status FROM "
                        + "tbl_RegistrationToken WHERE "
                        + "user_email = ? AND token_string = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, activationCode);
                
                rs = ps.executeQuery();
                if (rs.next()){
                    int ID = rs.getInt("ID");
                    long createDate = rs.getLong("create_date");
                    long expireDate = rs.getLong("expire_date");
                    int status = rs.getInt("status");
                    TblRegistrationTokenDTO dto = new TblRegistrationTokenDTO(ID, createDate, expireDate, activationCode, email, status);
                    return dto;
                }
            }
        }
        finally{
            if (rs != null){
                rs.close();
            }
            if (ps != null){
                ps.close();
            }
            if (con != null){
                con.close();
            }
        }
        return null;
    }

}
