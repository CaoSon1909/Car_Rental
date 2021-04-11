/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ACER
 */
public class MainSearchServlet extends HttpServlet {

    private final String SEARCH_ALL_CARS_SERVLET = "SearchAllCarsServlet";
    private final String SEARCH_BY_NAME_SERVLET = "SearchByNameServlet";
    private final String SEARCH_BY_CATEGORY_AND_RENTAL_DATE = "SearchByCategoryAndRentalDate";
    

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
        try {
            String url = "";
            String searchType = request.getParameter("txtSearchType");
            if (searchType.isEmpty()) {
                //lần đầu load chưa search gì thì cho searchType là search All
                searchType = "search_all";
            }
            if (searchType != null) {
                int page = getPage(request);
                request.setAttribute("PAGE", page);
                if (searchType.equals("search-by-name")) {
                    url = SEARCH_BY_NAME_SERVLET;
                    request.getRequestDispatcher(url).forward(request, response);
                }
                if (searchType.equals("search-by-category-and-rental-date")) {
                    url = SEARCH_BY_CATEGORY_AND_RENTAL_DATE;
                    request.getRequestDispatcher(url).forward(request, response);
                }
                if (searchType.equals("search_all")) {
                    url = SEARCH_ALL_CARS_SERVLET;
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
        } finally {

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
