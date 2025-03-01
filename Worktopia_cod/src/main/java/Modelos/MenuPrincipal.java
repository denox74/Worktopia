package Modelos;


import Clases.Usuarios;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipal {

    @FXML
    private Button BtnEntrar;
    @FXML
    private TextField usuario;
    @FXML
    private TextField password;

    public MenuPrincipal() {

    }


    @FXML
    public void logeado(ActionEvent event) {
        String user = usuario.getText();
        String pass = password.getText();
        if (user.equals("admin") && pass.equals("admin")) {
            alerta("Login correcto");
            nuevaVentana();
            ((Stage) BtnEntrar.getScene().getWindow()).close();
        } else if (!user.equals("admin") && !pass.equals("admin")) {
            alerta("Login incorrecto");

        } else {
            alerta("Tienes que rellenar los campos");
        }
    }

    public void nuevaVentana() {
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

    public void alerta(String titulo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("NOTIFICACIÓN");
        alert.setHeaderText(null);
        alert.setContentText(titulo);
        alert.showAndWait();

    }


}
