package Clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class TestDBConnection {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://db4free.net:3306/worktopiadb?zeroDateTimeBehavior=convertToNull";
        String user = "worktopia";
        String password = "iesrincon";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

}
