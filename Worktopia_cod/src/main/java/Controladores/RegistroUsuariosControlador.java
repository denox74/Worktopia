package Controladores;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class RegistroUsuariosControlador
{
    @FXML private TextField dni;
    @FXML private TextField nombre;
    @FXML private TextField primerApellido;
    @FXML private TextField segundoApellido;
    @FXML private TextField eMail;
    @FXML private TextField telefono;
    @FXML private Button btnConfirmar;
    @FXML private Button btnCancelar;

    public  RegistroUsuariosControlador(){
    }

}
