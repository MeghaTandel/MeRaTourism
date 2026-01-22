package com.gecp.servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;


@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Users List</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; color: #333; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background: url('https://wallpaperbat.com/img/435283-free-inspirational-travel-desktop-amp-phone-wallpaper-traveling.jpg') no-repeat center center/cover; }");
        out.println(".container { background-color: rgba(255, 255, 255, 0.5); padding: 50px; border-radius: 25px; box-shadow: 10px 4px 8px rgba(0, 0, 0, 0.5); }");
        out.println("h2 { text-align: center; color: blueviolet; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        out.println("th, td { padding: 10px; border-bottom: 1px solid #ddd; text-align: center; }");
        out.println("th { background-color: blueviolet; color: white; }");
        out.println("tr:hover { background-color: #f1f1f1; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>Registered Users</h2>");
        out.println("<table>");
        out.println("<tr><th>Username</th><th>Email</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user_db");

            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");

                out.println("</tr><td>" + username + "</td><td>" + email + "</td></tr>");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<p>Error retrieving users: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
