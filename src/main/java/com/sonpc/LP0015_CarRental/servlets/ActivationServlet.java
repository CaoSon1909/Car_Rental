/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.tblRegistrationToken.TblRegistrationTokenDAO;
import com.sonpc.LP0015_CarRental.tblRegistrationToken.TblRegistrationTokenDTO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
public class ActivationServlet extends HttpServlet {

    private final String ACTIVATION_SUCCESS_PAGE = "activation-sucess.html";
    private final String ACTIVATION_PAGE = "activation.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = ACTIVATION_SUCCESS_PAGE;
        try {
            String email = request.getParameter("txtUserEmail");
            String activationCode = request.getParameter("txtActivation");

            //1: Lấy registrationTokenDTO dựa vào email và activationCode
            TblRegistrationTokenDAO registrationTokenDAO = new TblRegistrationTokenDAO();
            TblRegistrationTokenDTO registrationTokenDTO = registrationTokenDAO.getTokenString(email, activationCode);

            //2:Kiểm tra xem activation code còn tồn tại ko (status != 1 || now > expire_date)
            if (registrationTokenDTO != null) {
                int status = registrationTokenDTO.getStatus();
                long expireDate = registrationTokenDTO.getExpiredDate();
                long now = new Date().getTime();

                if (status != 1) {
                    url = ACTIVATION_PAGE;
                    request.setAttribute("UNAVAILABLE_MESSAGE", "Activation code is unavaiable!");
                } else {
                    if (now > expireDate) {
                        url = ACTIVATION_PAGE;
                        request.setAttribute("STATUS_EXPIRED_MESSAGE", "Activation code has expired!");
                    }
                    //activate success
                    TblUserDAO userDAO = new TblUserDAO();
                    boolean result = userDAO.activateNewAccount(email);
                    if (result) {
                        url = ACTIVATION_SUCCESS_PAGE;
                    }
                    request.getRequestDispatcher(url).forward(request, response);
                }
            } else {
                url = ACTIVATION_PAGE;
                request.setAttribute("INCORRECT_ACTIVATION_CODE", "Activation code is incorrect.");
                request.getRequestDispatcher(url).forward(request, response);
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
