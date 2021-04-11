/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.tblCar.TblCarDAO;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDTO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ACER
 */
public class UpdateQuantityServlet extends HttpServlet {

    private final String SHOW_CART_SERVLET = "ShowCartServlet";

    private boolean isQuantityValid(int quantity, int carID) throws SQLException, NamingException {
        TblCarDAO dao = new TblCarDAO();
        TblCarDTO dto = dao.getCarByCarID(carID);
        int currentQuantity = dto.getCurrentQuantity();
        if (currentQuantity <= 0) {
            return false;
        }
        if (quantity <= 0) {
            return false;
        }
        if (quantity > currentQuantity) {
            return false;
        }
        return true;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = "";
        try {
            //get parameters
            String cartIDString = request.getParameter("txtCartID");
            String quantityString = request.getParameter("txtQuantity");
            String carIDString = request.getParameter("txtCarID");
            //parsing
            int cartID = Integer.parseInt(cartIDString);
            int quantity = Integer.parseInt(quantityString);
            int carID = Integer.parseInt(carIDString);
            //Kiểm tra quantity vs current quantity của tbl_Car

            boolean isQuantityValid = isQuantityValid(quantity, carID);

            if (isQuantityValid) {
                TblCartDetailDAO detailDAO = new TblCartDetailDAO();
                boolean result = detailDAO.updateQuantity(cartID, quantity, carID);
                if (result) {
                    url = SHOW_CART_SERVLET;
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }

        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
           log(ex.getMessage());
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
