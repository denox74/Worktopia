package Modelos;

import Clases.Usuarios;
import ConexionDB.ConectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RegistroUsuarios {

    @FXML
    public VBox vboxAgregarUsuario;
    @FXML
    public TextField TextAgregarNombre;
    @FXML
    public TextField TextAgregarEmail;
    @FXML
    public TextField TextAgregarPassword;
    @FXML
    public ComboBox<String> enumAgregarCategoria;
    @FXML
    public Button BtnAgregarUsuario;

    ObservableList<String> categorias = FXCollections.observableArrayList("Admin", "Empleado");

    public RegistroUsuarios() {

    }
    @FXML
    public void initialize() {
        enumAgregarCategoria.setItems(categorias);
    }

    public static void rellenarCombo(ComboBox<String> comboBox, ObservableList<String> categorias) {
        comboBox.setItems(categorias);
    }

    public void llenarComboBox(ActionEvent event) {
        rellenarCombo(enumAgregarCategoria, categorias);
    }
    private void limpiarCampos() {
        TextAgregarNombre.clear();
        TextAgregarEmail.clear();
        TextAgregarPassword.clear();
        enumAgregarCategoria.getSelectionModel().clearSelection();
    }

    public void agregarUsuario(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);

        String query = "INSERT INTO Usuarios (nombre, email, contrasenia,categoria) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = ConectionDB.getConn().prepareStatement(query);
            ps.setString(1, TextAgregarNombre.getText());
            ps.setString(2, TextAgregarEmail.getText());
            ps.setString(3, TextAgregarPassword.getText());
            ps.setString(4, enumAgregarCategoria.getSelectionModel().getSelectedItem().toString());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                alert.setContentText("Registro agregado exitosamente");
                alert.show();
                alert2.setContentText("¿Quiere agregar otro usuario?");

                ButtonType si = new ButtonType("Sí");
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert2.getButtonTypes().setAll(si, no);

                Optional<ButtonType> result = alert2.showAndWait();
                if (result.isPresent() && result.get() == si) {
                    limpiarCampos();
                } else {
                    ((Stage)BtnAgregarUsuario.getScene().getWindow()).close();
                }
            } else {
                alert.setContentText("No se pudo registrar el usuario");
                alert.show();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
