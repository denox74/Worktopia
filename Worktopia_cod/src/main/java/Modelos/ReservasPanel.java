package Modelos;


import Clases.Reservas;
import Controlador.ControladorEmail;
import Controlador.ControladorReservas;
import Manejadores_Reservas_Facturas.ReservaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.Set;

public class ReservasPanel {
    private ControladorReservas controladorReservas = new ControladorReservas();
    private ControladorEmail controladorEmail = new ControladorEmail();
    private ListaClientes listaClientes = new ListaClientes();
    @FXML
    private Button btnSalir;
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
    private TextField dniCliente;

    private Button BtnSeleccionado;
    private double precioTotal;
    private BigDecimal subtotal;

    @FXML
    public void initialize() {
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/bannerTopiaC.png")));
        dniCliente.textProperty().addListener((observableValue, s, t1) -> {
            controladorReservas.buscarDni(dniCliente.getText(), dniCliente);
        });
        dniCliente.textProperty().bindBidirectional(listaClientes.getSelectSeleccionDni());

    }

    public ReservasPanel() {
    }

    public void insertarReserva(ActionEvent event) {

        String emailCliente = controladorReservas.obtenerEmailCliente(dniCliente.getText());
        String nombreCliente = controladorReservas.obtenerNombreCliente(dniCliente.getText());
        //Formateo de la fecha a fecha europea
        LocalDate fechas = fecha.getValue();
        DateTimeFormatter formateo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechas.format(formateo);

        if (emailCliente != null && !emailCliente.isEmpty()) {
            String htmlPlantilla = "src/main/resources/html/reservasHTML.html";
            String mensaje = controladorEmail.cargarPlantilla(
                    htmlPlantilla,
                    nombreCliente,
                    fechaFormateada,
                    horaInicio.getText().toString(),
                    horaFin.getText().toString(),
                    espacio.getText().toString(),
                    subtotal.toString()
            );
            controladorEmail.enviarCorreo(emailCliente, "Confirmación de reserva", mensaje);
        }
        System.out.println("idreserva" + fecha);

        insertarDatos();

    }


    //Expone los datos del espacio mas el horario
    public void datosTextFieldMesa(ActionEvent event) {
        Button BtnSeleccionHora = (Button) event.getSource();

        if (BtnSeleccionHora != null) {
            espacio.setText(BtnSeleccionado.getText());
            if (horaInicio.getText().isEmpty()) {
                horaInicio.setText(BtnSeleccionHora.getText());
            } else if (horaFin.getText().isEmpty()) {
                horaFin.setText(BtnSeleccionHora.getText());
                cierreVentanaHorarios();
            }
        }
        precioTotal = controladorReservas.facturaPrecioText(espacio, horaInicio, horaFin);
        precio.setText(String.format("%.2f €", precioTotal));
        subtotal = BigDecimal.valueOf(precioTotal);


    }

    // Apertura del vbox con el puntero del raton

    public void ventanaHorarios(MouseEvent event) {
        Button BtnSeleccion = (Button) event.getSource();

        // Si ya está abierta y es el mismo botón, cerrarla
        if (contenedorHorarios.isVisible() && BtnSeleccionado == BtnSeleccion) {
            cierreVentanaHorarios();
            return;
        }

        // Cerrar la anterior si se selecciona otra
        if (BtnSeleccionado != null && BtnSeleccionado != BtnSeleccion) {
            cierreVentanaHorarios();
        }

        BtnSeleccionado = BtnSeleccion;
        limpiarCamposHorarios();
        contenedorHorarios.setVisible(true);

        int espacioId = controladorReservas.obtenerIdEspacio(BtnSeleccionado.getText());
        actualizarColoresHorarios(espacioId);

        // Posicionar la ventana en la ubicación del ratón
        posicionarVentanaEnRaton(event);
    }

    private void limpiarCamposHorarios() {
        horaInicio.setText("");
        horaFin.setText("");
        precio.setText("");
    }

    private void posicionarVentanaEnRaton(MouseEvent event) {
        // Obtener coordenadas del puntero del ratón
        double x = event.getScreenX() - 250;
        double y = event.getScreenY() - 200;

        //Ajuste opcional para evitar que aparezca fuera de pantalla
        double anchoVentana = contenedorHorarios.getWidth();
        double altoVentana = contenedorHorarios.getHeight();

        if (x + anchoVentana > contenedorHorarios.getScene().getWindow().getWidth()) {
            x -= anchoVentana; // Si la ventana se sale, muévela a la izquierda
        }
        if (y + altoVentana > contenedorHorarios.getScene().getWindow().getHeight()) {
            y -= altoVentana; // Si se sale por abajo, sube la ventana
        }

        // Posicionar el contenedor en la posición del ratón
        contenedorHorarios.setLayoutX(x);
        contenedorHorarios.setLayoutY(y);
    }

    public void cierreVentanaHorarios() {
        contenedorHorarios.setVisible(false);
        BtnSeleccionado = null;
    }


    public void actualizarColoresHorarios(int idEspacio) {
        LocalDate fechaSeleccionada = fecha.getValue();
        if (fechaSeleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una fecha.");
            alert.showAndWait();
            contenedorHorarios.setVisible(false);
            return;
        }

        Set<String> horariosOcupados = controladorReservas.obtenerHorariosReservados(idEspacio, fechaSeleccionada);

        for (javafx.scene.Node node : contenedorHorarios.lookupAll(".button")) {
            if (node instanceof Button) {
                Button btnHorario = (Button) node;
                String horario = btnHorario.getText();

                if (horariosOcupados.contains(horario)) {
                    // La hora está dentro del rango de una reserva
                    btnHorario.setStyle("-fx-background-color: #e60415; -fx-text-fill: white;");
                    btnHorario.setDisable(true);
                } else {
                    // La fecha está reservada, pero no esta hora específica
                    btnHorario.setStyle("-fx-background-color: #047f07; -fx-text-fill: black;");
                    btnHorario.setDisable(false);
                }
            }
        }
    }


    public void insertarDatos() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String dniText = dniCliente.getText();
        String espacioText = espacio.getText();
        String horaInicioText = horaInicio.getText();
        String horaFinText = horaFin.getText();
        LocalDate fechaReserva = fecha.getValue();
        BigDecimal subTotal = subtotal;
        int idAsiento = controladorReservas.obtenerIdEspacio(espacioText);

        if (dniText.isEmpty() || espacioText.isEmpty() || horaInicioText.isEmpty() || horaFinText.isEmpty()) {
            alert.setContentText("Debe completar todos los campos.");
            alert.show();
            return;
        }

        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaReserva, LocalTime.parse(horaInicioText));
        LocalDateTime fechaHoraFin = LocalDateTime.of(fechaReserva, LocalTime.parse(horaFinText));

        try {
            int idFactura = ReservaDAO.obtenerFacturaPendiente(dniText);
            if (idFactura == -1) {
                idFactura = ReservaDAO.crearFactura(dniText, subTotal);
            }

            Reservas reserva = new Reservas(0, dniText, idAsiento, idFactura, Timestamp.valueOf(fechaHoraInicio), Timestamp.valueOf(fechaHoraFin), subTotal);
            int idReserva = ReservaDAO.insertarReserva(reserva);

            alert.setContentText("Reserva y factura creadas correctamente. ID Reserva: " + idReserva);
            alert.show();

        } catch (SQLException e) {
            alert.setContentText("Error en la base de datos: " + e.getMessage());
            alert.show();
        }
    }


    public void salir(ActionEvent event) {
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

}
