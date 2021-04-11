/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.tblOrder.TblOrderDAO;
import com.sonpc.LP0015_CarRental.tblOrder.TblOrderDTO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDTO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDAO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDTO;
import com.sonpc.LP0015_CarRental.tblOrderDetail.TblOrderDetailDAO;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDAO;
import com.sonpc.LP0015_CarRental.conventions.Constant;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
public class CheckoutServlet extends HttpServlet {

    private final String CONFIRM_SERVLET = "ConfirmServlet";
    
    //1:
    private boolean createNewOrderForUser(String email, float subTotal, int status, long startDate, int discountID) throws NamingException, SQLException {
        TblOrderDAO orderDAO = new TblOrderDAO();
        boolean result = orderDAO.createNewOrder(email, subTotal, status, startDate, discountID);
        if (result) {
            return true;
        }
        return false;
    }

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

    //2:
    private boolean createOrderDetail(String email) throws NamingException, SQLException {
        int count = 0;
        //1:get the created order
        TblOrderDAO orderDAO = new TblOrderDAO();
        TblOrderDTO orderDTO = orderDAO.getLastestOrderOfUser(email);
        //2: get orderID
        int orderID = orderDTO.getID();
        //3: get quantity, price, carID from tblCartDetail
        TblCartDetailDAO detailDAO = new TblCartDetailDAO();
        List<TblCartDetailDTO> list = detailDAO.getAllCartDetailOfUser(email);
        if (list != null) {
            for (TblCartDetailDTO detailDTO : list) {
                int quantity = detailDTO.getQuantity();
                float price = detailDTO.getPrice();
                int carID = detailDTO.getCarID();

                TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                boolean result = orderDetailDAO.createNewOrderDetail(orderID, carID, quantity, price);
                if (result){
                    boolean isCartDetailDeleted = deleteCartDetail(carID);
                    if (isCartDetailDeleted){
                        boolean isCurrentQuantityUpdated = updateCurrentQuantity(orderID, carID);
                        if (isCurrentQuantityUpdated){
                            count++;
                        }
                    }
                }
            }
        }
        if (count == 0){
            return false;
        }
        return true;
    }
    
    private boolean deleteCartDetail(int carID) throws NamingException, SQLException{
        TblCartDetailDAO dao = new TblCartDetailDAO();
        boolean result = dao.deleteCart(carID);
        return result;
    }
    
    private boolean updateCurrentQuantity(int orderID, int carID) throws NamingException, SQLException{
        TblCarDAO dao = new TblCarDAO();
        boolean result =  dao.updateQuantityAfterCheckout(orderID, carID);
        return result;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = CONFIRM_SERVLET;
        try {
            //lấy parameter
            String email = getUserEmail(request);
            String startDateString = request.getParameter("txtStartDate");
            String subTotalString = request.getParameter("txtSubTotal");
            String discountCodeString = request.getParameter("txtDiscount");
            //
            SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_PATTERN);
            long startDate = format.parse(startDateString).getTime();

            float subTotal = Float.parseFloat(subTotalString);

            int discountID = 1;
            try {
                discountID = Integer.parseInt(discountCodeString);
            } catch (NumberFormatException ex) {
                discountID = 1;
            }

            //1: tạo ra order trước
            boolean createNewOrder = createNewOrderForUser(email, subTotal, 1, startDate, discountID);
            //2: tạo ra order detail dựa vào cart detail
            if (createNewOrder) {
                boolean createNewOrderDetail = createOrderDetail(email);
                if (createNewOrderDetail) {
                    request.setAttribute("SUCCESS_MESSAGE", "Check out success! Thank you");
                }
            }

        } catch (NamingException ex) {
            log(ex.getMessage());
        } catch (SQLException ex) {
            log(ex.getMessage());
        } catch (ParseException ex) {
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
