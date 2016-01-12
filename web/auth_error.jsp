<%-- 
    Document   : auth_error
    Created on : 30 Δεκ 2015, 5:47:24 πμ
    Author     : RG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Authentication Error</title>
        <link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>  
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>    
        <link rel='icon' href='images/favicon.png' type='image/x-icon'/ >
        <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.js" type="text/javascript" ></script>
        <link href="css/auth_error.css" rel="stylesheet" type="text/css"/> 
        <link href="css/index.css" rel="stylesheet" type="text/css"/> 
    </head>
    <body>
       <jsp:include page="header.jsp" /> 
       <div id="mainPane">
<div class="panel panel-danger">
  <div class="panel-heading">
    <h3 class="panel-title">Authentication Error</h3>
  </div>
  <div class="panel-body">
    <p>Authentication has failed please try again...<p>
        <center><a class="btn btn-default" value="submit" style="margin-top: 2em;" href="login.jsp">Return</a></center>
  </div>
</div>
           </div>
<jsp:include page="footer.jsp" />
    </body>
</html>
