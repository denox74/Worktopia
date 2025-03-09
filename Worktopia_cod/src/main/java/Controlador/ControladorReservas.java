package Controlador;

import ConexionDB.ConectionDB;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

            } catch(SQLException e){
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
}
