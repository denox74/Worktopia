package Modelos;

import Aplicaciones.MenuPrincipalApp;
import Clases.SesionUsuario;
import ConexionDB.ConectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistroClientes {
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
    public void initialize(){
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/bannerTopiaC.png")));
        inicioSesion();
    }

    public RegistroClientes() {
    }





    public void inicioSesion() {
        String categoria = SesionUsuario.getCategoriaUsuario();

        if (categoria != null && categoria.equals("Admin")) {
            BtnUsuarios.setVisible(true);
            BtnUsuarios.setDisable(false);
        } else {
            BtnUsuarios.setVisible(false);
        }
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

    public void abrirVentana(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);

            MenuPrincipalApp.agregarIcono(stage);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ventanaReservas(ActionEvent event) {
        abrirVentana("/Menus/Reservas.fxml", "Reservas");
        ((Stage) Reservas.getScene().getWindow()).close();
    }

    public void ventanaListaClientes(ActionEvent event) {
        abrirVentana("/Menus/ListaClientes.fxml", "Lista de Cliente");
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        abrirVentana("/Menus/Facturacion.fxml", "Factuacion");
        ((Stage) Facturacion.getScene().getWindow()).close();
    }
    public void ventanaUsuarios(ActionEvent event) {
        abrirVentana("/Menus/ListaUsuarios.fxml", "Usuarios");
        ((Stage) BtnUsuarios.getScene().getWindow()).close();
    }
    public void ventanaListaReservas(ActionEvent event) {
        abrirVentana("/Menus/ListaReservas.fxml", "Lista de Reservas");
        ((Stage) BtnUsuarios.getScene().getWindow()).close();
    }


}
