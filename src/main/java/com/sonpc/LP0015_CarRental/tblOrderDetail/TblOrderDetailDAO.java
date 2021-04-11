/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblOrderDetail;

import com.sonpc.LP0015_CarRental.utils.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.naming.NamingException;

/**
 *
 * @author ACER
 */
public class TblOrderDetailDAO implements Serializable {

    public boolean createNewOrderDetail(int orderID, int carID, int quantity, float price) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tbl_OrderDetail (order_id, car_id, quantity, price) VALUES (?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, orderID);
                ps.setInt(2, carID);
                ps.setInt(3, quantity);
                ps.setFloat(4, price);
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

    public TblOrderDetailDTO getOrderDetailByOrderIDAndCarID(int orderID, int carID) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, order_id, car_id, quantity, price FROM tbl_OrderDetail WHERE order_id = ? AND car_id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, orderID);
                ps.setInt(2, carID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int ID = rs.getInt("ID");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");

                    TblOrderDetailDTO dto = new TblOrderDetailDTO(ID, orderID, carID, quantity, price);

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

    public List<TblOrderDetailDTO> getOrderDetailByOrderID(int orderID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblOrderDetailDTO> list = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "SELECT ID, order_id, car_id, quantity, price "
                        + "FROM tbl_OrderDetail "
                        + "WHERE order_id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, orderID);
                rs = ps.executeQuery();
                while (rs.next()){
                    if (list == null){
                        list = new Vector<TblOrderDetailDTO>();
                    }
                    int ID = rs.getInt("ID");
                    int carID = rs.getInt("car_id");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    
                    TblOrderDetailDTO dto = new TblOrderDetailDTO(ID, orderID, carID, quantity, price);
                    
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
                ps.close();
            }
        }
        return null;
    }

}
