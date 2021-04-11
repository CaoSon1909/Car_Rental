/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.conventions.Constant;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDAO;
import com.sonpc.LP0015_CarRental.tblCar.TblCarDTO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDAO;
import com.sonpc.LP0015_CarRental.tblCartDetail.TblCartDetailDTO;
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
public class ConfirmServlet extends HttpServlet {

    private final String CONFIRM_TO_CHECKOUT_PAGE = "confirm.jsp";
    private final String SHOW_CART_SERVLET = "ShowCartServlet";

    private String getUserEmail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            TblUserDTO user = (TblUserDTO) session.getAttribute("USER_DTO");
            if (user != null) {
                return user.getEmail();
            }
        }
        return null;
    }

    //1
    private List<TblCartDetailDTO> getAllCartdetailOfUser(String email) throws NamingException, SQLException {
        TblCartDetailDAO dao = new TblCartDetailDAO();
        List<TblCartDetailDTO> list = dao.getAllCartDetailOfUser(email);
        if (list != null) {
            return list;
        }
        return null;
    }

    //2:
    private int getIdOfUnavailableCar(List<TblCartDetailDTO> list) throws NamingException, SQLException {
        for (TblCartDetailDTO detailDTO : list) {
            int carID = detailDTO.getCarID();
            TblCarDAO carDAO = new TblCarDAO();
            TblCarDTO car = carDAO.getCarByCarID(carID);
            if (car.getStatus() == 0
                    || car.getCurrentQuantity() <= 0
                    || detailDTO.getQuantity() > car.getCurrentQuantity()) {
                return car.getID();
            }
        }
        return 0;
    }

    private String getTodayTime() {
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_PATTERN);
        java.util.Date date = new Date();
        String dateString = format.format(date);
        return dateString;
    }

    private float getTotalOfCart(String email) throws NamingException, SQLException {
        List<TblCartDetailDTO> listCartDetail = getAllCartdetailOfUser(email);
        float total = 0;
        float sum = 0;
        if (listCartDetail != null) {
            for (TblCartDetailDTO detailDTO : listCartDetail) {
                int quantity = detailDTO.getQuantity();
                float price = detailDTO.getPrice();
                total = quantity * price;
                sum = sum + (total);
            }
            return sum;
        }
        return 0;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = CONFIRM_TO_CHECKOUT_PAGE;
        try {
            //1:show lại cart của user
            //2:check if any car in cart have status = 0 or have current quantity < quantity
            //3:cho phep user nhap discount_code ()
            //4:trước khi áp dụng discount code phải kiểm tra expired_date phải > now và status phải = 1
            //5:sau khi áp dụng discount code xong thì update status lại = 0
            //6:sau khi áp dụng discount code thì phải display đc subtotal sau khi giảm
            //7:nếu user hủy đơn hàng thì status của order từ 1 => 0, đồng thời status của discount code phải từ 0 -> 1

            //1:
            String email = getUserEmail(request);
            List<TblCartDetailDTO> cartDetailList = getAllCartdetailOfUser(email);
            if (cartDetailList != null) {
                //2:
                int unavailCarID = getIdOfUnavailableCar(cartDetailList);
                if (unavailCarID == 0) {
                    //today
                    String today = getTodayTime();
                    request.setAttribute("TODAY", today);
                    //cart detail list
                    request.setAttribute("CART_DETAIL_LIST", cartDetailList);
                    //subtotal of cart
                    float subTotal = getTotalOfCart(email);
                    //lấy amount từ discount servlet và giảm giá
                    Float amount = (Float) request.getAttribute("AMOUNT");
                    if (amount != null) {
                        subTotal = subTotal - subTotal * amount;
                    }
                    request.setAttribute("SUB_TOTAL", subTotal);
                } else {
                    url = SHOW_CART_SERVLET;
                    request.setAttribute("UNVAILABLE_CAR_ID", unavailCarID);
                }
            }
            request.getRequestDispatcher(url).forward(request, response);
            //2:

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
