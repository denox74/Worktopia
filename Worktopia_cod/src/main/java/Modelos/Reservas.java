package Modelos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Reservas {

    @FXML
    private Button AgregarClientes;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button Facturacion;
    @FXML
    private Button btnConfirmar;
    @FXML
    private TextField espacio;
    @FXML
    private TextField horario;
    @FXML
    private TextField precio;
    @FXML
    private VBox contenedorHorarios;
    @FXML
    private Button Mesa1;
    @FXML
    private Button Mesa2;
    @FXML
    private Button Mesa3;
    @FXML
    private Button Mesa4;
    @FXML
    private Button Mesa5;
    @FXML
    private Button Mesa6;
    @FXML
    private Button Mesa7;
    @FXML
    private Button Mesa8;
    @FXML
    private Button Mesa9;
    @FXML
    private Button Mesa10;
    @FXML
    private Button Mesa11;
    @FXML
    private Button Mesa12;
    @FXML
    private Button Mesa13;
    @FXML
    private Button Mesa14;
    @FXML
    private Button Mesa15;
    @FXML
    private Button Mesa16;
    @FXML
    private Button Mesa17;
    @FXML
    private Button Mesa18;
    @FXML
    private Button BtnOficina1;
    @FXML
    private Button BtnOficina2;
    @FXML
    private Button BtnConferencias1;
    @FXML
    private Button BtnConferencias2;

    private Button BtnSeleccionado;


    public Reservas() {

    }

    public void ventanaRegistro(ActionEvent event) {
        RegistroUsuarios();
        ((Stage) AgregarClientes.getScene().getWindow()).close();
    }

    public void ventanaListaReservas(ActionEvent event) {
        ListaReservas();
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }

    public void ventanaListaClientes(ActionEvent event) {
        ListaUsuarios();
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        Facturaciones();
        ((Stage) Facturacion.getScene().getWindow()).close();
    }

    public void ventanaHorarios(ActionEvent event) {
        Button BtnSeleccion = (Button) event.getSource();
        if (contenedorHorarios.isVisible() && BtnSeleccionado == BtnSeleccion) {
            cierreVentanaHorarios();
            return;
        }

        if (BtnSeleccionado != null && BtnSeleccionado != BtnSeleccion) {
            cierreVentanaHorarios();
        }
        BtnSeleccionado = BtnSeleccion;
        contenedorHorarios.setVisible(true);
        Bounds bounds = BtnSeleccionado.localToScreen(BtnSeleccionado.getBoundsInLocal());
        contenedorHorarios.setLayoutX(bounds.getMinX() - 140);
        contenedorHorarios.setLayoutY(bounds.getMinY() - 60);
        if (BtnSeleccionado == Mesa1 || BtnSeleccionado == Mesa2 || BtnSeleccionado == Mesa3 ||
                BtnSeleccionado == Mesa4 || BtnSeleccionado == Mesa7 || BtnSeleccionado == Mesa8 ||
                BtnSeleccionado == Mesa9 || BtnSeleccionado == Mesa10 || BtnSeleccionado == Mesa13 ||
                BtnSeleccionado == Mesa14 || BtnSeleccionado == Mesa15 || BtnSeleccionado == Mesa16) {
            contenedorHorarios.setLayoutX(bounds.getMinX() - 200);
            contenedorHorarios.setLayoutY(bounds.getMinY() - 150);
        } else if (BtnSeleccionado == Mesa5 || BtnSeleccionado == Mesa6 || BtnSeleccionado == Mesa11 ||
                BtnSeleccionado == Mesa12 || BtnSeleccionado == Mesa17 || BtnSeleccionado == Mesa18) {
            contenedorHorarios.setLayoutX(bounds.getMinX() - 200);
            contenedorHorarios.setLayoutY(bounds.getMinY() - 200);
        }
    }

    public void cierreVentanaHorarios() {
        contenedorHorarios.setVisible(false);
        BtnSeleccionado = null;
    }

    public void RegistroUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/RegistroClientes.fxml"));
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

    public void ListaUsuarios() {
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
