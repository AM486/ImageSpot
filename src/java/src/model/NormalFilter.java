/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RG
 */
public class NormalFilter extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________NORMAL______________**");

        try {
            String imgInput = request.getParameter("name").toString();
            String appPath = request.getServletContext().getRealPath("");

            String savePath = appPath + File.separator + imgInput;

            String imgOutput = savePath;
            System.out.println("output: " + imgOutput);
            String reply = imgOutput;
            System.out.println(reply);
            
            src.model.ApplyFilter.deleteTemps( src.model.ApplyFilter.savePath(request) );
            
            response.getWriter().write(reply);
            request.setAttribute("image", imgInput);
            request.setAttribute("filter", imgInput);
            getServletContext().getRequestDispatcher("/imgProc.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
