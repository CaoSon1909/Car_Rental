/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.conventions.Constant;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDAO;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDTO;
import com.sonpc.LP0015_CarRental.tblCarCategory.TblCarCategoryDAO;
import com.sonpc.LP0015_CarRental.tblCarCategory.TblCarCategoryDTO;
import java.io.IOException;
import java.sql.SQLException;
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
public class MainServlet extends HttpServlet {

    private final String MAIN_PAGE = "main.jsp";

    private int getMaxRecord() throws NamingException, SQLException {
        TblCarDAO dao = new TblCarDAO();
        return dao.countAllCars();
    }

    private int getMaxPage() throws NamingException, SQLException {
        int maxRecord = getMaxRecord();

        int maxPage = maxRecord / Constant.ROWS_IN_PAGE;

        if (maxRecord % Constant.ROWS_IN_PAGE != 0) {
            maxPage += 1;
        }
        return maxPage;
    }

    private int getPage(HttpServletRequest request) {
        String pageString = request.getParameter("page");
        int page = 1;
        if (pageString != null) {
            try {
                page = Integer.parseInt(pageString);
            } catch (NumberFormatException ex) {
                log(ex.getMessage());
            }
        }
        return page;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = MAIN_PAGE;
        try {
            //max Page
            int maxPage = getMaxPage();
            //page
            int page = getPage(request);
            //previous page and next page
            Integer previousPage = null;
            Integer nextPage = null;
            //conditions
            if (page != 1) {
                previousPage = page - 1;
            }
            if (page < maxPage) {
                nextPage = page + 1;
            }
            //load first 20 cars
            TblCarDAO carDAO = new TblCarDAO();
            List<TblCarDTO> carList = carDAO.getAllCars(page);
            if (carList != null) {
                request.setAttribute("CAR_LIST", carList);
            } else {
                previousPage = null;
                nextPage = null;
            }
            //set attribute for previous page + next page
            request.setAttribute("PRE_PAGE", previousPage);
            request.setAttribute("NEXT_PAGE", nextPage);
            request.setAttribute("PAGE", page);

            //load category
            TblCarCategoryDAO categoryDAO = new TblCarCategoryDAO();
            List<TblCarCategoryDTO> categoryList = categoryDAO.loadCategory();
            if (categoryList != null) {
                request.setAttribute("CATEGORY_LIST", categoryList);
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
