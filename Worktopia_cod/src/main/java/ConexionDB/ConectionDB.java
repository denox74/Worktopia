package ConexionDB;

import Clases.Clientes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    public static void openConn() throws ClassNotFoundException {
        // Hay que asegurar que ejecuta el DRIVER de MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {

        } catch (Exception ex) {

        }

        // Seguidamente se intenta establecer al conexión
        try {
            String sUrl = URL + ":" + PORT + "/" + DATABASE + "?zeroDateTimeBehavior=convertToNull";
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/worktopiadb?zeroDateTimeBehavior=convertToNull", USER, PASSWORD);
            System.out.println("Connected to: " + sUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Por último se carga el objeto de la clase Statement que se utilizará para realizar las consultas
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }

    //Cuando se cierre la aplicación hay que cerrar la conexión a la BBDD
    public static void closeConn() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Statement getStmt() {
        return stmt;
    }



    // Método de prueba para verificar la conexión
    public static void testConnection() throws ClassNotFoundException {
        openConn();
        if (conn != null) {
            System.out.println("Connection test successful.");
        } else {
            System.out.println("Connection test failed.");
        }
        closeConn();
    }



    // Adquirir Clientes




    public static List<Clientes> getClientes() {
        List<Clientes> clientes = new ArrayList<>();
        String query = "SELECT id_cliente, dni, nombre, primerApellido, segundoApellido, email, telefono FROM Clientes";
        try {
            openConn();
            if (conn != null) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Clientes cliente = new Clientes(
                            rs.getInt("id_cliente"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("primerApellido"),
                            rs.getString("segundoApellido"),
                            rs.getString("email"),
                            rs.getString("telefono")

                    );
                    clientes.add(cliente);
                }
                rs.close();
            }
            closeConn();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    //Test de clientes, adquirir una lista de clientes
    public static void testGetClientes() {
        List<Clientes> clientes = ConectionDB.getClientes();
        for (Clientes cliente : clientes) {
            System.out.println(cliente);
        }
    }


}




