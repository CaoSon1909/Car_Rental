/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.tblUserEvaluation.TblUserEvaluationDAO;
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

/**
 *
 * @author ACER
 */
public class RatingServlet extends HttpServlet {

    private final String SHOW_FEED_BACK_SERVLET = "ShowFeedbackPageServlet";

    private boolean createNewFeedback(String email, int carID, int scale) throws NamingException, SQLException {
        TblUserEvaluationDAO dao = new TblUserEvaluationDAO();
        boolean result = dao.createNewEvaluation(email, carID, scale);
        if (result) {
            return true;
        }
        return false;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = SHOW_FEED_BACK_SERVLET;
        try {
            String email = request.getParameter("txtEmail");
            int carID = Integer.parseInt(request.getParameter("txtCarID"));
            int scale = Integer.parseInt(request.getParameter("txtScale"));
            String orderIDString = request.getParameter("txtOrderID");
            
            
            boolean result = createNewFeedback(email, carID, scale);
            if (result) {
                request.setAttribute("MESSAGE", "You feedback has been saved");
            }
            request.setAttribute("CAR_ID", carID);
            request.setAttribute("EMAIL", email);
            request.setAttribute("ORDER_ID", orderIDString);
        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
            log(ex.getMessage());
        } catch (NumberFormatException ex) {
            
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
