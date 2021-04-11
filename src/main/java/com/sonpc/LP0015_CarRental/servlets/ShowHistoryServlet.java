/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.conventions.Constant;
import com.sonpc.LP0015_CarRental.tblOrder.TblOrderDAO;
import com.sonpc.LP0015_CarRental.tblOrder.TblOrderDTO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class ShowHistoryServlet extends HttpServlet {

    private final String HISTORY_PAGE = "history.jsp";

    private String getUserEmail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            TblUserDTO user = (TblUserDTO) session.getAttribute("USER_DTO");
            if (user != null) {
                String email = user.getEmail();
                return email;
            }
        }
        return null;
    }

    private List<TblOrderDTO> getAllOrderOfUser(String email) throws NamingException, SQLException {
        TblOrderDAO dao = new TblOrderDAO();
        List<TblOrderDTO> list = dao.getAllOrder(email);
        if (list != null) {
            return list;
        }
        return null;
    }

    private String getDateFormatAtString(TblOrderDTO order) {
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_PATTERN);
        long date = order.getStartDate();
        Date javaDate = new Date(date);
        String dateString = format.format(javaDate);
        return dateString;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = HISTORY_PAGE;
        try {
            String email = getUserEmail(request);
            List<TblOrderDTO> list = getAllOrderOfUser(email);
            String dateString = "";
            if (list != null) {
                for (TblOrderDTO tblOrderDTO : list) {
                    dateString = getDateFormatAtString(tblOrderDTO);
                    tblOrderDTO.setDateString(dateString);
                }
                request.setAttribute("ORDER_LIST", list);
            }
        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
           log(ex.getMessage());
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
