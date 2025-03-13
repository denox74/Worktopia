/**
 * Clase MenuPrincipal que se encarga de la lógica de la ventana de inicio de sesión.
 * En esta clase se valida el usuario y contraseña ingresados por el usuario y se redirige a la ventana de lista de clientes.
 */
package Modelos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipal {

    private ListaUsuarios listaUsuarios;
    @FXML
    private Button BtnEntrar;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField password;

    public MenuPrincipal() {
        listaUsuarios = new ListaUsuarios(this);
    }


    @FXML
    public void logeado(ActionEvent event) {
        String user = usuario.getText();
        String pass = password.getText();
        listaUsuarios.loginUsuarios(user, pass);
        ((Stage) BtnEntrar.getScene().getWindow()).close();
    }

    public void nuevaVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/ListaClientes.fxml"));
            Parent cargaVentana = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(cargaVentana));
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void cerrarVentana() {
        ((Stage) BtnEntrar.getScene().getWindow()).close();

    }


}
