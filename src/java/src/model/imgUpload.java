/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.opencv.core.Core;

/**
 *
 * @author RG
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB

public class imgUpload extends HttpServlet {

 

    private static final String SAVE_DIR = "users";
    String OS = System.getProperty("os.name");

    protected void doPost(HttpServletRequest request,
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

        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");

        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR + File.separator + username;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String filePos = null;
        for (Part part : request.getParts()) {
            filePos = extractFileName(part);
            part.write(savePath + File.separator + filePos);

        }

        String imgLocation = SAVE_DIR + File.separator + username + File.separator + filePos.substring(filePos.lastIndexOf(File.separator) + 1, filePos.length());
        System.out.println(imgLocation);

//        System.out.println("imgLocation: "+ imgLocation);
        imgLocation = imgLocation.replace(File.separator, "/");
        request.setAttribute("image", imgLocation);

        System.out.println(imgLocation);
        request.setAttribute("filter", imgLocation);

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private String extractFileName(String path) {

        String folder = null;
        if (path != null) {
            if (OS.contains("Windows")) {

                int i = path.lastIndexOf(File.separator);
                System.out.println("length: " + path.length() + "   \\ position:  " + i);
                folder = path.substring(i + 1, path.length());
            }
            if (OS.contains("Linux")) {

                int i = path.lastIndexOf("/");
                System.out.println("length: " + path.length() + "   / position:  " + i);
                folder = path.substring(i + 1, path.length());
            }
            System.out.println("folder: " + folder);
            return folder;
        }
        return null;
    }

}
