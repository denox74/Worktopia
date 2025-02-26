package Modelos;

import ConexionDB.ConectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistroClientes {
    private final ConectionDB conectionDB = new ConectionDB();
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
    private Button Reservas;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button Facturacion;


    public RegistroClientes() {
    }


    public void ventanaListaUsuario(ActionEvent event) {
        ListaUsuarios();
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaReservas(ActionEvent event) {
        Reservas();
        ((Stage) Reservas.getScene().getWindow()).close();
    }

    public void ventanaListaReservas(ActionEvent event) {
        ListaReservas();
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        Facturaciones();
        ((Stage) Facturacion.getScene().getWindow()).close();
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
                    alert.setContentText(rowsInserted > 0 ? "Usuario agregado correctamente" : "No se pudo agregar el usuario");
                    alert.show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }

            public void ListaUsuarios () {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/ListaClientes.fxml"));
                    Parent cargaVentana = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(cargaVentana));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            public void Reservas () {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/Reservas.fxml"));
                    Parent cargaVentana = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(cargaVentana));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            public void ListaReservas () {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/ListaReservas.fxml"));
                    Parent cargaVentana = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(cargaVentana));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            public void Facturaciones () {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/Facturacion.fxml"));
                    Parent cargaVentana = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(cargaVentana));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }
