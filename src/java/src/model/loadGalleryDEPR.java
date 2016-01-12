/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RG
 */
public class loadGalleryDEPR extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    username = cookie.getValue();
                    System.out.println("Cookie username:  " + username);
                }
            }
        }

        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + "users" + File.separator + username;
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        File[] listOfFiles = fileSaveDir.listFiles();

        String imageFolderPath = "users/" + username + "/";
        String output = "";
        if (listOfFiles.length == 0) {
            output = "<center><h4>Your Gallery is empty presshgmjkhn jdhfkfn kfutiktubtyubnrtyvurtu 'add' to upload your images</h4></center>";

        } else {
            for (int i = 0; i < listOfFiles.length; i++) {
                String imagePath = imageFolderPath + listOfFiles[i].getName();
                output = output + "<div class='col-xs-6 col-md-4 links'>"
                       
                        + "<a href=" + imagePath + " class='thumbnail'  data-gallery>"                        
                        + "<img data-src='holder.js/100%x180' alt='100%x180' src=" + imagePath + " style='height: 180px; width: 100%; display: block;'>"
                        + "</a>"
                        + " </div>";
            }

        }
        System.out.println("outputvrtyutybvruityrvbikrtyi: " + output);
        request.setAttribute("usernamebrtyubrbyuvrty", username);
        request.setAttribute("dirContent", output);
        request.getRequestDispatcher("/gallery.jsp").forward(request, response);

    }

}
