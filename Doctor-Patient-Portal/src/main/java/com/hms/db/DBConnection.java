package com.hms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:oracle:thin:@//localhost:1521/orclpdb.168.1.66";

    private static final String USER = "DOCTOR_APP";
    private static final String PASSWORD = "sys";

    public static Connection getConn() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection conn = DriverManager.getConnection(
                URL, USER, PASSWORD
            );

            System.out.println("Oracle connection successful!");
            return conn;

        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException(
                "Oracle database connection failed.", e
            );
        }
    }
}