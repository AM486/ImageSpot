/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.filters;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author RG
 */
public class GaussianFilter extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________GAUSSIAN_______________**");

        try {

            String imgInput = request.getParameter("name").toString();
            String savePath = savePath(request);
//____________________________________

            Mat source = Imgcodecs.imread(savePath, Imgcodecs.CV_LOAD_IMAGE_COLOR);

            Mat destination = new Mat(source.rows(), source.cols(), source.type());
            Imgproc.GaussianBlur(source, destination, new Size(35, 35), 0);

            String output = savePath.substring(0, savePath.lastIndexOf(".")) + "_GA_temp.jpg";
            imgInput = request.getParameter("name").toString();
            String imgOutput = imgInput.substring(0, imgInput.lastIndexOf(".")) + "_GA_temp.jpg";
            Imgcodecs.imwrite(output, destination);

            //____________________________________
            System.out.println("output: " + output);
            System.out.println("imgOutput: " + imgOutput);

            publishImg( response,  imgOutput);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

     void publishImg( HttpServletResponse response,  String imgOutput) {
        try {
            String reply = imgOutput;
            System.out.println(reply);
            response.getWriter().write(reply);
        } catch (IOException ex) {
            Logger.getLogger(DilationFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String savePath(HttpServletRequest request) {
        String imgInput = request.getParameter("name").toString();
        System.out.println("imgInput: " + imgInput);
        String appPath = request.getServletContext().getRealPath("");

        String savePath = appPath + File.separator + imgInput;
        System.out.println("savePath: " + savePath);
        return savePath;

    }
}
