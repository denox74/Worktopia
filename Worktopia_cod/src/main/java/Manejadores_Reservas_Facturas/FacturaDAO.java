package Manejadores_Reservas_Facturas;
import java.sql.*;

public class FacturaDAO {
    private Connection conexion;

    public FacturaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int obtenerUltimaFacturaPendiente(String dniCliente) throws SQLException {
        String sql = "SELECT id_factura FROM Factura WHERE dni_cliente = ? AND estado = 'pendiente' ORDER BY id_factura DESC LIMIT 1";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, dniCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_factura"); // Retorna el ID de la última factura pendiente
            }
        }
        return -1; // Si no hay factura pendiente, retornamos -1
    }

    public int crearNuevaFactura(String dniCliente) throws SQLException {
        String sql = "INSERT INTO Factura (dni_cliente, estado) VALUES (?, 'pendiente')";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dniCliente);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Retorna el ID de la nueva factura
            }
        }
        throw new SQLException("Error al crear la factura.");
    }
}