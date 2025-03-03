package Modelos;

import Aplicaciones.MenuPrincipalApp;
import Clases.Clientes;
import Clases.SesionUsuario;
import ConexionDB.ConectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
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
    private Button BtnUsuarios;
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
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/bannerTopiaC.png")));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        colSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));


        ObservableList<Clientes> clientesList = FXCollections.observableArrayList(ConectionDB.getClientes());
        tablaClientes.setItems(clientesList);

        FilteredList<Clientes> filtroClientes = new FilteredList<Clientes>(clientesList,p -> true);
        DNIbuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtroClientes.setPredicate(Clientes->{
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }else{
                    return Clientes.getDni().toLowerCase().contains(newValue.toLowerCase());
                }
            });
        });

        SortedList<Clientes> sortedData = new SortedList<>(filtroClientes);
        sortedData.comparatorProperty().bind(tablaClientes.comparatorProperty());
        tablaClientes.setItems(sortedData);



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
        inicioSesion();
    }


    public ListaClientes() {

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
        alert.setContentText("Quiere modificar el Cliente con dni: " + dni);

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
        alert.setContentText("Quiere eliminar el Cliente con dni: " + dni);
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
    public void inicioSesion() {
        String categoria = SesionUsuario.getCategoriaUsuario();

        if (categoria != null && categoria.equals("Admin")) {
            BtnUsuarios.setVisible(true);
            BtnUsuarios.setDisable(false);
        } else {
            BtnUsuarios.setVisible(false);
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

    public void ventanaRegistro(ActionEvent event) {
        abrirVentana("/Menus/RegistroClientes.fxml", "Agregar Cliente");
        ((Stage) AgregarClientes.getScene().getWindow()).close();
    }

    public void ventanaReservas(ActionEvent event) {
        abrirVentana("/Menus/Reservas.fxml", "Reservas");
        ((Stage) Reservas.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        abrirVentana("/Menus/Facturacion.fxml", "Factuacion");
        ((Stage) Facturacion.getScene().getWindow()).close();
    }
    public void ventanaListaReservas(ActionEvent event) {
        abrirVentana("/Menus/ListaReservas.fxml", "Listas De Reservas");
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }

    public void ventanaListaUsuarios(ActionEvent event) {
        abrirVentana("/Menus/ListaUsuarios.fxml", "Lista De Usuarios");
        ((Stage) BtnUsuarios.getScene().getWindow()).close();
    }
    public void salirVbox(ActionEvent event) {
        vboxIconos.setVisible(false);
    }

}