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
import java.util.Map;
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
public class AuthenticationFilter implements Filter {

    private final String LOGIN_PAGE = "login-page";
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthenticationFilter() {

    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoAfterProcessing");
        }
    }

    //This function is used for checking whether or not the requested resources from client is existed? => return resource
    private String getResourceFromClient(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String resource = uri.substring(lastIndex + 1);

        Map<String, String> siteMap = (Map<String, String>) req.getServletContext().getAttribute("SITE_MAP");
        String url = siteMap.get(resource);
        if (url != null) {
            return resource;
        }
        return null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //check is resource existed?
        String resource = getResourceFromClient(request, response);
        System.out.println("=====================================");
        System.out.println("Authentication Filter:");
        System.out.println("Request resource: " + resource);
        if (resource == null) {
            System.out.println("Is resource valid: " + false);
            res.sendError(404);
        } else {
            //đưa resource vào req scope
            req.setAttribute("RESOURCES", resource);
            //
            System.out.println("Is resource valid: " + true);
            //check does resource need authenticate?
            List<String> authenticateList = (List<String>) req.getServletContext().getAttribute("AUTHENTICATION_LIST");
            boolean isAuthenticated = false;
            if (authenticateList.contains(resource)) {
                System.out.println("Need authenticated: " + true);
                HttpSession session = req.getSession(false);
                if (session == null) {
                    System.out.println("Need authenticated: " + true);
                    System.out.println("Is authenticated: " + false);
                    System.out.println("Session is empty - Redirect to login page.");
                    res.sendRedirect(LOGIN_PAGE);
                } else {
                    TblUserDTO user = (TblUserDTO) session.getAttribute("USER_DTO");
                    if (user != null) {
                        isAuthenticated = true;
                        req.setAttribute("IS_AUTHENTICATED", isAuthenticated);
                        System.out.println("Is authenticated: " + true);
                    }//end if user exist
                    else {
                        System.out.println("Is authenticated: " + false);
                        res.sendRedirect(LOGIN_PAGE);
                    }//end else
                }
            }//end if authenticateList contain resource
            else {
                System.out.println("Need authenticated: " + false);
                System.out.println("=====================================");
            }
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
                log("AuthenticationFilter:Initializing filter");
            }
        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
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
