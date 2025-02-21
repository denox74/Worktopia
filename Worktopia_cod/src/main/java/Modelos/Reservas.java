package Modelos;

import ConexionDB.ConectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

public class Reservas {

    private final ConectionDB conectionDB = new ConectionDB();
    @FXML
    private Button AgregarClientes;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button Facturacion;
    @FXML
    private Button btnConfirmar;
    @FXML
    private TextField espacio;
    @FXML
    private TextField horaInicio;
    @FXML
    private TextField horaFin;
    @FXML
    private TextField precio;
    @FXML
    private DatePicker fecha;
    @FXML
    private VBox contenedorHorarios;
    @FXML
    private Button Mesa1;
    @FXML
    private Button Mesa2;
    @FXML
    private Button Mesa3;
    @FXML
    private Button Mesa4;
    @FXML
    private Button Mesa5;
    @FXML
    private Button Mesa6;
    @FXML
    private Button Mesa7;
    @FXML
    private Button Mesa8;
    @FXML
    private Button Mesa9;
    @FXML
    private Button Mesa10;
    @FXML
    private Button Mesa11;
    @FXML
    private Button Mesa12;
    @FXML
    private Button Mesa13;
    @FXML
    private Button Mesa14;
    @FXML
    private Button Mesa15;
    @FXML
    private Button Mesa16;
    @FXML
    private Button Mesa17;
    @FXML
    private Button Mesa18;
    @FXML
    private Button BtnOficina1;
    @FXML
    private Button BtnOficina2;
    @FXML
    private Button BtnConferencias1;
    @FXML
    private Button BtnConferencias2;

    private Button BtnSeleccionado;


    public Reservas() {

    }

    public void ventanaRegistro(ActionEvent event) {
        RegistroUsuarios();
        ((Stage) AgregarClientes.getScene().getWindow()).close();
    }

    public void ventanaListaReservas(ActionEvent event) {
        ListaReservas();
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }

    public void ventanaListaClientes(ActionEvent event) {
        ListaUsuarios();
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        Facturaciones();
        ((Stage) Facturacion.getScene().getWindow()).close();
    }

    // configuracion de ventana de apertura de los horarios
    public void ventanaHorarios(ActionEvent event) {
        Button BtnSeleccion = (Button) event.getSource();
        if (contenedorHorarios.isVisible() && BtnSeleccionado == BtnSeleccion) {
            cierreVentanaHorarios();
            return;
        }
        if (BtnSeleccionado != null && BtnSeleccionado != BtnSeleccion) {
            cierreVentanaHorarios();
        }
        BtnSeleccionado = BtnSeleccion;
        horaInicio.setText("");
        horaFin.setText("");
        contenedorHorarios.setVisible(true);
        int espacioId = obtenerIdEspacio(BtnSeleccionado.getText());
        actualizarColoresHorarios(espacioId);
        Bounds bounds = BtnSeleccionado.localToScreen(BtnSeleccionado.getBoundsInLocal());
        contenedorHorarios.setLayoutX(bounds.getMinX() - 140);
        contenedorHorarios.setLayoutY(bounds.getMinY() - 60);
        if (BtnSeleccionado == Mesa1 || BtnSeleccionado == Mesa2 || BtnSeleccionado == Mesa3 ||
                BtnSeleccionado == Mesa4 || BtnSeleccionado == Mesa7 || BtnSeleccionado == Mesa8 ||
                BtnSeleccionado == Mesa9 || BtnSeleccionado == Mesa10 || BtnSeleccionado == Mesa13 ||
                BtnSeleccionado == Mesa14 || BtnSeleccionado == Mesa15 || BtnSeleccionado == Mesa16) {
            contenedorHorarios.setLayoutX(bounds.getMinX() - 250);
            contenedorHorarios.setLayoutY(bounds.getMinY() - 200);
        } else if (BtnSeleccionado == Mesa5 || BtnSeleccionado == Mesa6 || BtnSeleccionado == Mesa11 ||
                BtnSeleccionado == Mesa12 || BtnSeleccionado == Mesa17 || BtnSeleccionado == Mesa18) {
            contenedorHorarios.setLayoutX(bounds.getMinX() - 250);
            contenedorHorarios.setLayoutY(bounds.getMinY() - 220);
        }
        if (BtnSeleccionado == BtnOficina1) {
            contenedorHorarios.setLayoutX(bounds.getMinX() - 300);
            contenedorHorarios.setLayoutY(bounds.getMinY() - 300);
        } else if (BtnSeleccionado == BtnOficina2) {
            contenedorHorarios.setLayoutX(bounds.getMinX() - 300);
            contenedorHorarios.setLayoutY(bounds.getMinY() - 300);
        }

    }

    public void cierreVentanaHorarios() {
        contenedorHorarios.setVisible(false);
        BtnSeleccionado = null;
    }

    //Expone los datos del espacio mas el horario
    public void datosTextFieldMesa(ActionEvent event) {
        Button BtnSeleccionHora = (Button) event.getSource();
        if (BtnSeleccionHora != null) {
            espacio.setText(BtnSeleccionado.getText());
            if(horaInicio.getText().isEmpty()){
                horaInicio.setText(BtnSeleccionHora.getText());
            }else if (horaFin.getText().isEmpty()) {
                horaFin.setText(BtnSeleccionHora.getText());
                cierreVentanaHorarios();
            }

        }
    }
    public int obtenerIdEspacio(String nombreEspacio) {
        try {
            ConectionDB.openConn();
            String consulta = "SELECT id_asiento FROM Asientos WHERE nombre = ?";
            int idEspacio = -1;
            try (PreparedStatement stmt = conectionDB.getConn().prepareStatement(consulta)) {
                stmt.setString(1, nombreEspacio);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    idEspacio = rs.getInt("id_asiento");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return idEspacio;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            conectionDB.closeConn();
        }

    }

    public Set<String> obtenerHorariosReservados(int asiento) {
        try {
            ConectionDB.openConn();
            Set<String> horariosOcupados = new HashSet<>();
            String consulta = "SELECT TIME(fecha_hora_inicio) FROM Reservas WHERE id_asiento = ?";

            try (PreparedStatement stmt = conectionDB.getConn().prepareStatement(consulta)) {
                stmt.setInt(1, asiento);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    horariosOcupados.add(rs.getString(1));
                }
            } catch (SQLException e) {
            }

            return horariosOcupados;

        } catch (ClassNotFoundException e) {

            throw new RuntimeException(e);
        }finally {
            conectionDB.closeConn();
        }

    }
    public Set<String> obtenerFechasReservadas(int idEspacio) {
        try {
            ConectionDB.openConn();
            Set<String> fechasOcupadas = new HashSet<>();
            String consulta = "SELECT DATE(fecha_hora_inicio) FROM Reservas WHERE id_asiento = ?";

            try (PreparedStatement stmt = conectionDB.getConn().prepareStatement(consulta)) {
                stmt.setInt(1, idEspacio);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    fechasOcupadas.add(rs.getString(1)); // Obtener la fecha como String
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return fechasOcupadas;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            conectionDB.closeConn();
        }
    }


    public void actualizarColoresHorarios(int idEspacio) {

        Set<String> horariosOcupados = obtenerHorariosReservados(idEspacio);
        Set<String> fechasOcupadas = obtenerFechasReservadas(idEspacio);

        LocalDate fechaSeleccionada = fecha.getValue();
        if (fechaSeleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una fecha.");
            alert.showAndWait();
            return;
        }

        String fechaString = fechaSeleccionada.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (javafx.scene.Node node : contenedorHorarios.lookupAll(".button")) {
            if (node instanceof Button) {
                Button btnHorario = (Button) node;
                String horario = btnHorario.getText();

                for(String horaReserva : horariosOcupados){
                    String horaM = horaReserva.substring(0,5);
                    if (horaM.equals(horario) && fechasOcupadas.contains(fechaString)) {
                        btnHorario.setStyle("-fx-background-color: #e60415; -fx-text-fill: white;");
                        btnHorario.setDisable(true);
                    } else {
                        btnHorario.setStyle("-fx-background-color: #047f07; -fx-text-fill: black;");
                        btnHorario.setDisable(false);
                    }
                }

            }
        }
    }



    public void RegistroUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/RegistroClientes.fxml"));
            Parent cargaVentana = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(cargaVentana));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void ListaReservas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/ListaReservas.fxml"));
            Parent cargaVentana = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(cargaVentana));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ListaUsuarios() {
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

    public void Facturaciones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/Facturacion.fxml"));
            Parent cargaVentana = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(cargaVentana));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
