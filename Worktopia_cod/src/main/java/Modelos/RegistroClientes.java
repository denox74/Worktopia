package Modelos;

import ConexionDB.ConectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistroClientes {

    private ListaClientes listaClientes = new ListaClientes();
    @FXML
    private TextField dni;
    @FXML
    private TextField nombre;
    @FXML
    private TextField primerApellido;
    @FXML
    private TextField segundoApellido;
    @FXML
    private TextField eMail;
    @FXML
    private TextField telefono;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button BtnUsuarios;
    @FXML
    private Button Reservas;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button Facturacion;

    @FXML
    public void initialize() {

    }

    public RegistroClientes() {
    }


    public void guardarCliente(ActionEvent event) {
        agregarCliente();

    }


    public void agregarCliente() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String dniText = this.dni.getText();
        String nombreText = this.nombre.getText();
        String primerApellidoText = this.primerApellido.getText();
        String segundoApellidoText = this.segundoApellido.getText();
        String EmailText = this.eMail.getText();
        String telefonoText = this.telefono.getText();

        if (dniText.isEmpty() || nombreText.isEmpty() || primerApellidoText.isEmpty() || segundoApellidoText.isEmpty() || EmailText.isEmpty() || telefonoText.isEmpty()) {
            alert.setContentText("Debe completar todos los campos");
            alert.show();
        } else {
            try {
                String query = "INSERT INTO Clientes (dni, nombre, primerApellido, segundoApellido, eMail, telefono) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(query)) {
                    stmt.setString(1, dni.getText());
                    stmt.setString(2, nombre.getText());
                    stmt.setString(3, primerApellido.getText());
                    stmt.setString(4, segundoApellido.getText());
                    stmt.setString(5, eMail.getText());
                    stmt.setString(6, telefono.getText());

                    int rowsInserted = stmt.executeUpdate();
                        if (rowsInserted > 0) {
                            alert.setContentText("Usuario agregado correctamente");
                            alert.show();

                        } else {
                        alert.setContentText("Usuario no agregado");
                        alert.show();
                        }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }


    }


}
