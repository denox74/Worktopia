package Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ListaUsuarios {

    @FXML private Button AgregarClientes;
    @FXML private Button Reservas;
    @FXML private Button ListaReservas;
    @FXML private Button ListaClientes;
    @FXML private Button Facturacion;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private ListView listaSQL;
    @FXML private TextField DNIbuscar;
    @FXML private Button btnSalirLogin;
    @FXML private TextField numeroUsuario;
    @FXML private TextField nombreUsuario;

    public ListaUsuarios(){
    }
}
