/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblCar;

import com.sonpc.LP0015_CarRental.conventions.Constant;
import com.sonpc.LP0015_CarRental.utils.DBHelpers;
import com.sonpc.LP0015_CarRental.tblOrderDetail.TblOrderDetailDAO;
import com.sonpc.LP0015_CarRental.tblOrderDetail.TblOrderDetailDTO;
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
public class TblCarDAO implements Serializable {

//    //1. biến private static dao
//    private static TblCarDAO dao;
//
//    //2. 1 cóntructor
//    private TblCarDAO() {
//    }
//    
    
    public List<TblCarDTO> getAllCars(int currentPage) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblCarDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, name, description, color, price, "
                        + "create_year, current_quantity, category_id, status "
                        + "FROM tbl_Car "
                        + "WHERE status = 1 and current_quantity > 0 "
                        + "ORDER BY create_year "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                ps = con.prepareStatement(sql);
                ps.setInt(1, (currentPage - 1) * Constant.ROWS_IN_PAGE);
                ps.setInt(2, Constant.ROWS_IN_PAGE);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblCarDTO>();
                    }
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String color = rs.getString("color");
                    float price = rs.getFloat("price");
                    long createYear = rs.getLong("create_year");
                    int currentQuantity = rs.getInt("current_quantity");
                    int categoryID = rs.getInt("category_id");
                    int status = rs.getInt("status");

                    TblCarDTO dto = new TblCarDTO(id, name, description, color, price, createYear, currentQuantity, categoryID, status);
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

    public int countAllCars() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(ID) AS count "
                        + "FROM tbl_Car WHERE status = 1 AND current_quantity > 0";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count;
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

    public List<TblCarDTO> searchLikesName(String searchValue, int currentPage) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblCarDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, name, description, color, price, "
                        + "create_year, current_quantity, category_id, status "
                        + "FROM tbl_Car "
                        + "WHERE status = 1 AND name LIKE ? AND current_quantity > 0 "
                        + "ORDER BY create_year "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + searchValue + "%");
                ps.setInt(2, (currentPage - 1) * Constant.ROWS_IN_PAGE);
                ps.setInt(3, Constant.ROWS_IN_PAGE);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblCarDTO>();
                    }
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String color = rs.getString("color");
                    float price = rs.getFloat("price");
                    long createYear = rs.getLong("create_year");
                    int currentQuantity = rs.getInt("current_quantity");
                    int categoryID = rs.getInt("category_id");
                    int status = rs.getInt("status");

                    TblCarDTO dto = new TblCarDTO(id, name, description, color, price, createYear, currentQuantity, categoryID, status);
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

    public int countLikeNames(String searchValue) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(ID) AS count "
                        + "FROM tbl_Car "
                        + "WHERE status = 1 AND name LIKE ? AND current_quantity > 0";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + searchValue + "%");
                rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count;
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

    public List<TblCarDTO> searchLikeCategoryAndYear(int categoryID, long year, int currentPage) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblCarDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, name, description, color, price, "
                        + "create_year, current_quantity, category_id, status "
                        + "FROM tbl_Car "
                        + "WHERE status = 1 AND category_id = ? AND current_quantity > 0 AND create_year = ? "
                        + "ORDER BY create_year "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                ps = con.prepareStatement(sql);
                ps.setInt(1, categoryID);
                ps.setLong(2, year);
                ps.setInt(3, (currentPage - 1) * Constant.ROWS_IN_PAGE);
                ps.setInt(4, Constant.ROWS_IN_PAGE);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new Vector<TblCarDTO>();
                    }
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String color = rs.getString("color");
                    float price = rs.getFloat("price");
                    int currentQuantity = rs.getInt("current_quantity");
                    int status = rs.getInt("status");

                    TblCarDTO dto = new TblCarDTO(id, name, description, color, price, year, currentQuantity, categoryID, status);
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

    public int countLikeCategoryAndYear(int categoryID, long year) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(ID) AS count "
                        + "FROM tbl_Car "
                        + "WHERE status = 1 AND category_id = ? AND current_quantity > 0 AND create_year = ? ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, categoryID);
                ps.setLong(2, year);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count;
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

    public TblCarDTO getCarByCarID(int car_id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT ID, name, description, color, price, "
                        + "create_year, current_quantity, category_id, status "
                        + "FROM tbl_Car "
                        + "WHERE ID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, car_id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int ID = rs.getInt("ID");
                    String name = rs.getString("name");
                    String des = rs.getString("description");
                    String color = rs.getString("color");
                    float price = rs.getFloat("price");
                    long year = rs.getLong("create_year");
                    int quantity = rs.getInt("current_quantity");
                    int categoryID = rs.getInt("category_id");
                    int status = rs.getInt("status");

                    TblCarDTO dto = new TblCarDTO(ID, name, des, color, price, year, quantity, categoryID, status);
                    return dto;
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
        return null;
    }

    public boolean updateQuantityAfterCheckout(int orderID, int carID) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        int quantity = 0;
        int currentQuantity = 0;
        int newQuantity = 0;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {

                //get Order detail dto => get quantity
                TblOrderDetailDAO dao = new TblOrderDetailDAO();
                TblOrderDetailDTO dto = dao.getOrderDetailByOrderIDAndCarID(orderID, carID);
                if (dto != null) {
                    quantity = dto.getQuantity();
                }
                //get food dto => get currentQuantity
                TblCarDAO carDAO = new TblCarDAO();
                TblCarDTO carDTO = carDAO.getCarByCarID(carID);
                if (carDTO != null) {
                    currentQuantity = carDTO.getCurrentQuantity();
                }
                //quantity mới = currentQuantity - quantity
                newQuantity = currentQuantity - quantity;
                if (newQuantity > 0) {
                    //new Quantity ko đc <= 0
                    String sql = "UPDATE tbl_Car SET current_quantity = ? WHERE ID = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, newQuantity);
                    ps.setInt(2, carID);

                    int row = ps.executeUpdate();
                    if (row > 0) {
                        return true;
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
}
