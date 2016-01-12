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
import static src.model.ApplyFilter.savePath;

/**
 *
 * @author RG
 */
public class ImgDelete extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________ImgDelete______________**");
        try {
            String imgInput = request.getParameter("name").toString();
            String del = imgInput.substring(imgInput.lastIndexOf("/")+1, imgInput.length());
            System.out.println("to be deleted: " + del);

            String old_img = savePath(request).replace("/", File.separator);

            String path = old_img.substring(0, old_img.lastIndexOf(File.separator));
            System.out.println("path: " + path);
            File folder = new File(path);
            System.out.println("folder: " + folder.getName());
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(del)) {
                    listOfFiles[i].delete();
                    System.out.println("Deleted " + listOfFiles[i].getName());
                }
            }

//            getServletContext().getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
