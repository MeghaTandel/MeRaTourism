package com.gecp.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {

    // JDBC Database credentials
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/project";
    private static final String JDBC_USER = "root"; // Your MySQL username
    private static final String JDBC_PASSWORD = "root"; // Your MySQL password

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // Get the form data
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    System.out.println("1");
    System.out.println("username " + username);
    System.out.println("email" + email);
    System.out.println("password" + password);

    // Validate input
    if (username == null || email == null || password == null
            || username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
        // If any field is empty, forward to registration page with error message
        System.out.println("2");
        request.setAttribute("errorMessage", "All fields must be filled.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Register.html");
        dispatcher.forward(request, response);
        return;
    }

    System.out.println("3");
    // Database connection and statement
    Connection conn = null;
    PreparedStatement pstmtCheck = null;
    PreparedStatement pstmtInsert = null;
    ResultSet rs = null;

    System.out.println("4");
    try {
        // Load MySQL JDBC driver (necessary for some environments)
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish the database connection
        conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        System.out.println("5");
        // Check if the email already exists in the database
        String checkSQL = "SELECT * FROM user_db WHERE email = ?";
        pstmtCheck = conn.prepareStatement(checkSQL);
        pstmtCheck.setString(1, email);
        rs = pstmtCheck.executeQuery();
        System.out.println("6");
        if (rs.next()) {
            // Email already exists, redirect to registration page with error message
            request.setAttribute("errorMessage", "Email is already registered.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Register.html");
            System.out.println("7");
            dispatcher.forward(request, response);
                return;

        }
        System.out.println("8");
        // SQL to insert new user
        String insertSQL = "INSERT INTO user_db (username, email, password) VALUES (?, ?, ?)";
        pstmtInsert = conn.prepareStatement(insertSQL);
        pstmtInsert.setString(1, username);
        pstmtInsert.setString(2, email);
        pstmtInsert.setString(3, password);
        System.out.println("Query " + insertSQL);
        // Execute insert
        int rowsAffected = pstmtInsert.executeUpdate();
        System.out.println("No of rowsAffected: " + rowsAffected);
        if (rowsAffected > 0) {
            // If successful, redirect to the login page
            response.sendRedirect("Login.html");
            

        } else {
            // In case of failure, display an error
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Register.html");
            dispatcher.forward(request, response);
        }

    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        // Handle database errors
        request.setAttribute("errorMessage", "Database error. Please try again later.");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Register.html");
        dispatcher.forward(request, response);
    } finally {
        // Close resources
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmtCheck != null) {
                pstmtCheck.close();
            }
            if (pstmtInsert != null) {
                pstmtInsert.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
