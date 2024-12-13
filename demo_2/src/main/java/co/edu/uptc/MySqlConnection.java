package co.edu.uptc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

    private static final String DATABASE = "consultorio_productos";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE;
    private static final String USERNAME = "camilin";
    private static final String PASSWORD = "imaccc";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
