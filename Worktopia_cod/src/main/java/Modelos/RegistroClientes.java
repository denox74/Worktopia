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


public class RegistroClientes {
    private final ConectionDB conectionDB = new ConectionDB();
    @FXML private TextField dni;
    @FXML private TextField nombre;
    @FXML private TextField primerApellido;
    @FXML private TextField segundoApellido;
    @FXML private TextField eMail;
    @FXML private TextField telefono;
    @FXML private Button btnConfirmar;
    @FXML private Button btnCancelar;
    @FXML private Button Reservas;
    @FXML private Button ListaReservas;
    @FXML private Button ListaClientes;
    @FXML private Button Facturacion;



    public RegistroClientes(){
    }


    public void ventanaListaUsuario(ActionEvent event){
        ListaUsuarios();
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }
    public void ventanaReservas(ActionEvent event){
        Reservas();
        ((Stage) Reservas.getScene().getWindow()).close();
    }
    public void ventanaListaReservas(ActionEvent event){
        ListaReservas();
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }
    public void ventanaFacturaciones(ActionEvent event){
        Facturaciones();
        ((Stage) Facturacion.getScene().getWindow()).close();
    }
    public void guardarUsuario(ActionEvent event){
        agregarUsuario();
    }

    public void agregarUsuario() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Usuario agregado correctamente");
        String dni = this.dni.getText();
        String nombre = this.nombre.getText();
        String primerApellido = this.primerApellido.getText();
        String segundoApellido = this.segundoApellido.getText();
        String eMail = this.eMail.getText();
        String telefono = this.telefono.getText();
        if (nombre.isEmpty() || primerApellido.isEmpty() || segundoApellido.isEmpty() || eMail.isEmpty() || telefono.isEmpty()) {
            ;alert.setContentText("Debe completar todos los campos");
        }else{
            String query = "INSERT INTO Clientes (nombre, eMail, telefono) " +
                           "VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement stmt = conectionDB.getConn().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setString(2, (nombre + " " + primerApellido + " " + segundoApellido));
                stmt.setString(3, eMail);
                stmt.setString(4, telefono);
                stmt.executeUpdate();
            } catch (Exception e) {
                alert.setContentText("Error al agregar usuario");
                alert.show();
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
        public void Reservas() {
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
        public void ListaReservas() {
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
        public void Facturaciones() {
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
