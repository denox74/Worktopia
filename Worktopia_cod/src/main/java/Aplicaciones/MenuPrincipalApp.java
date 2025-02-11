package Aplicaciones;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuPrincipalApp extends Application {

    @Override
    public void start(Stage menu) throws IOException {
        FXMLLoader menuPrincipal = new FXMLLoader(MenuPrincipalApp.class.getResource("/Menus/RegistroUsuarios.fxml"));
        menu.setTitle("Menu Principal");
        menu.setScene(new Scene(menuPrincipal.load()));
        menu.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
