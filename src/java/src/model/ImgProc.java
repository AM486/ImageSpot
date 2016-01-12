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
import org.opencv.core.Core;

/**
 *
 * @author RG
 */
public class ImgProc extends HttpServlet {
    
       static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String imgInput = request.getParameter("image").toString();
         System.out.println("do.ImgProc image: " + imgInput);
        request.setAttribute("image", imgInput);
        imgInput = imgInput.replace("/", File.separator+File.separator);
        System.out.println("do.ImgProc filter: " + imgInput);
        request.setAttribute("filter", imgInput);

        getServletContext().getRequestDispatcher("/imgProc.jsp").forward(request, response);

    }

}
