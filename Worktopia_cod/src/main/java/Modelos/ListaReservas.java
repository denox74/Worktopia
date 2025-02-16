package Modelos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class ListaReservas {

    @FXML private Button AgregarClientes;
    @FXML private Button Reservas;
    @FXML private Button ListaClientes;
    @FXML private Button Facturacion;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private TextField DNIbuscar;
    @FXML private ListView listaSQL;

    public ListaReservas(){
    }
    public void ventanaRegistro(ActionEvent event){
        RegistroUsuarios();
        ((Stage) AgregarClientes.getScene().getWindow()).close();
    }
    public void ventanaReservas(ActionEvent event){
        Reservas();
        ((Stage) Reservas.getScene().getWindow()).close();
    }
    public void ventanaListaClientes(ActionEvent event){
        ListaUsuarios();
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }
    public void ventanaFacturaciones(ActionEvent event){
        Facturaciones();
        ((Stage) Facturacion.getScene().getWindow()).close();
    }

    public void RegistroUsuarios(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/RegistroUsuarios.fxml"));
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
