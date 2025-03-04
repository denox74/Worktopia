package Modelos;


import Aplicaciones.MenuPrincipalApp;
import Clases.Reservas;
import Clases.SesionUsuario;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ListaReservas {

    private double xOffset = 0;
    private double yOffset = 0;
    private int idReserva;
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
    private Button BtnUsuarios;
    @FXML
    private TextField DNIBuscar;
    @FXML
    private TextField TextInicio;
    @FXML
    private TextField TextFin;
    @FXML
    private TextField TextDni;
    @FXML
    private TextField TextAsiento;
    @FXML
    private VBox vboxReservas;
    @FXML
    private TableView<Clases.Reservas> tablaReservas;
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


    @FXML
    public void initialize() {

        colNReserva.setCellValueFactory(new PropertyValueFactory<>("id_reserva"));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colAsiento.setCellValueFactory(new PropertyValueFactory<>("id_asiento"));
        colFactura.setCellValueFactory(new PropertyValueFactory<>("id_factura"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_inicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_fin"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));



        tablaReservas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Reservas reserva = tablaReservas.getSelectionModel().getSelectedItem();
                if (reserva != null) {
                    exportarDatos(reserva);
                }
            }
        });

        vboxReservas.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - vboxReservas.getLayoutX() + 250;
            yOffset = event.getSceneY() - vboxReservas.getLayoutY() + 70;
        });
        vboxReservas.setOnMouseDragged(event -> {
            vboxReservas.setLayoutX(event.getScreenX() - xOffset);
            vboxReservas.setLayoutY(event.getScreenY() - yOffset);
        });

        try {
            loadReservasFromDatabase();
        } catch (SQLException |
                 ClassNotFoundException e) {
            e.printStackTrace();
        }
       inicioSesion();
    }


    private void loadReservasFromDatabase() throws SQLException, ClassNotFoundException {
        List<Reservas> reservas = ConectionDB.getReservas();
        ObservableList<Clases.Reservas> reservasList = FXCollections.observableArrayList(reservas);
        tablaReservas.setItems(reservasList);

        FilteredList<Reservas> filtroReservas = new FilteredList<Reservas>(reservasList, p -> true);
        DNIBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtroReservas.setPredicate(Reservas -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else {
                    return Reservas.getDni().toLowerCase().contains(newValue.toLowerCase());
                }
            });
        });

        SortedList<Reservas> sortedData = new SortedList<>(filtroReservas);
        sortedData.comparatorProperty().bind(tablaReservas.comparatorProperty());
        tablaReservas.setItems(sortedData);


    }


    public ListaReservas() {

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

    public void exportarDatos(Reservas reservas) {
        idReserva = reservas.getId_reserva();
        TextInicio.setText(String.valueOf(reservas.getFecha_hora_inicio()));
        TextFin.setText(String.valueOf(reservas.getFecha_hora_fin()));
        TextDni.setText(reservas.getDni());
        TextAsiento.setText(String.valueOf(reservas.getId_asiento()));
        vboxReservas.setVisible(true);
    }
    public void modificarReservas(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere modificar el Cliente con dni: " + TextDni.getText());

        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "UPDATE Reservas SET dni = ?, fecha_hora_inicio = ?, fecha_hora_fin = ?,id_asiento = ? WHERE id_reserva = ?";
            try (PreparedStatement update = ConectionDB.getConn().prepareStatement(query)) {
                update.setString(1, TextDni.getText());
                update.setString(2, TextInicio.getText());
                update.setString(3, TextFin.getText());
                update.setString(4, TextAsiento.getText());
                update.setInt(5, idReserva);
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
    public void eliminarReservas(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere eliminar el Cliente con dni: " + TextDni.getText());
        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "DELETE FROM Reservas WHERE id_reserva = ?";
            try(PreparedStatement delete = ConectionDB.getConn().prepareStatement(query)){
                delete.setInt(1, idReserva);
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

    public void ventanaListaClientes(ActionEvent event) {
        abrirVentana("/Menus/ListaClientes.fxml", "Lista de Cliente");
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        abrirVentana("/Menus/Facturacion.fxml", "Factuacion");
        ((Stage) Facturacion.getScene().getWindow()).close();
    }
    public void ventanaListaUsuarios(ActionEvent event) {
        abrirVentana("/Menus/ListaUsuarios.fxml", "Usuarios");
        ((Stage) BtnUsuarios.getScene().getWindow()).close();
    }

    public void salirVbox(ActionEvent event) {
        vboxReservas.setVisible(false);
    }

}
