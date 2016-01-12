/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.connection;

import com.uthldap.Uthldap;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RG
 */
public class LDAPconnection extends HttpServlet {

    private String user;
    private String pass;

    private static final String LOG_DIR = "log";

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/imagespot";

        //  Database credentials
        final String DBUSER = "root";
        final String DBPASS = "";

        user = request.getParameter("username");
        pass = request.getParameter("password");

        String appPath = request.getServletContext().getRealPath("");

        String logPath = appPath + File.separator + LOG_DIR;
        File logDir = new File(logPath);
        if (!logDir.exists()) {
            logDir.mkdir();
        }
        logDir.getParentFile().mkdirs();
        String timeStamp = new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date());
        PrintWriter logWriter = new PrintWriter(logDir + File.separator + "log.txt");

        logWriter.println(new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date()) + "Username: " + user + "\n" + "Password: " + pass);
        System.out.println("Username: " + user + "\n" + "Password: " + pass);

        Uthldap ldap = new Uthldap(user, pass);
        System.out.println(ldap.auth());
//        logWriter.close();
        if (ldap.auth()) {
            try {
                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                // Open a connection
                Connection conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

                Statement stmt = conn.createStatement();

                String username = ldap.getMail().substring(0, ldap.getMail().lastIndexOf("@"));
                String email = ldap.getMail();
                String affiliation = ldap.getAffiliation();
                String dep = ldap.getDept();
                String fullname = ldap.getName();
                String name = fullname.substring(0, fullname.lastIndexOf(" "));
                String surname = fullname.substring(fullname.lastIndexOf(" ") + 1, fullname.length()).toLowerCase();
                surname = surname.substring(0, 1).toUpperCase() + surname.substring(1, surname.length());
                String most_used_filter = null;
                int folder_size = 0;
                int login_no = 1;
                int pictures_no = 0;

                System.out.println("Connecting to LDAP");
                logWriter.println(new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date()) + "Connecting to LDAP");
                String savePath = appPath + File.separator + "users" + File.separator + username;
                System.out.println("savePath " + savePath);
                user = username;
                System.out.println("username " + user);
                //create user session
                Cookie loginCookie = new Cookie("user", user);
                //setting cookie to expiry in 30 mins
                loginCookie.setMaxAge(30 * 60);
                response.addCookie(loginCookie);

                System.out.println("username: " + username);
                System.out.println("email: " + email);
                System.out.println("affiliation: " + affiliation);
                System.out.println("dep: " + dep);
                System.out.println("fullname: " + fullname);
                System.out.println("name: " + name);
                System.out.println("surname: " + surname);

                //create user directory
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    String sql = "INSERT INTO `users`"
                            + "(`username`, `name`, `surname`, `department`, "
                            + "`affiliation`, `login_no`, `pictures_no`, `folder_size`, "
                            + "`most_used_filter`, `email`) VALUES "
                            + "('" + username + "','" + name + "','" + surname + "','" + dep + "',"
                            + "'" + affiliation + "'," + login_no + "," + pictures_no + "," + folder_size + ","
                            + most_used_filter + ",'" + email + "')";
                    System.out.println("query: " + sql);
                    logWriter.println(new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date()) + "query: " + sql);
                    
                    stmt.executeUpdate(sql);
                    System.out.println("Inserted user " + user + " into the system...");
                    fileSaveDir.mkdir();
                    
                    
                } else {
                    String sql = "Select * from `users` WHERE username='" + username + "' ";
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        login_no = rs.getInt("login_no");
                    }
                    login_no++;
                    System.out.println("login_no: " + login_no);

                    sql = "UPDATE `users` "
                            + "SET login_no = " + login_no + "  WHERE username='" + username + "' ";
                    stmt.executeUpdate(sql);
                    
                }
                logWriter.close();
                conn.close();
                request.setAttribute("username", username);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                
            
            } catch (NullPointerException e) {
                logWriter.println(new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date()) + e.getMessage());
                logWriter.close();
                System.out.println(e.getMessage());
                response.sendRedirect("auth_error.jsp");
            } catch (ClassNotFoundException e) {
                logWriter.println(new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date()) + e.getMessage());
                logWriter.close();
                Logger.getLogger(LDAPconnection.class.getName()).log(Level.SEVERE, null, e);
            } catch (javax.xml.ws.WebServiceException e) {
                logWriter.println(new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date()) + e.getMessage());
                logWriter.close();
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                logWriter.println(new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]").format(new Date()) + e.getMessage());
                logWriter.close();
                System.out.println(e.getMessage());
            }
        } else {
            response.sendRedirect("auth_error.jsp");
        }
        
        

    }

}
