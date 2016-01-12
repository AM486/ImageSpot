<%-- 
    Document   : index
    Created on : 23 Δεκ 2015, 8:24:18 μμ
    Author     : RG
--%>
<%@page import="java.util.*"%>
<%@page import="src.model.DB_interaction.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/index.css" rel="stylesheet" type="text/css"/> 
        <link rel='icon' href='images/favicon.png' type='image/x-icon'/ >
              <link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>  
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>    
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
        <link rel="stylesheet" href="css/bootstrap-image-gallery.min.css">


        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>
        <script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
        <!--<script src="js/bootstrap-image-gallery.min.js"></script>-->
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/bootstrap-filestyle.js"></script>
        <script src="js/bootstrap-image-gallery.js" type="text/javascript"></script>
        <!--<script src="js/bootstrap-image-gallery.js"></script>-->

        <!--<script src="js/bootstrap.js" type="text/javascript" ></script>-->
        <script src="js/customJS.js"></script>
        <script   type="text/javascript">
            $(document).ready(function () {
                //image gallery setup    
                $(function () {
                    'use strict';
                    $('#blueimp-gallery').data('useBootstrapModal', false);
                    $('#blueimp-gallery').toggleClass('blueimp-gallery-controls', true);
                });
            });


            $(function () {

                var click = false;

                $("#accordion").accordion({
                    heightStyle: "fill", collapsible: true, active: false
                });


                $("#statsToggle").click(function () {
                    if (click === true) {
                        $("#stats").animate({left: '-=15vw'}, 1000);
                        $("#accordion").accordion({
                            active: false
                        });
                        $("#statsToggle img").attr("src", "images/favicon_toggle.png");
                        click = false;
                    }
                    else {
                        $("#stats").animate({left: '+=15vw'}, 1000);

                        $("#statsToggle img").attr("src", "images/favicon2.png");
                        click = true;
                    }
                });

                $("#statsToggle img").mouseover(function () {
                    $(this).stop().animate({"border-color": '1px solid #337ab7'}, 'fast');
                });
                $("#statsToggle img").mouseout(function () {
                    $(this).stop().animate({"border-color": '1px solid #ccc'}, 'fast');
                });

                $(".ui-accordion-header").mouseover(function () {
                    $(this).stop().animate({"border-color": '1px solid #337ab7'}, 'fast');
                });

                $(".ui-accordion-header").mouseout(function () {
                    $(this).stop().animate({"border-color": '1px solid #ccc'}, 'fast');
                });




            });
            $(function () {
                $("#accordion-resizer").resizable({
                    height: '100%',
                    minWidth: 500,
                    resize: function () {
                        $("#accordion").accordion("refresh");
                    }
                });

            });


        </script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <title>ImageSpot</title>
    </head>
    <body>

        <jsp:include page="header.jsp" />

        <div id="mainPane">
            <!-- Modal1 -->
            <div class="modal fade" id="UploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">File Upload</h4>
                            <span id="error" style="float:right"></span>
                        </div>
                        <div class="modal-body">                         

                            <center> <form method="post" action="do.Upload"     enctype="multipart/form-data">
                                    <input type="file" id="uploadBtn" tabindex="-1" name="file"  style="position: absolute; clip: rect(0px, 0px, 0px, 0px);" >
                                    <br />
                                    <div class="">
                                        <button  type="button" id="closeBtn" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="submit" id="submit" class="btn btn-primary" disabled="">Upload</button>
                                    </div>
                                </form>

                            </center>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Modal2 -->
            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header modal-header alert alert-warning">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Deleting...</h4>
                            <span id="error" style="float:right"></span>
                        </div>
                        <div class="modal-body">                         

                            <div class="row">
                                <center>Are you sure you want to remove  <span id="removing"></span>?</center>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <button id="deleteBtn" type="button" class="btn btn-primary" data-dismiss="modal">Yes</button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="gallery">
                <div class="gallery-header">                                           
                    <%  String username = null;
                        Cookie[] cookies = request.getCookies();
                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                if (cookie.getName().equals("user")) {
                                    username = cookie.getValue();
                                    out.println("<h4 >" + username + "'s gallery</h4>");
                                }
                            }
                        }
                    %>
                    <script>
                        //redirect to login 
                        var username = "<%out.print(username);%>";
                        console.log(username);
                        if (username === "null") {
                            console.log("redirecting to login");
                            console.log(window.location.href);
                            window.location.href = "/ImageSpot/login.jsp";
                        }
                    </script>

                    <button id="modalBtn" type="button" class="btn btn-primary " data-toggle="modal" data-target="#UploadModal">
                        <span class="glyphicon glyphicon-plus" aria-hidden="false"></span> add
                    </button>


                </div>
                <jsp:include page="gallery.jsp" />
            </div>
            <!--Statistics accordion--> 
            <div id="stats">
                <div id="statsToggle"><img class="img-circle" src="images/favicon_toggle.png" width=25 height=25/></div>    
                <div id="accordion-resizer" class="ui-widget-content">

                    <div id="accordion">
                        <h3>Personal Information</h3>
                        <div >
                            <%
                                Map m = src.model.DB_interaction.selectAll(username);
                                out.println("<span><strong>Name :</strong> " + m.get("name") + "</span></br>");
                                out.println("<span><strong>Surname: </strong>" + m.get("surname") + "</span></br>");
                                out.println("<span><strong>Email:</strong> " + m.get("email") + "</span></br>");
                                out.println("<span><strong>Department:</strong> " + m.get("department") + "</span></br>");
                                out.println("<span><strong>Affiliation:</strong> " + m.get("affiliation") + "</span></br>");
                            %>
                        </div>
                        <%out.print("<h3>" + username.substring(0, 1).toUpperCase() + username.substring(1, username.length()) + "'s Summary</h3>");%> 
                        <div >
                            <%
                                out.println("<span><strong>Times Logged in :</strong> " + m.get("login_no") + "</span></br>");
                                out.println("<span><strong>Number of pictures on server:</strong> " + m.get("pictures_no") + "</span></br>");
                                out.println("<span><strong>Profile size on server: </strong> " + m.get("folder_size") + "KB</span></br>");
                                out.println("<span><strong>Most used filter: </strong>" + m.get("most_used_filter") + "</span></br>");
                            %>
                        </div>               
                    </div>
                </div>
            </div>

            <!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
            <div id="blueimp-gallery" class="blueimp-gallery">
                <!-- The container for the modal slides -->
                <div class="slides"></div>
                <!-- Controls for the borderless lightbox -->
                <h3 class="title"></h3>        
                <a class="prev"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span></a>
                <a class="next"><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a>
                <a class="close">×</a>
                <a class="play-pause"></a>
                <ol class="indicator"></ol>
                <!-- The modal dialog, which will be used to wrap the lightbox content -->
                <div class="modal fade">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-hidden="true">&times;</button>
                                <h4 class="modal-title"></h4>
                            </div>
                            <div class="modal-body next"></div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default pull-left prev">
                                    <i class="glyphicon glyphicon-chevron-left"></i>
                                    Previous
                                </button>
                                <button type="button" class="btn btn-primary next">
                                    Next
                                    <i class="glyphicon glyphicon-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="footer.jsp" />
    </body>
    <script type="text/javascript">
        $('#uploadBtn').filestyle({
            size: "sm"
        });


        //ajax apply button
        $(document).on("click", "a.delete", function () {
            $("#removing").html($(this).attr("alt"));
        });


        $(document).on("click", "#deleteBtn", function () {
            console.log($("#removing").html());
            $.get("do.ImgDelete?name=" + $("#removing").html(), function (reply) {
            });
            $(document).find("a[alt='" + $("#removing").html() + "']").parent().parent().animate({opacity: '0'}, function () {
                $(this).css('display', 'none');
            });
        });

        $("#uploadBtn").change(function () {
            var filename = $('input[type=file]').val().split('\\').pop();
            check_size = this.files[0].size < 5000000;
            check_type = (filename.indexOf("jpg") > -1) || (filename.indexOf("jpeg") > -1) ||
                    (filename.indexOf("jpe") > -1) || (filename.indexOf("png") > -1) ||
                    (filename.indexOf("pns") > -1) || (filename.indexOf("jpg".toUpperCase()) > -1) || (filename.indexOf("jpeg".toUpperCase()) > -1) ||
                    (filename.indexOf("jpe".toUpperCase()) > -1) || (filename.indexOf("png".toUpperCase()) > -1) ||
                    (filename.indexOf("pns".toUpperCase()) > -1);

            if ((check_size === false) || (check_type === false)) {
                if (!check_size) {
                    $('span#error').html('<strong>Error! </strong>File too large! File limmit is 5MB. ');
                }
                if (!check_type) {
                    $('span#error').html('<strong>Error! </strong>File type not supported. Please select between .jpg, .jpeg, .jpe, .png, .pns ');
                }

                $('div.modal-header').addClass('alert alert-danger');
                $("#submit").prop('disabled', true);
                $('span#error').css('color', 'red');
            }
            if (check_size && check_type) {
                $("#submit").prop('disabled', false);
                $('span#error').css('color', 'green');
                $('span#error').html('<strong>OK</strong>');
                $('div.modal-header').removeClass('alert alert-danger').addClass('alert alert-success');

            }
        });


    </script>

</html>
