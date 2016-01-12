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
import static src.model.ApplyFilter.deleteTemps;

/**
 *
 * @author RG
 */
public class test extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________TEST_______________**");
        String appPath = request.getServletContext().getRealPath("");
//        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
//        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(appPath);       // Write response body.
//        request.setAttribute("testResponse", appPath);
        
//        getServletContext().getRequestDispatcher("/imgProc.jsp").forward(request, response);

    }

}
