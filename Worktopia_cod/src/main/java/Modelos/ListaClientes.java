package Modelos;

import Clases.Clientes;
import ConexionDB.ConectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ListaClientes {

    @FXML
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button AgregarClientes;
    @FXML
    private Button Reservas;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button Facturacion;
    @FXML
    private Button BtnModificar;
    @FXML
    private Button BtnEliminar;
    @FXML
    private TextField DNIbuscar;
    @FXML
    private TextField TextNombre;
    @FXML
    private TextField TextPrimerApellido;
    @FXML
    private TextField TextSegundoApellido;
    @FXML
    private TextField TextEmail;
    @FXML
    private TextField TextTelefono;
    private String dni;
    @FXML
    private VBox vboxIconos;

    @FXML
    private TableView<Clientes> tablaClientes;
    @FXML
    private TableColumn<Clientes, String> colDNI;
    @FXML
    private TableColumn<Clientes, String> colNombre;
    @FXML
    private TableColumn<Clientes, String> colPrimerApellido;
    @FXML
    private TableColumn<Clientes, String> colSegundoApellido;
    @FXML
    private TableColumn<Clientes, String> colEmail;
    @FXML
    private TableColumn<Clientes, String> colTelefono;


    @FXML
    public void initialize() {
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        colSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));


        ObservableList<Clientes> clientesList = FXCollections.observableArrayList(ConectionDB.getClientes());
        tablaClientes.setItems(clientesList);

        tablaClientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Clientes cliente = tablaClientes.getSelectionModel().getSelectedItem();
                if (cliente != null) {
                    exportarDatos(cliente);
                }
            }
        });

        vboxIconos.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - vboxIconos.getLayoutX() + 250;
            yOffset = event.getSceneY() - vboxIconos.getLayoutY() + 70;
        });
        vboxIconos.setOnMouseDragged(event -> {
            vboxIconos.setLayoutX(event.getScreenX() - xOffset);
            vboxIconos.setLayoutY(event.getScreenY() - yOffset);
        });
    }


    public ListaClientes() {

    }

    public void ventanaRegistro(ActionEvent event) {
        RegistroUsuarios();
        ((Stage) AgregarClientes.getScene().getWindow()).close();
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

    public void salirVbox(ActionEvent event) {
        vboxIconos.setVisible(false);
    }

    public void exportarDatos(Clientes clientes) {
        dni = clientes.getDni();
        TextNombre.setText(clientes.getNombre());
        TextPrimerApellido.setText(clientes.getPrimerApellido());
        TextSegundoApellido.setText(clientes.getSegundoApellido());
        TextEmail.setText(clientes.getEmail());
        TextTelefono.setText(clientes.getTelefono());
        vboxIconos.setVisible(true);
    }

    public void modificarDatos(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere modificar el Cliente con dni " + dni);

        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "UPDATE Clientes SET nombre = ?, primerApellido = ?, segundoApellido = ?, email = ?, telefono = ? WHERE dni = ?";
            try (PreparedStatement update = ConectionDB.getConn().prepareStatement(query)) {
                update.setString(1, TextNombre.getText());
                update.setString(2, TextPrimerApellido.getText());
                update.setString(3, TextSegundoApellido.getText());
                update.setString(4, TextEmail.getText());
                update.setString(5, TextTelefono.getText());
                update.setString(6, dni);
                int actualizado = update.executeUpdate();
                if (actualizado > 0) {
                    alert2.setContentText("Datos actualizados correctamente");
                    alert2.show();
                }

            } catch(SQLException e){
                throw new RuntimeException(e);
            }
            initialize();

        }
    }
    public void eliminarDatos(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere eliminar el Cliente con dni " + dni);
        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "DELETE FROM Clientes WHERE dni = ?";
            try(PreparedStatement delete = ConectionDB.getConn().prepareStatement(query)){
                delete.setString(1, dni);
                int actualizado = delete.executeUpdate();
                if (actualizado > 0) {
                    alert2.setContentText("Datos Eliminados correctamente");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initialize();
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