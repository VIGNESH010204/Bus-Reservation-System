package com.reservation.storage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bus_reservation_db";
    private static final String USER = "root";      // your DB username
    private static final String PASSWORD = "mysql@123";  // your DB password

    static {
        try {
            // Load MySQL driver (required in some environments)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

