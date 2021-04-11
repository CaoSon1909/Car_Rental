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
import java.io.PrintWriter;
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
public class SearchByNameServlet extends HttpServlet {

    private final String MAIN_PAGE = "main.jsp";

    private int getMaxRecord(String searchValue) throws SQLException, NamingException {
        TblCarDAO dao = new TblCarDAO();
        return dao.countLikeNames(searchValue);
    }

    private int getMaxPage(String searchValue) {
        int maxRecord = 0;
        int maxPage = 0;
        try {
            maxRecord = getMaxRecord(searchValue);
            maxPage = maxRecord / Constant.ROWS_IN_PAGE;
            if (maxRecord % Constant.ROWS_IN_PAGE != 0) {
                maxPage += 1;
            }
        } catch (SQLException ex) {
           log(ex.getMessage());
        } catch (NamingException ex) {
          log(ex.getMessage());
        }
        return maxPage;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = MAIN_PAGE;
        try {
            //search parameter
            String searchValue = request.getParameter("txtSearchValue");
            //Paging
            int currentPage = 1;
            //max page
            int maxPage = getMaxPage(searchValue);
            //page
            int page = (Integer) request.getAttribute("PAGE");
            //next page and previous page
            Integer previousPage = null;
            Integer nextPage = null;
            if (page != 1) {
                previousPage = page - 1;
            }
            if (page < maxPage) {
                nextPage = page + 1;
            }
            //set attribute for PRE_PAGE and NEXT_PAGE
            request.setAttribute("PRE_PAGE", previousPage);
            request.setAttribute("NEXT_PAGE", nextPage);

            //search result
            TblCarDAO dao = new TblCarDAO();
            List<TblCarDTO> list = dao.searchLikesName(searchValue, page);
            if (list != null) {
                request.setAttribute("CAR_LIST", list);
            }

            //load category
            TblCarCategoryDAO categoryDAO = new TblCarCategoryDAO();
            List<TblCarCategoryDTO> categoryList = categoryDAO.loadCategory();
            if (categoryList != null) {
                request.setAttribute("CATEGORY_LIST", categoryList);
            }

            request.getRequestDispatcher(url).forward(request, response);

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
