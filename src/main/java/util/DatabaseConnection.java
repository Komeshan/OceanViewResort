package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getInstance() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/oceanviewresort";
                String user = "root";
                String password = "Ocean@123";
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("MySQL Driver not found!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to connect to database.");
            }
        }
        return connection;
    }
}
