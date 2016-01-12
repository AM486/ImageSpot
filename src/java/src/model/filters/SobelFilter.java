/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.filters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import src.model.ApplyFilter;

/**
 *
 * @author RG
 */
public class SobelFilter extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________SOBEL_______________**");

        try {

            String imgInput = request.getParameter("name").toString();
            String savePath = savePath(request);
//____________________________________  

            int kernelSize = 3;
            // Mat source = Imgcodecs.imread(folder+"\\"+imgName, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

            Mat source = Imgcodecs.imread(savePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
            Mat destination = new Mat(source.rows(), source.cols(), source.type());

            Mat kernel = new Mat(kernelSize, kernelSize, CvType.CV_32F) {
                {
                    put(0, 0, -3);
                    put(0, 1, -3);
                    put(0, 2, -3);

                    put(1, 0 - 3);
                    put(1, 1, 0);
                    put(1, 2, -3);

                    put(2, 0, 5);
                    put(2, 1, 5);
                    put(2, 2, 5);
                }
            };

            Imgproc.filter2D(source, destination, -8, kernel);
            String output = savePath.substring(0, savePath.lastIndexOf(".")) + "_SOBEL_temp.jpg";
            imgInput = request.getParameter("name").toString();
            String imgOutput = imgInput.substring(0, imgInput.lastIndexOf(".")) + "_SOBEL_temp.jpg";
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
