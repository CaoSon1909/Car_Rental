/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.tblCarCategory;

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
public class TblCarCategoryDAO implements Serializable{
    
    public List<TblCarCategoryDTO> loadCategory() throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblCarCategoryDTO> list = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "SELECT ID, name, description "
                        + "FROM tbl_CarCategory";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while(rs.next()){
                    if (list == null){
                        list = new Vector<TblCarCategoryDTO>();
                    }
                    int ID = rs.getInt("ID");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    
                    TblCarCategoryDTO dto = new TblCarCategoryDTO(ID, name, description);
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
}
