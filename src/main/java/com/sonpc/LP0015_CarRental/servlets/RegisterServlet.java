/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.servlets;

import com.sonpc.LP0015_CarRental.requestobjects.UserRequestObject;
import com.sonpc.LP0015_CarRental.tblRegistrationToken.TblRegistrationTokenDAO;
import com.sonpc.LP0015_CarRental.tblRegistrationToken.TblRegistrationTokenDTO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDAO;
import com.sonpc.LP0015_CarRental.tblUser.TblUserDTO;
import com.sonpc.LP0015_CarRental.validators.UserValidator;
import com.sonpc.LP0015_CarRental.validators.Validator;
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
public class RegisterServlet extends HttpServlet {

    private final String REGISTER_PAGE = "register.jsp";
    private final String USER_ACTIVATION_PAGE = "user-activation.jsp";

    private UserRequestObject getRequestObject(HttpServletRequest request) {
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirmPassword = request.getParameter("txtConfirm");
        String fullName = request.getParameter("txtFullName");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");

        UserRequestObject reqObj = new UserRequestObject(email, password, confirmPassword, fullName, phone, address);
        return reqObj;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = "";
        try {
            //A: validate email, confirm password và password match
            UserRequestObject reqObj = getRequestObject(request);
            Validator<UserRequestObject> validator = new UserValidator(reqObj);
            validator.validateObject();
            if (validator.hasError()) {
                request.setAttribute("ERROR", validator.getErrorMap());
                url = REGISTER_PAGE;
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                //B: display in new page:
                //a: show account info to user
                //b: show activation code to user
                //c: 2 options for user, which are 2 links : "login page" and "activate your account" links
                url = USER_ACTIVATION_PAGE;
                //show account info that user just create => save attrubute to request scope
                request.setAttribute("USER_EMAIL", reqObj.getEmail());
                request.setAttribute("USER_PASSWORD", reqObj.getPassword());
                request.setAttribute("USER_FULL_NAME", reqObj.getFullName());
                request.setAttribute("USER_PHONE", reqObj.getPhoneNumber());
                request.setAttribute("USER_ADDERSS", reqObj.getAddress());
                //insert user to tbl_User
                TblUserDAO userDAO = new TblUserDAO();
                boolean insertResult = userDAO.createNewAccount(reqObj);
                //generate activation code
                TblRegistrationTokenDAO registerTokenDAO = new TblRegistrationTokenDAO();
                boolean result = registerTokenDAO.generateNewTokenString(reqObj.getEmail());
                if (result){
                    //show activation code
                    TblRegistrationTokenDTO registrationTokenDTO = registerTokenDAO.getTheLastestTokenStringOfUser(reqObj.getEmail());
                    String tokenString = registrationTokenDTO.getTokenString();
                    request.setAttribute("TOKEN_STRING", tokenString);
                    request.getRequestDispatcher(url).forward(request, response);
                }
                
            }

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
