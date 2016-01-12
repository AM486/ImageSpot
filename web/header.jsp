<%-- 
    Document   : header
    Created on : 23 Δεκ 2015, 5:25:51 μμ
    Author     : RG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

//    if (username == null) {
//        response.sendRedirect("login.jsp");
//    }

%>
<nav class="my_navbar" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">

        <a class="" id="logo" href="index.jsp">ImageSpot</a>
        <span id="debug"></span>
        <ul class="header-link">
            <%  String username = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("user")) {
                            username = cookie.getValue();

                        }
                    }
                }

                if ((username == null) || (username.equals(""))) {
                    out.print("<li><a id='profile' href='userProfile.jsp' >" + "</a></li>");
                    System.out.println("No user, header");
                    response.sendRedirect("login.jsp");
                } else {
                    out.print("<li><a id='profile' href='userProfile.jsp' ><strong>" + username + "</strong></a></li>");
                    out.print("<li>"
                            + "<a id='logout' href='do.Logout'>Logout</a>"
                            + "</li>");
                }

            %>
        </ul>
    </div>   
</nav>

<script type="text/javascript">

//   redirect to main site
    if (location.pathname === "/ImageSpot/do.Connect") {
        console.log(window.location.href);
        window.location.href = "/ImageSpot/index.jsp";
    }

    if ($('#profile').html() === "") {
        $("#logo").attr('href', 'login.jsp');
    }

</script>