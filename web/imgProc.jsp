<%-- 
    Document   : imgProc
    Created on : 23 Δεκ 2015, 8:24:18 μμ
    Author     : RG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/imgProc.css" rel="stylesheet" type="text/css"/> 
        <link href="css/index.css" rel="stylesheet" type="text/css"/> 

        <link rel='icon' href='images/favicon.png' type='image/x-icon'/ >
              <link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>  
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>      
        <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.js" type="text/javascript" ></script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <title>ImageSpot</title>
        <script>
            //ajax filter selection
            $(document).on("click", "#dilation", function () {
                waiting()
                $.when(
                        $.get("do.Dilation?name=${requestScope.filter}", function (reply) {
                            console.log(reply)
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });
            $(document).on("click", "#erosion", function () {
                waiting()
                $.when(
                        $.get("do.Erosion?name=${requestScope.filter}", function (reply) {
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });
            $(document).on("click", "#gaussian", function () {
                waiting()
                $.when(
                        $.get("do.Gaussian?name=${requestScope.filter}", function (reply) {
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });
            $(document).on("click", "#morph", function () {
                waiting()
                $.when(
                        $.get("do.Morph?name=${requestScope.filter}", function (reply) {
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });
            $(document).on("click", "#grayscale", function () {
                waiting()
                $.when(
                        $.get("do.Grayscale?name=${requestScope.filter}", function (reply) {
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });


            $(document).on("click", "#canny", function () {
                waiting()
                $.when(
                        $.get("do.Canny?name=${requestScope.filter}", function (reply) {
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });
            $(document).on("click", "#sobel", function () {

                waiting()
                $.when(
                        $.get("do.Sobel?name=${requestScope.filter}", function (reply) {
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });

            $(document).on("click", "#dots", function () {
                waiting()
                $.when(
                        $.get("do.Dots?name=${requestScope.filter}", function (reply) {
                            $("#apply").attr("alt", reply);
                            $("#mainImg").attr("src", reply);
                        })
                        ).then(function ( ) {
                    done()
                });
            });

            function waiting() {
                $('#fog').css("z-index", "200")
                        .stop().animate({opacity: '.7'});
                $('.waiting').css("z-index", "300")
                        .stop()
                        .animate({opacity: '1'});

            }
            function done() {
                $.when(
                        $('#fog').animate({opacity: '0'})).then(function ( ) {
                    $('#fog').css("z-index", "-1");
                    $('.waiting').css('opacity', "0")
                            .css("z-index", "-1");
                });
                console.log("done");

            }





//ajax apply button
            $(document).on("click", "#apply", function () {
                $.get("do.Apply?name=" + $("#apply").attr("alt"), function (reply) {
                    $("#mainImg").attr("src", reply);
                });
                $('#fog').css("z-index", "200");
                $('.progress').css("z-index", "300")
                        .css('opacity', "1");
                
                $.when(  $('#fog').animate({opacity: '.7'})  ).then(function ( ) {
                    
                   $.when( $('#progress-bar').stop().animate({width: '100%'},"swing")).then(function () {
                        $('.progress').animate({opacity: '0'}, function () {
                            window.location = "/ImageSpot/index.jsp";
                        });
                   });                    
                });
            });

//filter activation effect
            $(document).on("click", ".filter", function () {
                $(this).parent().parent().find('.active').removeClass('active');
                $(this).addClass('active');
            });

        </script>

    </head>
    <body>
        <jsp:include page="header.jsp" />
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
                            window.location.href = "/ImageSpot/login.jsp";
                        }
                    </script>
        <div id="fog">        </div>
        <div class="waiting">
            <img src="images/loading.gif" width="50vw" height="auto">            
        </div>

        <div class="progress">
            <div id="progress-bar" class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: 0%; max-width: 100%;">
                <span class="progress-text">Saving..</span>
            </div>
        </div>
        <div id="mainPane" class="">
            <div id="hud" class="container">
                <center>
                    <h2>Choose a filter for your image!</h2>
                    <div class=""><img id="mainImg" src=${requestScope.image} name=${requestScope.image} >    </div>
                    <div id="filterStrip1" class="filterStrip">
                        <div class="filter">
                            <a id="dilation" ><img src="images/lena_DIL.jpg" class="filterPreview"/></a>
                            <span class="filterLabel" >Dilation</span>
                        </div>
                        <div class="filter">
                            <a id="erosion" ><img src="images/lena_ER.jpg" class="filterPreview" /></a>
                            <span class="filterLabel" >Erosion</span>
                        </div>
                        <div class="filter">
                            <a id="gaussian" ><img src="images/lena_GAUSSIAN.jpg" class="filterPreview" /></a>
                            <span class="filterLabel" >Gaussian</span>
                        </div>
                        <div class="filter">
                            <a id="morph" ><img src="images/lena_MORPH.jpg" class="filterPreview" /></a>
                            <span class="filterLabel" >Morph</span>
                        </div>
                    </div>

                    <div id="filterStrip2" class="filterStrip">
                        <div class="filter">
                            <a id="grayscale" ><img src="images/lena_BW.jpg" class="filterPreview" /></a>
                            <span class="filterLabel" >Grayscale</span>
                        </div>
                        <div class="filter">
                            <a id="canny"  ><img src="images/lena_CA.jpg" class="filterPreview" /></a>
                            <span class="filterLabel" >Canny</span>
                        </div>
                        <div class="filter">                           
                            <a id="sobel" ><img src="images/lena_SOBEL.jpg" class="filterPreview" /></a>
                            <span class="filterLabel" >Sobel</span>
                        </div>
                        <div class="filter">
                            <a id="dots" ><img src="images/lena_DOTS.jpg" class="filterPreview" /></a>
                            <span class="filterLabel" >Dots</span>
                        </div>
                    </div>
                    <div id="applyFilter">
                        <a class="btn btn-default" id="return" href="#"  role="button">Return</a>
                        <a class="btn btn-default" id="reset" href="do.Reset?name=${requestScope.image}"   role="button">Reset</a>
<!--                        <a class="btn btn-primary" id="apply" href="do.Apply?name=${requestScope.image}" role="button">Apply</a>-->
                        <a class="btn btn-primary" id="apply"  role="button">Apply</a>
                    </div>
                </center>
            </div>

        </div>

        <jsp:include page="footer.jsp" />
    </body>
    <script type="text/javascript">
        $(document).on("click", "#return", function () {
            $.get("do.Reset?name=${requestScope.image}", function (reply) {
            });
            window.location.href = "/ImageSpot/index.jsp";
        });
    </script>

</html>
