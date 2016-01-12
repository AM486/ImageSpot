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
        <!--<link href="css/index.css" rel="stylesheet" type="text/css"/>--> 
        <link href="css/login.css" rel="stylesheet" type="text/css"/> 
        <link rel='icon' href='images/favicon.png' type='image/x-icon' >
        <link href="css/hover.css" rel="stylesheet" media="all">
        <link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>  
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>      
        <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.js" type="text/javascript" ></script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <title>Welcome to ImageSpot</title>
    </head>
    <body >    
        <nav class="my_navbar" role="navigation">
            <div class="navbar-header">
                <a class="" id="logo" href="login.jsp">ImageSpot</a>
                <span id="debug"></span>
            </div>   
        </nav>
        <div id="mainPane">
            <%
                Cookie loginCookie = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("user")) {
                            loginCookie = cookie;
                            break;
                        }
                    }
                }
                if (loginCookie != null) {
                    loginCookie.setMaxAge(0);
                    response.addCookie(loginCookie);
                }
                System.out.println("Logging out");
            %>

            <div class="form-container">
                <center><h2>Welcome to <span class="logo">ImageSpot</span>!</h2></center>

                <center><a href="" class="hvr-float-shadow" style="margin:0em 1em 1em 1em;"><img  src="images/favicon2.png"  alt="ImageSpot" width="100px" ></a></center>
                <form action="do.Connect"  method="POST">
                    <div class="form-group">                        
                        <label for="username">Username*</label>
                        <input name="username" type="text" class="form-control" id="username" placeholder="Username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password*</label>
                        <input name="password" type="password" class="form-control" id="password" placeholder="Password">
                    </div>                
                    <center><button type="submit" class="btn btn-primary">Login</button></center>
                </form>

            </div>

        </div>
        <span style="margin-left: 1%;">*Please apply your University of Thessaly academic credentials to login.</span></br>
        <jsp:include page="footer.jsp" />
    </body>
</html>
