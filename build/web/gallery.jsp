<%-- 
    Document   : gallery
    Created on : 31 Δεκ 2015, 1:54:17 μμ
    Author     : RG
--%>



<%@page import="java.io.File"%>
<%@page import="src.model.DB_interaction.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row">
    <!--${requestScope.dirContent}-->
    <%

        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    username = cookie.getValue();
                    System.out.println("gallery.jsp Cookie username:  " + username);
                    
                }
            }
        }
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + "users" + File.separator + username;
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        src.model.DB_interaction.updateDB(fileSaveDir, username);
        File[] listOfFiles = fileSaveDir.listFiles();
        System.out.println("listOfFiles.length :  " + listOfFiles.length );
        String imageFolderPath = "users/" + username + "/";
        String output = "";
        if (listOfFiles.length == 0) {
            output = "<center><h4>Your Gallery is empty press 'add' to upload your images</h4></center>";

        } else {
            
            for (int i = 0; i < listOfFiles.length; i++) {
                if (!listOfFiles[i].getName().contains("_temp")) {
                    String imagePath = imageFolderPath + listOfFiles[i].getName();
                    output = output + "<div class='col-xs-6 col-md-4 links' >"
                            + "<div class='gallery_option' '>"
                            //                        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"> Launch demo modal </button>
                            + "<a href='#' alt=" + imagePath + "   data-toggle='modal' data-target='#deleteModal' class='delete glyphicon glyphicon-remove' style='float:right'></a>"
                            + "<a  href='do.ImgProc?image=" + imagePath + "'" + " class='edit glyphicon glyphicon-pencil' style='float:right'></a>"
                            + "</div>"
                            + "<a href=" + imagePath + " class='thumbnail'  data-gallery style=' width: auto;  height: 20vh; display: block;'>"
                            + "<img data-src='holder.js/100%x180' alt='100%x180' src=" + imagePath + " style=' max-height:100%;'>"
                            + "</a>"
                            + " </div>";
                }
            }
        }
        out.println(output);
        //System.out.println(output);

    %>

</div>

<script>

</script>
