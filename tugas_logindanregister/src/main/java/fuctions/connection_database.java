package fuctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection_database {

    // Jadikan metode ini static
    public static Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/inventaris_logistik";
        String user = "root";
        String password = ""; // Pastikan password sesuai dengan konfigurasi MySQL Anda
        
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error establishing connection: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        try {
            Connection c = connection_database.getConnection();
            if (c != null) {
                System.out.println(String.format("Connected to database %s Success", c.getCatalog()));
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
