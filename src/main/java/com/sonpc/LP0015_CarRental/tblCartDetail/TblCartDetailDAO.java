/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblCartDetail;

import com.sonpc.LP0015_CarRental.tblCar.TblCarDAO;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDTO;
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
public class TblCartDetailDAO implements Serializable {

    public List<TblCartDetailDTO> getAllCartDetailOfUser(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblCartDetailDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, user_email, car_id, quantity, price FROM tbl_CartDetail "
                        + "WHERE user_email = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblCartDetailDTO>();
                    }
                    int id = rs.getInt("ID");
                    String userEmail = rs.getString("user_email");
                    int carID = rs.getInt("car_id");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");

                    TblCartDetailDTO dto = new TblCartDetailDTO(id, userEmail, carID, quantity, price);

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

    public boolean checkExistedCarInCart(String email, int carID) throws NamingException, SQLException {
        List<TblCartDetailDTO> list = getAllCartDetailOfUser(email);
        if (list != null) {
            for (TblCartDetailDTO tblCartDetailDTO : list) {
                if (tblCartDetailDTO.getEmail().equals(email) && tblCartDetailDTO.getCarID() == carID) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addToCartLater(String email, int carID, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "UPDATE tbl_CartDetail SET quantity = ? WHERE user_email = ? AND car_id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, quantity);
                ps.setString(2, email);
                ps.setInt(3, carID);
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

    public boolean addToCartFirstTime(String email, int carID, int quantity, float price) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                String sql = "INSERT INTO tbl_CartDetail (user_email, car_id, quantity, price) VALUES (?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
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

    public int getCurrentQuantity(String email, int carID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT quantity FROM tbl_CartDetail WHERE user_email = ? AND car_id = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setInt(2, carID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    return quantity;
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
        return 0;
    }

    public List<TblCartDetailDTO> showCustomerCart(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblCartDetailDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, user_email, car_id, quantity, price FROM tbl_CartDetail WHERE user_email = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblCartDetailDTO>();
                    }
                    int ID = rs.getInt("ID");
                    int carID = rs.getInt("car_id");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");

                    TblCartDetailDTO dto = new TblCartDetailDTO(ID, email, carID, quantity, price);

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

    public boolean deleteCart(int carID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "DELETE tbl_CartDetail WHERE car_id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, carID);
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

    public boolean updateQuantity(int cartID, int quantity, int car_id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int currentQuantity = 0;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                TblCarDAO carDAO = new TblCarDAO();
                TblCarDTO car = carDAO.getCarByCarID(car_id);
                if (car != null) {
                    currentQuantity = car.getCurrentQuantity();
                    if (quantity < currentQuantity) {
                        String sql = "UPDATE tbl_CartDetail SET quantity = ? WHERE ID = ?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, quantity);
                        ps.setInt(2, cartID);
                        int row = ps.executeUpdate();
                        if (row > 0) {
                            return true;
                        }
                    }
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
    
        public TblCartDetailDTO getCartByEmailAndCarID(String userEmail, int carID) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "SELECT ID, user_email, car_id, quantity, price FROM tbl_CartDetail WHERE user_email = ? AND car_id = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userEmail);
                ps.setInt(2, carID);
                rs = ps.executeQuery();
                if (rs.next()){
                    int id = rs.getInt("ID");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    
                    TblCartDetailDTO dto = new TblCartDetailDTO(id, userEmail, carID, quantity, price);
                    
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
