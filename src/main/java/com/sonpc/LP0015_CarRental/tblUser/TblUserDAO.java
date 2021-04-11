/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblUser;

import com.sonpc.LP0015_CarRental.requestobjects.UserRequestObject;
import com.sonpc.LP0015_CarRental.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.naming.NamingException;

/**
 *
 * @author ACER
 */
public class TblUserDAO implements Serializable {

    public TblUserDTO checkLogin(String email, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT fullname, role_id, status "
                        + "FROM tbl_User "
                        + "WHERE email = ? AND password = ? AND status = 1";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullname");
                    int role = rs.getInt("role_id");
                    int status = rs.getInt("status");

                    TblUserDTO dto = new TblUserDTO(email, password, fullName, role, status);
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean registerWithGoogleAccount(String email, String name) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tbl_User (email, password, fullname, role_id, status, phone_number, create_date, address) "
                        + "VALUES (?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, "default_password");
                ps.setString(3, name);
                ps.setInt(4, 0);
                ps.setInt(5, 1);
                ps.setString(6, "0123456789");
                ps.setLong(7, new Date().getTime());
                ps.setString(8, "fpt");
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

    public TblUserDTO loginWithGoogleAccount(String email, String name) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT email, password, fullname, role_id, status FROM tbl_User "
                        + "WHERE email = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String password = rs.getString("password");
                    int roleID = rs.getInt("role_id");
                    int status = rs.getInt("status");

                    TblUserDTO dto = new TblUserDTO(email, password, name, roleID, status);
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public List<TblUserDTO> getAllUsers() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblUserDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT email, password, fullname, role_id, status "
                        + "FROM tbl_User";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblUserDTO>();
                    }

                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String fullName = rs.getString("fullname");
                    int role = rs.getInt("role_id");
                    int status = rs.getInt("status");

                    TblUserDTO dto = new TblUserDTO(email, password, fullName, role, status);

                    list.add(dto);
                }
                return list;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean createNewAccount(UserRequestObject reqObj) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "INSERT INTO tbl_User (email, password, fullname, role_id, status, phone_number, create_date, address) "
                        + "VALUES (?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, reqObj.getEmail());
                ps.setString(2, reqObj.getPassword());
                ps.setString(3, reqObj.getFullName());
                //role_id mặc định là user (=0)
                ps.setInt(4, 0);
                //status mặc định là 0 khi chưa activate
                ps.setInt(5, 0);
                ps.setString(6, reqObj.getPhoneNumber());
                ps.setLong(7, new Date().getTime());
                ps.setString(8, reqObj.getAddress());
                
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

    public boolean activateNewAccount(String email) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "UPDATE tbl_User SET status = ? WHERE email = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, 1);
                ps.setString(2, email);
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
