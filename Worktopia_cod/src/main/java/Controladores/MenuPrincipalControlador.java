package Controladores;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MenuPrincipalControlador {

    @FXML private Button BotonEntrar;
    @FXML private TextField usuario;
    @FXML private TextField password;

    public MenuPrincipalControlador() {
        ImageView portada = new ImageView("file:src/main/java/Imagenes/ImagenPrincipalLogin.png");
        portada.setFitHeight(500);
        portada.setFitWidth(500);

    }




}
