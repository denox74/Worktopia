package ConexionDB;

import Clases.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectionDB {
//     private static final String URL = "jdbc:mysql://bd-4free.net";
    private static final String URL = "jdbc:mysql://bt33sWO8fH9pN8LVVDGr@bwj6lss5tgchvux8qwv9-mysql.services.clever-cloud.com:3306/bwj6lss5tgchvux8qwv9";
    private static final String PORT = "3306";
    //private static final String USER = "worktopia";
    private static final String USER = "uqxj2bxqdgq4e1fp";
    //private static final String PASSWORD = "iesrincon";
    private static final String PASSWORD = "bt33sWO8fH9pN8LVVDGr";
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
            //conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/worktopiadb?zeroDateTimeBehavior=convertToNull", USER, PASSWORD);
            conn = DriverManager.getConnection("jdbc:mysql://uqxj2bxqdgq4e1fp:bt33sWO8fH9pN8LVVDGr@bwj6lss5tgchvux8qwv9-mysql.services.clever-cloud.com:3306/bwj6lss5tgchvux8qwv9", USER, PASSWORD);
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
        if (conn != null) {
            System.out.println("Connection test successful.");
        } else {
            System.out.println("Connection test failed.");
        }
    }



    // Adquirir Clientes
    public static List<Clientes> getClientes() {
        List<Clientes> clientes = new ArrayList<>();
        String query = "SELECT dni, nombre, primerApellido, segundoApellido, email, telefono FROM Clientes";
        try {
            if (conn != null) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Clientes cliente = new Clientes(
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }


    // Adquirir Asientos
    public static List<Asientos> getAsientos() {
        List<Asientos> asientos = new ArrayList<>();
        String query = "SELECT id_asiento, nombre, tarifa_hora FROM Asientos";
        try {
            if (conn != null) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Asientos asiento = new Asientos(
                            rs.getInt("id_asiento"),
                            rs.getString("nombre"),
                            rs.getBigDecimal("tarifa_hora")

                    );
                    asientos.add(asiento);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asientos;
    }


    // Adquirir Reservas
    public static List<Reservas> getReservas() {
        List<Reservas> reservas = new ArrayList<>();
        String query = "SELECT id_reserva, dni, id_asiento, id_factura, fecha_hora_inicio, fecha_hora_fin FROM Reservas";
        String updateQuery = "UPDATE Reservas SET subtotal = ? WHERE id_reserva = ?";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reservas reserva = new Reservas(
                        rs.getInt("id_reserva"),
                        rs.getString("dni"),
                        rs.getInt("id_asiento"),
                        rs.getInt("id_factura"),
                        rs.getTimestamp("fecha_hora_inicio"),
                        rs.getTimestamp("fecha_hora_fin")
                );
                // Calculate the subtotal
                BigDecimal subtotal = reserva.calcularSubtotal();

                // Update the subtotal in the database
                try (PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {
                    updatePs.setBigDecimal(1, subtotal);
                    updatePs.setInt(2, reserva.getId_reserva());
                    updatePs.executeUpdate();
                }

                reserva.setSubtotal(subtotal);
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }


    // Adquirir Facturas

    public static List<Facturas> getFacturas() {
        List<Facturas> facturas = new ArrayList<>();
        String query = "SELECT id_factura, dni, precio_total, descuento, fecha_hora_emision, estado, fecha_hora_pago FROM Facturas";
        try {
            if (conn != null) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Facturas factura = new Facturas(
                            rs.getInt("id_factura"),
                            rs.getString("dni"),
                            rs.getBigDecimal("precio_total"),
                            rs.getBigDecimal("descuento"),
                            rs.getString("fecha_hora_emision"),
                            rs.getString("estado"),
                            rs.getString("fecha_hora_pago")
                    );
                    facturas.add(factura);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }




    // Adquirir Usuarios
    public static List<Usuarios> getUsuarios() {
        List<Usuarios> usuarios = new ArrayList<>();
        String query = "SELECT id_usuario, nombre, email, contrasenia, categoria FROM Usuarios";
        try {
            if (conn != null) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Usuarios usuario = new Usuarios(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contrasenia"),
                            rs.getString("categoria")
                    );
                    usuarios.add(usuario);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }



/////////////////////////////////////////////TESTS//////////////////////////////////////////////////////////

    //Test de clientes, adquirir una lista de clientes
    public static void testGetClientes() {
        List<Clientes> clientes = ConectionDB.getClientes();
        for (Clientes cliente : clientes) {
            System.out.println(cliente);
        }
    }


    //Test de asientos, adquirir una lista de asientos
    public static void testGetAsientos() {
        List<Asientos> asientos = ConectionDB.getAsientos();
        for (Asientos asiento : asientos) {
            System.out.println(asiento);
        }
    }

    //Test de reservas, adquirir una lista de reservas
    public static void testGetReservas() {
        List<Reservas> reservas = ConectionDB.getReservas();
        for (Reservas reserva : reservas) {
            System.out.println(reserva);
        }
    }


}