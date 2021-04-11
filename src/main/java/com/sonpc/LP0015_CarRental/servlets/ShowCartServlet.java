/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.tblCar.TblCarDAO;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDTO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDAO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDTO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author ACER
 */
public class ShowCartServlet extends HttpServlet {

    private final String SUM_OF_CART_SERVLET = "SumOfCartServlet";

    private TblUserDTO getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            TblUserDTO user = (TblUserDTO) session.getAttribute("USER_DTO");
            if (user != null) {
                return user;
            }
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = SUM_OF_CART_SERVLET;
        try {
            TblCartDetailDAO dao = new TblCartDetailDAO();
            TblUserDTO user = getUserFromSession(request);
            if (user != null) {
                List<TblCartDetailDTO> list = dao.getAllCartDetailOfUser(user.getEmail());
                if (list != null) {
                    //hash map để chứa foodID và currentQuantity
                    HashMap<TblCartDetailDTO, Integer> cartMap = new HashMap<TblCartDetailDTO, Integer>();
                    //hash map để chứa carId và carName
                    HashMap<Integer, String> carMap = new HashMap<Integer, String>();
                    TblCarDAO carDAO = new TblCarDAO();
                    for (TblCartDetailDTO dto : list) {
                        //Map <carID-carName>
                        int carID = dto.getCarID();
                        String carName = carDAO.getCarByCarID(carID).getName();
                        carMap.put(carID, carName);
                        //Map <cartDTO-current_Quantity>
                        TblCarDTO carDTO = carDAO.getCarByCarID(carID);
                        if (carDTO != null) {
                            int currenQuantity = carDTO.getCurrentQuantity();
                            cartMap.put(dto, currenQuantity);
                        }
                    }
                    request.setAttribute("CAR_MAP", carMap);
                    request.setAttribute("CART_MAP", cartMap);
                    request.setAttribute("CAR_IN_CART", list);
                }
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (NamingException ex) {
           log(ex.getMessage());
        } catch (SQLException ex) {
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
