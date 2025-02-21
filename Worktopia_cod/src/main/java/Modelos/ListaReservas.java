package Modelos;

import Clases.Reservas;
import ConexionDB.ConectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ListaReservas {

    @FXML
    private Button AgregarClientes;
    @FXML
    private Button Reservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button Facturacion;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField DNIbuscar;
    @FXML
    private ListView listaSQL;

    @FXML
    private TableView<Clases.Reservas> tablarReservas;
    @FXML
    private TableColumn<Clases.Reservas, Integer> colNReserva;
    @FXML
    private TableColumn<Clases.Reservas, String> colDNI;
    @FXML
    private TableColumn<Clases.Reservas, Integer> colAsiento;
    @FXML
    private TableColumn<Clases.Reservas, Integer> colFactura;
    @FXML
    private TableColumn<Clases.Reservas, java.sql.Timestamp> colFechaInicio;
    @FXML
    private TableColumn<Clases.Reservas, java.sql.Timestamp> colFechaFin;
    @FXML
    private TableColumn<Clases.Reservas, java.math.BigDecimal> colSubtotal;

    private ObservableList<Clases.Reservas> reservasList;

    @FXML
    public void initialize() {
        colNReserva.setCellValueFactory(new PropertyValueFactory<>("id_reserva"));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colAsiento.setCellValueFactory(new PropertyValueFactory<>("id_asiento"));
        colFactura.setCellValueFactory(new PropertyValueFactory<>("id_factura"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_inicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_fin"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        try {
            loadReservasFromDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadReservasFromDatabase() throws SQLException, ClassNotFoundException {
        List<Reservas> reservas = ConectionDB.getReservas();
        reservasList = FXCollections.observableArrayList(reservas);
        tablarReservas.setItems(reservasList);
    }


    public ListaReservas() {
    }

    public void ventanaRegistro(ActionEvent event) {
        RegistroUsuarios();
        ((Stage) AgregarClientes.getScene().getWindow()).close();
    }

    public void ventanaReservas(ActionEvent event) {
        Reservas();
        ((Stage) Reservas.getScene().getWindow()).close();
    }

    public void ventanaListaClientes(ActionEvent event) {
        ListaUsuarios();
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        Facturaciones();
        ((Stage) Facturacion.getScene().getWindow()).close();
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
