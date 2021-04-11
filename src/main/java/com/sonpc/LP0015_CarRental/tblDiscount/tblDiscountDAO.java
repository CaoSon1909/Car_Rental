/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblDiscount;

import com.sonpc.LP0015_CarRental.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author ACER
 */
public class tblDiscountDAO implements Serializable {

    public TblDiscountDTO getDiscountCodeByID(int id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "SELECT ID, description, amount, status, expire_date "
                        + "FROM tbl_Discount "
                        + "WHERE ID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()){
                    String description = rs.getString("description");
                    float amount = rs.getFloat("amount");
                    int status = rs.getInt("status");
                    long expiredDate = rs.getLong("expire_date");
                    TblDiscountDTO dto = new TblDiscountDTO(id, description, amount, status, expiredDate);
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

    public boolean updateStatusToUnavailable(int id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "UPDATE tbl_Discount SET status = ? WHERE ID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, 0);
                ps.setInt(2, id);
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

    public boolean updateStatusToAvailable(int id) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "UPDATE tbl_Discount SET status = ? WHERE ID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, 1);
                ps.setInt(2, id);
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
    
}
