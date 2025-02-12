package ConexionDB;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectionDB {

    private static final String URL = "jdbc:mysql://bd-4free.net";
    private static final String PORT = "3306";
    private static final String USER = "worktopia";
    private static final String PASSWORD = "iesrincon";
    private static final String DATABASE = "worktopiaDB";
    private static Statement stmt;
    private static Connection conn;
    private static ResultSet rs;

    //Abrir la conexión de la BBDD
    public static void openConn() {
        try {
        // Primero se comprueba que carga el controlador (Si está la librería necesaria)
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {

        }

        // Seguidamente se intenta establecer al conexión
        try {
            String sUrl = URL + ":" + PORT + "/" + DATABASE + "?zeroDateTimeBehavior=convertToNull";
            conn = DriverManager.getConnection(sUrl, USER, PASSWORD);
            System.out.println(sUrl);
        } catch (Exception e) {

        }

        // Por último se carga el objeto de la clase Statement que se utilizará para realizar las consultas
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
        }

    }

    public static Connection getConn() {
        return conn;
    }

    //Cuando se cierre la aplicación hay que cerrar la conexión a la BBDD
    public static void closeConn() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Statement getStmt() {
        return stmt;
    }
}


