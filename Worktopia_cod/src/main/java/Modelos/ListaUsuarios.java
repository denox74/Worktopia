package Modelos;

import Aplicaciones.MenuPrincipalApp;
import Clases.SesionUsuario;
import Clases.Usuarios;
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
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ListaUsuarios {
    private double xOffset = 0;
    private double yOffset = 0;

    private MenuPrincipal menuPrincipal;
    private int idusuario;
    @FXML
    private Button AgregarClientes;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button Facturacion;
    @FXML
    private Button Reservas;
    @FXML
    private Button ponerUsuario;
    @FXML
    private TextField empleado;
    @FXML
    private VBox vboxUsuarios;
    @FXML
    private TextField TextNombre;
    @FXML
    private TextField TextEmail;
    @FXML
    private ComboBox enumCategoria;
    private MenuPrincipalApp menuPrincipalapp = new MenuPrincipalApp();


    ObservableList<String> categorias = FXCollections.observableArrayList("Admin", "Empleado");
    @FXML
    private TableView<Clases.Usuarios> tablaUsuarios;
    @FXML
    private TableColumn<Usuarios, Integer> colEmpleado;
    @FXML
    private TableColumn<Usuarios, String> colNombre;
    @FXML
    private TableColumn<Usuarios, String> colEmail;
    @FXML
    private TableColumn<Usuarios, String> colPassword;
    @FXML
    private TableColumn<Usuarios, String> colCategoria;

    @FXML
    public void initialize() {
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/bannerTopiaC.png")));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("contrasenia"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tablaUsuarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Usuarios usuario = tablaUsuarios.getSelectionModel().getSelectedItem();
                if (usuario != null) {
                    exportarDatos(usuario);
                }
            }
        });

        vboxUsuarios.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - vboxUsuarios.getLayoutX() + 250;
            yOffset = event.getSceneY() - vboxUsuarios.getLayoutY() + 70;
        });
        vboxUsuarios.setOnMouseDragged(event -> {
            vboxUsuarios.setLayoutX(event.getScreenX() - xOffset);
            vboxUsuarios.setLayoutY(event.getScreenY() - yOffset);
        });

        try {
            loadUsuariosFromDatabase();
        } catch (SQLException |
                 ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ListaUsuarios(){

    }


    private void loadUsuariosFromDatabase() throws SQLException, ClassNotFoundException {
        List<Usuarios> usuarios = ConectionDB.getUsuarios();
        ObservableList<Usuarios> usuariosList = FXCollections.observableArrayList(usuarios);
        tablaUsuarios.setItems(usuariosList);

        FilteredList<Usuarios> filtroUsuarios = new FilteredList<>(usuariosList, u -> true);
        empleado.textProperty().addListener((observable, oldValue, newValue) -> {
            filtroUsuarios.setPredicate(usuario -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else {
                    return usuario.getNombre().toLowerCase().contains(newValue.toLowerCase());
                }
            });
        });

        SortedList<Usuarios> sortedData = new SortedList<>(filtroUsuarios);
        sortedData.comparatorProperty().bind(tablaUsuarios.comparatorProperty());
        tablaUsuarios.setItems(sortedData);
    }

    public void exportarDatos(Usuarios usuarios) {
        idusuario = usuarios.getId_usuario();
        TextNombre.setText(usuarios.getNombre());
        TextEmail.setText(usuarios.getEmail());
        enumCategoria.getSelectionModel().select(usuarios.getCategoria());
        vboxUsuarios.setVisible(true);
    }

    public ListaUsuarios(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;


    }

    public void modificarUsuarios(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere modificar El Usuario :" + idusuario);

        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "UPDATE Usuarios SET nombre = ?, email = ?, categoria = ? WHERE id_usuario = ?";
            try (PreparedStatement update = ConectionDB.getConn().prepareStatement(query)) {
                update.setString(1, TextNombre.getText());
                update.setString(2, TextEmail.getText());
                update.setString(3, enumCategoria.getSelectionModel().getSelectedItem().toString());
                update.setInt(4, idusuario);
                int actualizado = update.executeUpdate();
                if (actualizado > 0) {
                    alert2.setContentText("Datos actualizados correctamente");
                    alert2.show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize();

        }
    }

    public void eliminarUsuarios(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiere eliminar el usuario :" + idusuario);
        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String query = "DELETE FROM Usuarios WHERE id_usuario = ?";
            try (PreparedStatement delete = ConectionDB.getConn().prepareStatement(query)) {
                delete.setInt(1, idusuario);
                int actualizado = delete.executeUpdate();
                if (actualizado > 0) {
                    alert2.setContentText("Datos Eliminados correctamente");
                    alert2.show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initialize();
    }
    public void loginUsuarios(String usuario, String pass) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String query = "SELECT nombre, contrasenia, categoria FROM Usuarios WHERE nombre = ? AND contrasenia = ?";

        try (PreparedStatement ps = ConectionDB.getConn().prepareStatement(query)) {
            ps.setString(1, usuario);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String categoria = rs.getString("categoria");
                String user = rs.getString("nombre");
                String password = rs.getString("contrasenia");
                if (user.equals(usuario) && password.equals(pass)) {}
                SesionUsuario.setCategoriaUsuario(categoria); // Guardamos la categoría en la sesión
                alert.setContentText("Login correcto");
                alert.showAndWait();
                menuPrincipal.nuevaVentana();

            } else {
                alert.setContentText("Login incorrecto");
                menuPrincipal.cerrarVentana();
                menuPrincipalapp.start(new Stage());
                alert.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void rellenarCombo(ComboBox<String> comboBox, ObservableList<String> categorias) {
        comboBox.setItems(categorias);
    }

    public void llenarComboBox(ActionEvent event) {
        rellenarCombo(enumCategoria, categorias);
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

    public void ventanaListaClientes(ActionEvent event) {
        abrirVentana("/Menus/ListaClientes.fxml", "Lista de Cliente");
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        abrirVentana("/Menus/Facturacion.fxml", "Factuacion");
        ((Stage) Facturacion.getScene().getWindow()).close();
    }
    public void ventanaListaReservas(ActionEvent event) {
        abrirVentana("/Menus/ListaReservas.fxml", "Listas De Reservas");
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }
    public void ventanaAgregarUsuarios(ActionEvent event) {
        abrirVentana("/Menus/RegistroUsuarios.fxml", "Agregar Usuario");
    }
    public void salirVbox(ActionEvent event) {
        vboxUsuarios.setVisible(false);
    }

}
