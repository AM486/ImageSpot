/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.filters;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opencv.core.Core;
import static org.opencv.core.Core.NORM_MINMAX;
import static org.opencv.core.Core.merge;
import static org.opencv.core.Core.normalize;
import static org.opencv.core.Core.split;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;
import org.opencv.core.Mat;
import static org.opencv.core.Mat.zeros;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.circle;

/**
 *
 * @author RG
 */
public class DotsFilter extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("**______________DOTS_______________**");

        try {

            String imgInput = request.getParameter("name").toString();
            String savePath = savePath(request);
//____________________________________
            int elementSize = 2;
            int bsize = 10;
            Mat source = Imgcodecs.imread(savePath);

            Mat dst = zeros(source.size(), CV_8UC3);
            Mat cir = zeros(source.size(), CV_8UC1);
            Mat destination = new Mat(source.rows(), source.cols(), source.type());
            Mat element = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(elementSize * 3 + 1, elementSize * 3 + 1), new Point(elementSize,
                    elementSize));

            for (int i = 0; i < source.rows(); i += bsize) {
                for (int j = 0; j < source.cols(); j += bsize) {

                    circle(
                            cir,
                            new Point(j + bsize / (double) 2, i + bsize / (double) 2),
                            bsize / 2 - 1,
                            new Scalar(255, 255, 255), -1, -1, Core.LINE_AA
                    );

                }
            }

            Imgproc.morphologyEx(source, dst, Imgproc.MORPH_CLOSE, element);

            Mat cir_32f = new Mat(source.rows(), source.cols(), CV_32F);
            cir.convertTo(cir_32f, CV_32F);
            normalize(cir_32f, cir_32f, 0, 1, NORM_MINMAX);

            Mat dst_32f = new Mat(source.rows(), source.cols(), CV_32F);
            dst.convertTo(dst_32f, CV_32F);

            Vector<Mat> channels = new Vector();
            split(dst_32f, channels);
            System.out.println(channels.size());
            for (int i = 0; i < channels.size(); ++i) {
                channels.set(i, channels.get(i).mul(cir_32f));
            }
            merge(channels, dst_32f);
            dst_32f.convertTo(dst, CV_8U);

            // Core.gemm(source, source, bsize, source, bsize, dst);
            // Core.gemm(cir, destination, 1, new Mat(), 0,dst , 0);
//            Imgcodecs.imwrite("images\\outddput.jpg", dst);
            String output = savePath.substring(0, savePath.lastIndexOf(".")) + "_DOTS_temp.jpg";
            imgInput = request.getParameter("name").toString();
            String imgOutput = imgInput.substring(0, imgInput.lastIndexOf(".")) + "_DOTS_temp.jpg";
            Imgcodecs.imwrite(output, dst);

//____________________________________
            System.out.println("output: " + output);
            System.out.println("imgOutput: " + imgOutput);

            publishImg(response, imgOutput);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void publishImg(HttpServletResponse response, String imgOutput) {
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
