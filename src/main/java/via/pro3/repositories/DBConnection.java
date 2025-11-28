package via.pro3.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/slaughterhouse_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}