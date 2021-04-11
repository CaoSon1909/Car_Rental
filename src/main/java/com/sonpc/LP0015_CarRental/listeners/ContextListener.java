/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonpc.LP0015_CarRental.listeners;

import com.sonpc.LP0015_CarRental.conventions.Constant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

 
public class ContextListener implements ServletContextListener {

    //The funtion for reading file, return Map<String, String>
    public Map<String, String> readResourcesMappingFile(String fileName, ServletContext context) {
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;

        Map<String, String> map = null;
        String line = "";
        try {
            file = new File(fileName);
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                String tmp[] = line.split(Constant.DELIMETER);
                String action = tmp[0];
                String servlet = tmp[1];
                if (map == null) {
                    map = new HashMap<String, String>();
                }
                map.put(action, servlet);
            }

            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        } catch (FileNotFoundException ex) {
            context.log(ex.getMessage());
        } catch (IOException ex) {
            context.log(ex.getMessage());
        }
        return map;
    }

    //The function for reading resouces files, return List<String>
    public List<String> readResourcesFile(String fileName, ServletContext context) {
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;

        List<String> list = null;
        String line = null;
        try {
            file = new File(fileName);
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                if (list == null) {
                    list = new ArrayList<String>();
                }
                list.add(line);
            }
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        } catch (FileNotFoundException ex) {
            context.log(ex.getMessage());
        } catch (IOException ex) {
            context.log(ex.getMessage());
        }
        return list;
    }

    public void contextInitialized(ServletContextEvent sce) {
        //context scope
        ServletContext context = sce.getServletContext();
        //real path
        String path = context.getRealPath("/");
        //[NOTE]: Load file name strings....
        //load "resource_mapping.txt" string from initparameter
        String resourcesFileName = path + context.getInitParameter("resourceFileName");
        //load "authentication_resources.txt" string from initparameter
        String authenticationFileName = path + context.getInitParameter("authenticationFileName");
        //load "admin_resources.txt" string from initparameter
        String adminResourceFileName = path + context.getInitParameter("adminResourcesFileName");
        //load "user_resources.txt" string from initparameter
        String userResourcesFileName = path + context.getInitParameter("userResourcesFileName");
        //[NOTE]: Get siteMap (Map<String, String>) based on "resourcesFileName"
        Map<String, String> siteMap = readResourcesMappingFile(resourcesFileName, context);
        //[NOTE]: Get authentication list (List<String>) based on "authenticationFileName"
        List<String> authenticationList = readResourcesFile(authenticationFileName, context);
        //[NOTE]: Get admin resources (List<String>) based on "adminResourceFileName"
        List<String> adminResources = readResourcesFile(adminResourceFileName, context);
        //[NOTE]: Get user resources (List<String> based on "userResourcesFileName"
        List<String> userResources = readResourcesFile(userResourcesFileName, context);

        //[NOTE]: Set "siteMap", "authenticationList", "adminResources" and "userResources" into context scope.
        context.setAttribute("SITE_MAP", siteMap);
        context.setAttribute("AUTHENTICATION_LIST", authenticationList);
        context.setAttribute("ADMIN_RESOURCES_LIST", adminResources);
        context.setAttribute("USER_RESOURCES_LIST", userResources);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
