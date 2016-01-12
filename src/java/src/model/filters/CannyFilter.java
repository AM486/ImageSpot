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
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author RG
 */
public class CannyFilter extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________CANNY_______________**");

        try {

            String imgInput = request.getParameter("name").toString();
            String savePath = savePath(request);
//____________________________________

            Mat source = Imgcodecs.imread(savePath);
            Mat destination = new Mat(source.rows(), source.cols(), source.type());
            Mat det = new Mat(source.rows(), source.cols(), source.type());

            Imgproc.cvtColor(source, destination, Imgproc.COLOR_BGR2GRAY);
            Imgproc.blur(destination, det, new Size(3, 3));
            Imgproc.Canny(det, det, 5, 15, 3, false);
            Mat dest = new Mat();
            Core.add(dest, Scalar.all(0), dest);
            source.copyTo(dest, det);

               String output = savePath.substring(0, savePath.lastIndexOf(".")) + "_CA_temp.jpg";
            imgInput = request.getParameter("name").toString();
            String imgOutput = imgInput.substring(0, imgInput.lastIndexOf(".")) + "_CA_temp.jpg";
            Imgcodecs.imwrite(output, dest);

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
