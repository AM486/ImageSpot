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
public class ApplyFilter extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________Apply_______________**");

        String imgInput = savePath(request);
        System.out.println("imgInput: " + imgInput);
        renameTemps(request);

        String imgOutput = request.getParameter("name");
        imgOutput = imgOutput.replaceFirst("_temp", "");
        deleteTemps( savePath(request).replace(File.separator, "/") );

        String reply = imgOutput;
        System.out.println("reply " + reply);
        response.getWriter().write(reply);
    }

    public static void renameTemps(HttpServletRequest request) {
        String old_img = savePath(request);
        System.out.println("old_img: " + old_img);

        File oldName = new File(old_img);
        String new_img = old_img.replaceFirst("_temp", "");
        File newName = new File(new_img);
        if (oldName.renameTo(newName)) {
            System.out.println("renamed to: " + newName);
        } else {
            System.out.println("Error");
        }
//        return new_img;
    }

    public static void deleteTemps(String imgInput) {
        String path = imgInput.substring(0, imgInput.lastIndexOf("/"));
        System.out.println("path: " + path);
        File folder = new File(path);
        System.out.println("folder: " + folder.getName());
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains("_temp")) {
                listOfFiles[i].delete();
                System.out.println("Deleted " + listOfFiles[i].getName());
            }
        }
    }

    public static String savePath(HttpServletRequest request) {
        String imgInput = request.getParameter("name").toString();
        System.out.println(imgInput);
        String appPath = request.getServletContext().getRealPath("");

        String savePath = appPath + File.separator + imgInput;
        System.out.println("savePath: " + savePath);
        return savePath;

    }

}
