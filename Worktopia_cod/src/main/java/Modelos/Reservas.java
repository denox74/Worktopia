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

        Bounds alinear = BtnSeleccion.localToScreen(BtnSeleccion.getBoundsInLocal());
        contenedorHorarios.setTranslateX(alinear.getMinX());
        contenedorHorarios.setTranslateY(alinear.getMinY());
        contenedorHorarios.setVisible(true);
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
