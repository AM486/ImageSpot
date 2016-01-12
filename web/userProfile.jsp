<%-- 
    Document   : index
    Created on : 23 Δεκ 2015, 8:24:18 μμ
    Author     : RG
--%>

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
        <script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/bootstrap-filestyle.js"></script>
        <script src="js/bootstrap-image-gallery.js" type="text/javascript"></script>        
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
        </script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <title>ImageSpot</title>
    </head>
    <body>

        <jsp:include page="header.jsp" />

        <div id="mainPane">
          
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
