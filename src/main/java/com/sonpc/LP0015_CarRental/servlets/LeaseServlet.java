/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.tblCar.TblCarDAO;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDTO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDAO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ACER
 */
public class LeaseServlet extends HttpServlet {

    private final String MAIN_SERVLET = "main";

    private boolean isCarExistedInCart(String email, int carID) throws SQLException, NamingException {
        TblCartDetailDAO dao = new TblCartDetailDAO();
        return dao.checkExistedCarInCart(email, carID);
    }

    private boolean addNewCarToCart(String email, int carID) throws NamingException, SQLException {
        int quantity = 1; //default quantity when adding new car is 1
        TblCarDAO dao = new TblCarDAO();
        TblCarDTO car = dao.getCarByCarID(carID);
        if (car != null) {
            TblCartDetailDAO detailDAO = new TblCartDetailDAO();
            boolean result = detailDAO.addToCartFirstTime(email, carID, quantity, car.getPrice());
            if (result) {
                return true;
            }
        }
        return false;
    }

    private boolean updateQuantityInCart(String email, int carID) throws SQLException, NamingException {
        TblCartDetailDAO detailDAO = new TblCartDetailDAO();
        int currentQuantityInCart = detailDAO.getCurrentQuantity(email, carID);
        boolean result = detailDAO.addToCartLater(email, carID, ++currentQuantityInCart);
        if (result) {
            return true;
        }
        return false;
    }

    private int getCarID(HttpServletRequest request) {
        try {
            String carIDString = request.getParameter("txtCarID");
            int carID = Integer.parseInt(carIDString);
            return carID;
        } catch (NumberFormatException ex) {
            return 0;
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        boolean isExisted = false;
        try {
            //get carID from request object
            int carID = getCarID(request);
            //check if car is existed in cart?
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUserDTO user = (TblUserDTO) session.getAttribute("USER_DTO");
                if (user != null) {
                    String email = user.getEmail();
                    if (isCarExistedInCart(email, carID)) {
                        isExisted = true;
                    }
                    //
                    if (isExisted) {
                        boolean updateResult = updateQuantityInCart(email, carID);
                    } else {
                        boolean addNewResult = addNewCarToCart(email, carID);
                    }
                    String url = MAIN_SERVLET;
                    response.sendRedirect(url);
                }
            }

        } catch (SQLException ex) {
            log(ex.getMessage());
        } catch (NamingException ex) {
            log(ex.getMessage());
        } finally {
            out.close();
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
