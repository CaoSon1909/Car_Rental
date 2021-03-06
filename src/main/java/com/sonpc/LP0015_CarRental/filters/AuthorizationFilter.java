/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.filters;

import com.sonpc.LP0015_CarRental.tblUser.TblUserDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ACER
 */
public class AuthorizationFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthorizationFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthorizationFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthorizationFilter:DoAfterProcessing");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //get resource from authentication filter.
        String resource = (String) req.getAttribute("RESOURCES");
//        String uri = req.getRequestURI();
//        int lastIndex = uri.lastIndexOf("/");
//        String resource = uri.substring(lastIndex + 1);
        //
        System.out.println("Authorization Filter:");
        System.out.println("Request resource: " + resource);

        //admin resource list
        List<String> adminList = (List<String>) req.getServletContext().getAttribute("ADMIN_RESOURCES_LIST");
        //user resource list
        List<String> userList = (List<String>) req.getServletContext().getAttribute("USER_RESOURCES_LIST");

        //check xem isAuthenticated attribute ???? c?? ch??a
        Boolean isAuthenticated = (Boolean) req.getAttribute("IS_AUTHENTICATED");
        if (isAuthenticated != null) {
            boolean isAuthorized = false;
            
            HttpSession session = req.getSession(false);
            if (session != null){
                TblUserDTO user = (TblUserDTO) session.getAttribute("USER_DTO");
                if (user != null){
                    //n???u l?? request c???a admin
                    if (isAuthenticated == true && user.getRole() == 1){
                        isAuthorized = adminList.contains(resource);
                    }
                    //ho???c n???u l?? request c???a user
                    else if (isAuthenticated == true && user.getRole() == 0){
                        isAuthorized = userList.contains(resource);
                    }
                    //N???u authorized = true
                    if (isAuthorized){
                        System.out.println("Is authorized:" + true);
                        chain.doFilter(request, response);
                    }
                    else{
                        System.out.println("Is authorized: " + false);
                        res.sendError(403);
                    }
                }
            }
            
        } else {
            //N???u isAuthenticated ch??a t???n t???i => resource ???? ko c???n authenticated
            System.out.println("=====================================");
            chain.doFilter(request, response);
        }

    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthorizationFilter:Initializing filter");
            }
        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthorizationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthorizationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
