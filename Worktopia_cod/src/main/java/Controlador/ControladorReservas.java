package Controlador;

import ConexionDB.ConectionDB;
import com.almasb.fxgl.scene3d.Cone;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class ControladorReservas {


    public void modificarReservas(TextField dni, TextField fechaInicio, TextField fechaFin, TextField idAsiento, int idReserva) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere modificar el Cliente con dni: " + dni.getText());

        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "UPDATE Reservas SET dni = ?, fecha_hora_inicio = ?, fecha_hora_fin = ?,id_asiento = ? WHERE id_reserva = ?";
            try (PreparedStatement update = ConectionDB.getConn().prepareStatement(query)) {
                update.setString(1, dni.getText());
                update.setString(2, fechaInicio.getText());
                update.setString(3, fechaFin.getText());
                update.setString(4, idAsiento.getText());
                update.setInt(5, idReserva);
                int actualizado = update.executeUpdate();
                if (actualizado > 0) {
                    alert2.setContentText("Datos actualizados correctamente");
                    alert2.show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void eliminarReservas(TextField dni, int idReserva) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere eliminar el Cliente con dni: " + dni.getText());
        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "DELETE FROM Reservas WHERE id_reserva = ?";
            try (PreparedStatement delete = ConectionDB.getConn().prepareStatement(query)) {
                delete.setInt(1, idReserva);
                int actualizado = delete.executeUpdate();
                if (actualizado > 0) {
                    alert2.setContentText("Datos Eliminados correctamente");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String obtenerEmailCliente(String dni) {
        String email = "";
        String query = "SELECT email FROM Clientes WHERE dni = ?";
        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(query)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    public String obtenerNombreCliente(String dni) {
        String nombre = "";
        String query = "SELECT nombre FROM Clientes WHERE dni = ?";
        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(query)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    public String obtenerForma(String dni) {
        String estado = "";
        String query = "SELECT estado FROM Facturas WHERE dni = ?";
        try (PreparedStatement ps = ConectionDB.getConn().prepareStatement(query)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                estado = rs.getString("estado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estado;
    }
    public double facturaPrecioText(TextField nombreEspacio, TextField horaInicio, TextField horaFin) {
        String consulta = "SELECT tarifa_hora FROM Asientos WHERE nombre = ?";

        String inicioTexto = horaInicio.getText();
        String finTexto = horaFin.getText();

        double tarifa = 0;
        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(consulta)) {
            stmt.setString(1, nombreEspacio.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tarifa = rs.getInt("tarifa_hora");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }


        try {
            LocalTime inicio = LocalTime.parse(inicioTexto);
            LocalTime fin = LocalTime.parse(finTexto);

            double horasPasadas = (double) ChronoUnit.HOURS.between(inicio, fin);
            return horasPasadas * tarifa;

        } catch (Exception e) {
            return 0;
        }

    }
    public int obtenerIdEspacio(String nombreEspacio) {
        String consulta = "SELECT id_asiento FROM Asientos WHERE nombre = ?";
        int idEspacio = -1;
        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(consulta)) {
            stmt.setString(1, nombreEspacio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idEspacio = rs.getInt("id_asiento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idEspacio;
    }



}
