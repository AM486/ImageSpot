/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 *
 * @author RG
 */
public class DB_interaction {

    private static String JDBC_DRIVER;
    private static String DB_URL;

    private static final String DBUSER = "root";
    private static final String DBPASS = "";

    private static Statement db_connect() throws ClassNotFoundException, SQLException {
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://localhost:3306/imagespot";
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);
        Statement stmt = conn.createStatement();
        return stmt;
    }

    public static void updateDB(File fileSaveDir, String username) {
        try {
            File[] listOfFiles = fileSaveDir.listFiles();

            Statement stmt = db_connect();

            int folder_size = 0;
            int pictures_no = listOfFiles.length;

            int[] filter = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

            if (listOfFiles.length != 0) {

                for (int i = 0; i < listOfFiles.length; i++) {
                    System.out.println("file " + listOfFiles[i].getName() + " size " + Math.ceil(listOfFiles[i].length() / 1000));
                    folder_size = ((int) Math.ceil(listOfFiles[i].length() / 1000)) + folder_size;

                    if (listOfFiles[i].getName().contains("DIL")) {
                        filter[0]++;
                    }
                    if (listOfFiles[i].getName().contains("SOBEL")) {
                        filter[1]++;
                    }
                    if (listOfFiles[i].getName().contains("CA")) {
                        filter[2]++;
                    }
                    if (listOfFiles[i].getName().contains("MORPH")) {
                        filter[3]++;
                    }
                    if (listOfFiles[i].getName().contains("ER")) {
                        filter[4]++;
                    }
                    if (listOfFiles[i].getName().contains("GA")) {
                        filter[5]++;
                    }
                    if (listOfFiles[i].getName().contains("BW")) {
                        filter[6]++;
                    }
                    if (listOfFiles[i].getName().contains("DOTS")) {
                        filter[7]++;
                    }
                }
                int index = 10;
                int max = filter[0];
                for (int i = 1; i < filter.length; i++) {
                    System.out.println("filter[" + i + "]" + filter[i] + "  max: " + max);
                    if (filter[i] > max) {
                        max = filter[i];
                        index = i;
                    }
                }
                String most_used_filter = null;

                switch (index) {
                    case 0:
                        most_used_filter = "Dilation";
                        break;
                    case 1:
                        most_used_filter = "Sobel";
                        break;
                    case 2:
                        most_used_filter = "Canny";
                        break;
                    case 3:
                        most_used_filter = "Morphing";
                        break;
                    case 4:
                        most_used_filter = "Erosion";
                        break;
                    case 5:
                        most_used_filter = "Gaussian";
                        break;
                    case 6:
                        most_used_filter = "Grayscale";
                        break;
                    case 7:
                        most_used_filter = "Dots";
                        break;
                    case 10:
                        most_used_filter = "null";
                        break;
                }

                if (max == 0) {
                    most_used_filter = null;
                }

                String sql = "UPDATE `users` "
                        + "SET most_used_filter = '" + most_used_filter + "', pictures_no = " + pictures_no + ", folder_size = " + folder_size + "  WHERE username='" + username + "' ";

                System.out.println(sql);
                stmt.executeUpdate(sql);
                System.out.println("Done");
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Map selectAll(String username) throws ClassNotFoundException, SQLException {
        Statement stmt = db_connect();

        String sql = "SELECT * FROM `users` WHERE username='" + username + "'";
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);

        Map m = new HashMap();

        while (rs.next()) {
            //Retrieve by column name
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String department = rs.getString("department");
            String affiliation = rs.getString("affiliation");
            String email = rs.getString("email");
            int login_no = rs.getInt("login_no");
            int pictures_no = rs.getInt("pictures_no");
            int folder_size = rs.getInt("folder_size");
            String most_used_filter = rs.getString("most_used_filter");

            m.put("name", name);
            m.put("surname", surname);
            m.put("department", department);
            m.put("affiliation", affiliation);
            m.put("email", email);
            m.put("login_no", login_no);
            m.put("pictures_no", pictures_no);
            m.put("folder_size", folder_size);
            m.put("most_used_filter", most_used_filter);

            System.out.println("username: " + username);
            System.out.println("affiliation: " + affiliation);
            System.out.println("department: " + department);
            System.out.println("name: " + name);
            System.out.println("surname: " + surname);
            System.out.println("email: " + email);
            System.out.println("login_no: " + login_no);
            System.out.println("pictures_no: " + pictures_no);
            System.out.println("folder_size: " + folder_size);
            System.out.println("most_used_filter: " + most_used_filter);

        }
        System.out.println("Done");
//        rs.close();
        return m;
    }
}
