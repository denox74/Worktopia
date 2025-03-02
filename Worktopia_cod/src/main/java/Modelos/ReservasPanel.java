package Modelos;


import Aplicaciones.MenuPrincipalApp;
import Clases.Reservas;
import Clases.SesionUsuario;
import ConexionDB.ConectionDB;
import Manejadores_Reservas_Facturas.ReservaDAO;
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
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class ReservasPanel {

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
    private Button BtnUsuarios;
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
    private TextField dniCliente;
    @FXML
    private TextField TextSubtotal;

    private Button BtnSeleccionado;
    private BigDecimal subtotales;
    private double precioTotal;

    @FXML
    public void initialize(){
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/bannerTopiaC.png")));
        inicioSesion();
    }

    public ReservasPanel() {

    }

    public void insertarReserva(ActionEvent event) {
        insertarDatos();
        fecha.setValue(null);
        horaInicio.setText("");
        horaFin.setText("");
        espacio.setText("");
        dniCliente.setText("");
    }
    public void inicioSesion() {
        String categoria = SesionUsuario.getCategoriaUsuario();

        if (categoria != null && categoria.equals("Admin")) {
            BtnUsuarios.setVisible(true);
            BtnUsuarios.setDisable(false);
        } else {
            BtnUsuarios.setVisible(false);
        }
    }

    public double facturaPrecioText(TextField nombreEspacio, TextField horaInicio, TextField horaFin) {
        String consulta = "SELECT tarifa_hora FROM Asientos WHERE nombre = ?";

        String inicioTexto = horaInicio.getText();
        String finTexto = horaFin.getText();

        double tarifa = 0;
        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(consulta)) {
            stmt.setString(1, nombreEspacio.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tarifa = rs.getInt("tarifa_hora");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        try {
            LocalTime inicio = LocalTime.parse(inicioTexto);
            LocalTime fin = LocalTime.parse(finTexto);

            double horasPasadas = (double) ChronoUnit.HOURS.between(inicio, fin);
            return horasPasadas * tarifa;

        } catch (Exception e) {
            return 0;
        }
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
        precio.setText("");
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
            if (horaInicio.getText().isEmpty()) {
                horaInicio.setText(BtnSeleccionHora.getText());
            } else if (horaFin.getText().isEmpty()) {
                horaFin.setText(BtnSeleccionHora.getText());
                cierreVentanaHorarios();
            }
        }
        precioTotal = facturaPrecioText(espacio, horaInicio, horaFin);
        precio.setText(String.format("%.2f €", precioTotal));


        subtotales = BigDecimal.valueOf(precioTotal);
        TextSubtotal.setText(String.format("%.2f", subtotales));

    }

    public int obtenerIdEspacio(String nombreEspacio) {
        String consulta = "SELECT id_asiento FROM Asientos WHERE nombre = ?";
        int idEspacio = -1;
        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(consulta)) {
            stmt.setString(1, nombreEspacio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idEspacio = rs.getInt("id_asiento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idEspacio;
    }


    public Set<String> obtenerHorariosReservados(int asiento, LocalDate fecha) {
        Set<String> horariosOcupados = new HashSet<>();
        String consulta = "SELECT TIME(fecha_hora_inicio),TIME(fecha_hora_fin) FROM Reservas WHERE id_asiento = ? AND DATE(fecha_hora_inicio) = ?";

        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(consulta)) {
            stmt.setInt(1, asiento);
            stmt.setString(2, fecha.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalTime inicio = rs.getTime(1).toLocalTime();
                LocalTime fin = rs.getTime(2).toLocalTime();
                while (!inicio.isAfter(fin)) {
                    horariosOcupados.add(inicio.toString());
                    inicio = inicio.plusMinutes(30);
                }
            }
        } catch (SQLException e) {
        }
        return horariosOcupados;
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

        Set<String> horariosOcupados = obtenerHorariosReservados(idEspacio, fechaSeleccionada);

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

/*
    public int insertarIdFactura(String DNI, double nuevoPrecio) {
        String selectQuery = "SELECT id_factura, precio_total FROM Facturas WHERE dni = ?";
        String updateQuery = "UPDATE Facturas SET precio_total = ? WHERE id_factura = ?";
        String insertQuery = "INSERT INTO Facturas (dni, precio_total, descuento, fecha_hora_emision, estado) VALUES (?, ?, 0.00, NOW(), 'Pendiente')";

        int idFactura;
        double precioTotal;

        try (PreparedStatement stmt = ConectionDB.getConn().prepareStatement(selectQuery)) {
            stmt.setString(1, DNI);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // Si la factura existe, actualizamos el precio
                idFactura = rs.getInt("id_factura");
                precioTotal = rs.getDouble("precio_total");

                double nuevoTotal = precioTotal + nuevoPrecio; // Sumamos el nuevo precio

                try (PreparedStatement updateStmt = ConectionDB.getConn().prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, nuevoTotal);
                    updateStmt.setInt(2, idFactura);
                    updateStmt.executeUpdate();
                }
                return idFactura;

            } else { // Si la factura no existe, la creamos
                try (PreparedStatement insertStmt = ConectionDB.getConn().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setString(1, DNI);
                    insertStmt.setDouble(2, nuevoPrecio);
                    insertStmt.executeUpdate();

                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en la facturación: " + e.getMessage());
            throw new RuntimeException("Error al gestionar la factura.", e);
        }

        return -1;
    }*/



    public void insertarDatos() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String dniText = dniCliente.getText();
        String espacioText = espacio.getText();
        String horaInicioText = horaInicio.getText();
        String horaFinText = horaFin.getText();
        LocalDate fechaReserva = fecha.getValue();
        int idAsiento = obtenerIdEspacio(espacioText);

        if (dniText.isEmpty() || espacioText.isEmpty() || horaInicioText.isEmpty() || horaFinText.isEmpty()) {
            alert.setContentText("Debe completar todos los campos.");
            alert.show();
            return;
        }

        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaReserva, LocalTime.parse(horaInicioText));
        LocalDateTime fechaHoraFin = LocalDateTime.of(fechaReserva, LocalTime.parse(horaFinText));

        BigDecimal subTotal = new BigDecimal(TextSubtotal.getText().replace(",", "."));

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
    /*
        // Obtener ID de factura, pasándole el subtotal
        subTotal = new BigDecimal(TextSubtotal.getText().replace("," ,"."));


        int idFactura = insertarIdFactura(dniText, subTotal.doubleValue());

        if (idFactura == -1) {
            alert.setContentText("No se pudo generar la factura.");
            alert.show();
            return;
        }


        // Insertar la reserva
        String queryReserva = "INSERT INTO Reservas (dni, id_asiento, id_factura, fecha_hora_inicio, fecha_hora_fin, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmtReserva = ConectionDB.getConn().prepareStatement(queryReserva, Statement.RETURN_GENERATED_KEYS)) {
            stmtReserva.setString(1, dniText);
            stmtReserva.setInt(2, idAsiento);
            stmtReserva.setInt(3, idFactura);
            stmtReserva.setTimestamp(4, Timestamp.valueOf(fechaHoraInicio));
            stmtReserva.setTimestamp(5, Timestamp.valueOf(fechaHoraFin));
            stmtReserva.setBigDecimal(6, subTotal);

            int rowsInserted = stmtReserva.executeUpdate();
            if (rowsInserted == 0) {
                alert.setContentText("No se pudo crear la reserva.");
                alert.show();
            } else {
                alert.setContentText("Reserva y factura creadas correctamente.");
                alert.show();
            }

        } catch (SQLException e) {
            alert.setContentText("Error en la base de datos: " + e.getMessage());
            alert.show();
        }
    }*/


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

    public void ventanaListaClientes(ActionEvent event) {
        abrirVentana("/Menus/ListaClientes.fxml", "Lista de Cliente");
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaFacturaciones(ActionEvent event) {
        abrirVentana("/Menus/Facturacion.fxml", "Factuacion");
        ((Stage) Facturacion.getScene().getWindow()).close();
    }
    public void ventanaUsuarios(ActionEvent event) {
        abrirVentana("/Menus/ListaUsuarios.fxml", "Usuarios");
        ((Stage) BtnUsuarios.getScene().getWindow()).close();
    }
    public void ventanaListaReservas(ActionEvent event) {
        abrirVentana("/Menus/ListaReservas.fxml", "Lista de Reservas");
        ((Stage) BtnUsuarios.getScene().getWindow()).close();
    }


}
