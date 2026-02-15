package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DatabaseConnection() { }

    // Get the connection instance
    public static Connection getInstance() {
        if (connection == null) {
            try {
                // MySQL connection
                String url = "jdbc:mysql://localhost:3306/OceanViewResort";
                String user = "root";       // your DB username
                String password = "Ocean@123";   // your DB password
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connected successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to connect to database.");
            }
        }
        return connection;
    }
}
