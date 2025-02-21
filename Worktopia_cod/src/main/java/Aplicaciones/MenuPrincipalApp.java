package Aplicaciones;


import ConexionDB.ConectionDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuPrincipalApp extends Application {

    @Override
    public void start(Stage menu) throws IOException {
        FXMLLoader menuPrincipal = new FXMLLoader(MenuPrincipalApp.class.getResource("/Menus/MenuPrincipal.fxml"));
        menu.setTitle("Menu Principal");
        menu.setScene(new Scene(menuPrincipal.load()));
        menu.getMaxHeight();
        menu.setMaxWidth(1300);
        menu.show();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ConectionDB.openConn();
        ConectionDB.testConnection();
        launch();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConectionDB.closeConn();
            System.out.println("Connection closed.");
        }));
    }
}
