package com.gecp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteServlet extends HttpServlet {
    
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get email from the request parameters
        String password = request.getParameter("password");

        // SQL query to delete the user based on the email
        String deleteSQL = "DELETE FROM user_db WHERE password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            
            pstmt.setString(2, password);
            
            // Execute the update
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                // If deletion is successful, redirect to the login page
                response.sendRedirect("Login.html"); // Replace with your actual login page URL
            } else {
                // If no rows were deleted, show an error message (optional)
                response.getWriter().write("No user found with the given email.");
            }
            
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            response.getWriter().write("Error deleting user: " + e.getMessage());
        }
    }
}
