/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblOrder;

import com.sonpc.LP0015_CarRental.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import javax.naming.NamingException;
import com.sonpc.LP0015_CarRental.conventions.Constant;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class TblOrderDAO implements Serializable {

    public boolean createNewOrder(String email, float subTotal, int status, long startDate, int discountID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tbl_Order (sub_total, status, user_email, start_date, discount_id) "
                        + "VALUES (?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setFloat(1, subTotal);
                ps.setInt(2, status);
                ps.setString(3, email);
                ps.setLong(4, startDate);
                ps.setInt(5, discountID);
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

    public TblOrderDTO getLastestOrderOfUser(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT TOP 1 ID, sub_total, status, user_email, start_date, discount_id "
                        + "FROM tbl_Order WHERE user_email = ? AND status = 1"
                        + "ORDER BY ID DESC";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("ID");
                    float subTotal = rs.getFloat("sub_total");
                    int status = rs.getInt("status");
                    String userEmail = rs.getString("user_email");
                    long startDate = rs.getLong("start_date");
                    int discountID = rs.getInt("discount_id");

                    TblOrderDTO dto = new TblOrderDTO(id, subTotal, status, email, startDate, discountID);

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

    public List<TblOrderDTO> getAllOrder(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblOrderDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, sub_total, status, user_email, start_date, discount_id "
                        + "FROM tbl_Order WHERE user_email = ? AND status = ? "
                        + "ORDER BY start_date ASC";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setInt(2, 1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblOrderDTO>();
                    }
                    int ID = rs.getInt("ID");
                    float subTotal = rs.getFloat("sub_total");
                    int status = rs.getInt("status");
                    long startDate = rs.getLong("start_date");
                    int discountID = rs.getInt("discount_id");

                    TblOrderDTO dto = new TblOrderDTO(ID, subTotal, status, email, startDate, discountID);

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

    public List<TblOrderDTO> searchOrderByCarName(String carName) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblOrderDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, sub_total, status, user_email, start_date, discount_id "
                        + "FROM tbl_Order "
                        + "WHERE ID in ( SELECT order_id "
                        + "FROM tbl_OrderDetail "
                        + "WHERE car_id in ( SELECT ID "
                        + "FROM tbl_Car "
                        + "WHERE name LIKE ?)) AND status = 1";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + carName + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblOrderDTO>();
                    }
                    int ID = rs.getInt("ID");
                    float subTotal = rs.getFloat("sub_total");
                    int status = rs.getInt("status");
                    String userEmail = rs.getString("user_email");
                    long startDate = rs.getLong("start_date");
                    int discountID = rs.getInt("discount_id");

                    TblOrderDTO dto = new TblOrderDTO(ID, subTotal, status, userEmail, startDate, discountID);
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

    private long formatDate(String dateString) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_PATTERN);
        Date date = format.parse(dateString);
        return date.getTime();
    }
    
    
    public List<TblOrderDTO> searchOrderByOrderDate(String dateString) throws NamingException, SQLException, ParseException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblOrderDTO> list = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "SELECT ID, sub_total, status, user_email, start_date, discount_id "
                        + "FROM tbl_Order WHERE start_date = ? AND status = ?";
                ps = con.prepareStatement(sql);
                long date = formatDate(dateString);
                ps.setLong(1, date);
                ps.setInt(2, 1);
                rs = ps.executeQuery();
                while (rs.next()){
                    if (list == null){
                        list = new Vector<TblOrderDTO>();
                    }
                    int ID = rs.getInt("ID");
                    float subTotal = rs.getFloat("sub_total");
                    int status = rs.getInt("status");
                    String userEmail = rs.getString("user_email");
                    long startDate = rs.getLong("start_date");
                    int discountID = rs.getInt("discount_id");
                    
                    TblOrderDTO dto = new TblOrderDTO(ID, subTotal, status, userEmail, startDate, discountID);
                    list.add(dto);
                }
                return list;
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

    public boolean deleteOrder(int orderID) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "UPDATE tbl_Order SET status = ? WHERE ID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, 0);
                ps.setInt(2, orderID);
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
