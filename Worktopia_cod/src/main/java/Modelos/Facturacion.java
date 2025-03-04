package Modelos;

import Aplicaciones.MenuPrincipalApp;
import Clases.Asientos;
import Clases.Facturas;
import Clases.SesionUsuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ConexionDB.ConectionDB;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Facturacion {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private TextField TextDniCliente;
    @FXML
    private TextField TextSubtotal;
    @FXML
    private TextField TextDescuento;
    @FXML
    private TextField TextTotal;
    @FXML
    private Button BtnGenerarFactura;
    @FXML
    private Button BtnPagarFactura;
    @FXML
    private TextField TextFechaFactura;
    @FXML
    private Button Reservas;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button BtnUsuarios;
    @FXML
    private TextField facturaBuscar;
    @FXML
    private VBox vboxFacturas;
    @FXML
    private ComboBox<String> comboFormaPago;

    ObservableList<String> formasPago = FXCollections.observableArrayList("Tarjeta", "Efectivo");

    private int idFactura;
    @FXML
    private TableView<Facturas> tablaFacturas;
    @FXML
    private TableColumn<Facturas, String> colNFactura;
    @FXML
    private TableColumn<Facturas, String> colFechaFactura;
    @FXML
    private TableColumn<Facturas, String> colTotal;
    @FXML
    private TableColumn<Facturas, String> colDni;
    @FXML
    private TableColumn<Facturas, String> colDescuento;
    @FXML
    private TableColumn<Facturas, String> colEstadoPago;
    @FXML
    private TableColumn<Facturas, String> colFechaPago;
    @FXML
    private TableColumn<Facturas, String> colFormaPago;
    @FXML
    private TableColumn<Facturas, String> colSubtotal;





    @FXML
    public void generarFactura(ActionEvent event) {
        String facturaId = facturaBuscar.getText();
        if (facturaId.isEmpty()) {
            showAlert("Error", "Debe ingresar un número de factura.");
            return;
        }

        try {
            generarFacturaExcel(facturaId);
            showAlert("Éxito", "Factura generada correctamente.");
        } catch (Exception e) {
            showAlert("Error", "Error al generar la factura: " + e.getMessage());
        }
    }


    @FXML
    public void initialize() {
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/bannerTopiaC.png")));
        colNFactura.setCellValueFactory(new PropertyValueFactory<>("id_factura"));
        colFechaFactura.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_emision"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precio_total"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colDescuento.setCellValueFactory(new PropertyValueFactory<>("descuento"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFechaPago.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_pago"));
        colFormaPago.setCellValueFactory(new PropertyValueFactory<>("forma_pago"));


        ObservableList<Facturas> facturasList = FXCollections.observableArrayList(ConectionDB.getFacturas());
        tablaFacturas.setItems(facturasList);
        inicioSesion();
        
        tablaFacturas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Facturas factura = tablaFacturas.getSelectionModel().getSelectedItem();
                if (factura != null) {
                    exportarFacturas(factura);
                }
            }
        });
        TextDescuento.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                actualizarTotal();
            }
        });

        vboxFacturas.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - vboxFacturas.getLayoutX() + 250;
            yOffset = event.getSceneY() - vboxFacturas.getLayoutY() + 70;
        });
        vboxFacturas.setOnMouseDragged(event -> {
            vboxFacturas.setLayoutX(event.getScreenX() - xOffset);
            vboxFacturas.setLayoutY(event.getScreenY() - yOffset);
        });


    }

    public Facturacion() {
    
    }
    public static void rellenarCombo(ComboBox<String> comboBox, ObservableList<String> forma) {
        comboBox.setItems(forma);
    }

    public void llenarComboBox(ActionEvent event) {
        rellenarCombo(comboFormaPago, formasPago);
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

    
    public void exportarFacturas(Facturas facturas) {
        idFactura = facturas.getId_factura();
        TextFechaFactura.setText(facturas.getFecha_hora_emision());
        TextSubtotal.setText(facturas.getPrecio_total().toString());
        TextDniCliente.setText(facturas.getDni());
        TextDescuento.setText(facturas.getDescuento().toString());
        comboFormaPago.getSelectionModel().select(facturas.getForma_pago());
        double descuento = Double.parseDouble(TextDescuento.getText());
        double subTotal = Double.parseDouble(TextSubtotal.getText());
        TextTotal.setText(String.valueOf(descuento(descuento,subTotal)));
        vboxFacturas.setVisible(true);

    }
    public double descuento(double descuentoText, double subtotal) {
        if (descuentoText > 0) {
            return subtotal - (subtotal * descuentoText / 100);
        }
        return subtotal;
    }

    private void actualizarTotal() {
        try {
            double descuentoValor = validarNumero(TextDescuento.getText(), 0.00);
            double subtotal = validarNumero(TextSubtotal.getText(), 0.00);
            double total = descuento(descuentoValor, subtotal);

            TextTotal.setText(String.valueOf(total));
        } catch (Exception e) {
            TextTotal.setText("Error");
        }
    }

    private double validarNumero(String texto, double valorPorDefecto) {
        if (texto == null || texto.trim().isEmpty()) {
            return valorPorDefecto;
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            return valorPorDefecto;
        }
    }

    public void modificarFactura(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Quiere modificar la factura? :" + idFactura);

        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String queryUrl = "UPDATE Facturas SET fecha_hora_emision = ?,descuento = ?, precio_total = ? , subtotal = ? WHERE id_factura = ?";
            try {
                PreparedStatement stmt = ConectionDB.getConn().prepareStatement(queryUrl);
                stmt.setString(1, TextFechaFactura.getText());
                stmt.setString(2, TextDescuento.getText());
                stmt.setString(3, TextTotal.getText());
                stmt.setString(4,TextSubtotal.getText());
                stmt.setInt(5, idFactura);
                int confirmacion = stmt.executeUpdate();
                if (confirmacion == 1) {
                    showAlert("Modificar Factura","La factura se ha modificado Correctamente");
                }else {
                    showAlert("Modificar Factura","No se ha modificado la factura");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize();
        }
    }
    public void eliminarFactura(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Quiere eliminar la factura? :" + idFactura);

        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String queryUrl = "DELETE FROM Facturas WHERE id_factura = ?";
            try {
                PreparedStatement stmt = ConectionDB.getConn().prepareStatement(queryUrl);
                stmt.setInt(1, idFactura);
                int confirmacion = stmt.executeUpdate();
                if (confirmacion == 1) {
                    showAlert("Eliminar Factura","La factura se ha eliminado Correctamente");
                }else {
                    showAlert("Eliminar Factura","No se ha elimado la factura");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize();
        }
    }
    public void pagarFactura(ActionEvent event) {
        String estadoPago= "Pagada";
        LocalDateTime fecha = LocalDateTime.now();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Quiere abonar la factura? :" + idFactura);

        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(si, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == si) {
            String queryUrl = "UPDATE Facturas SET fecha_hora_emision = ?,descuento = ?, precio_total = ?, fecha_hora_pago = ? ,estado = ? , subtotal = ? , forma_pago = ? WHERE id_factura = ?";
            try {
                PreparedStatement stmt = ConectionDB.getConn().prepareStatement(queryUrl);
                stmt.setString(1, TextFechaFactura.getText());
                stmt.setString(2, TextDescuento.getText());
                stmt.setString(3, TextSubtotal.getText());
                stmt.setString(4,fecha.toString());
                stmt.setString(5,estadoPago);
                stmt.setString(6, TextTotal.getText());
                stmt.setString(7,comboFormaPago.getSelectionModel().getSelectedItem().toString());
                stmt.setInt(8, idFactura);
                int confirmacion = stmt.executeUpdate();
                if (confirmacion == 1) {
                    showAlert("Pagar Factura","La factura se ha Pagado Correctamente");
                }else {
                    showAlert("Pagar Factura","No se ha Pagado la factura");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize();
        }
    }
    
    
    private void generarFacturaExcel(String facturaId) throws SQLException, IOException, ClassNotFoundException {
        String queryFactura = "SELECT * FROM Facturas WHERE id_factura = ?";
        String queryCliente = "SELECT * FROM Clientes WHERE dni = ?";
        String queryReservas = "SELECT * FROM Reservas WHERE id_factura = ?";

        try (Connection conn = ConectionDB.getConn();
             PreparedStatement psFactura = conn.prepareStatement(queryFactura);
             PreparedStatement psCliente = conn.prepareStatement(queryCliente);
             PreparedStatement psReservas = conn.prepareStatement(queryReservas)) {

            psFactura.setString(1, facturaId);
            ResultSet rsFactura = psFactura.executeQuery();
            if (!rsFactura.next()) {
                throw new SQLException("Factura no encontrada.");
            }

            String dniCliente = rsFactura.getString("dni");
            LocalDateTime fechaEmision = rsFactura.getTimestamp("fecha_hora_emision").toLocalDateTime();
            BigDecimal totalFactura = rsFactura.getBigDecimal("precio_total");
            BigDecimal descuento = rsFactura.getBigDecimal("descuento");

            psCliente.setString(1, dniCliente);
            ResultSet rsCliente = psCliente.executeQuery();
            if (!rsCliente.next()) {
                throw new SQLException("Cliente no encontrado.");
            }

            String nombreCliente = rsCliente.getString("nombre");
            String telefonoCliente = rsCliente.getString("telefono");
            String emailCliente = rsCliente.getString("email");

            psReservas.setString(1, facturaId);
            ResultSet rsReservas = psReservas.executeQuery();
            List<String> reservas = new ArrayList<>();
            List<BigDecimal> subtotales = new ArrayList<>();
            BigDecimal totalSubtotales = BigDecimal.ZERO;
            while (rsReservas.next()) {
                int idReserva = rsReservas.getInt("id_reserva");
                int idAsiento = rsReservas.getInt("id_asiento");
                Timestamp inicio = rsReservas.getTimestamp("fecha_hora_inicio");
                Timestamp fin = rsReservas.getTimestamp("fecha_hora_fin");

                // Calcula el subtotal
                BigDecimal subtotal = calcularSubtotal(idAsiento, inicio, fin);
                totalSubtotales = totalSubtotales.add(subtotal);

                String reserva = String.format("Reserva ID: %d Asiento ID: %d fecha y hora de inicio %s y fin %s",
                        idReserva, idAsiento, inicio.toLocalDateTime(), fin.toLocalDateTime());
                reservas.add(reserva);
                subtotales.add(subtotal);
            }

            try (FileInputStream fis = new FileInputStream("src/main/resources/Docs/Modelo_factura_excel.xlsx");
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);

                // Rellenar los datos básicos
                setCellValue(sheet, 9, 1, facturaId);
                setCellValue(sheet, 15, 1, dniCliente);
                setCellValue(sheet, 16, 1, fechaEmision.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                setCellValue(sheet, 15, 5, nombreCliente);
                setCellValue(sheet, 16, 5, telefonoCliente);
                setCellValue(sheet, 14, 5, emailCliente);

                // Rellenar las reservas y subtotales
                int rowIndex = 17;
                for (int i = 0; i < reservas.size(); i++) {
                    Row row = sheet.getRow(rowIndex); // Obtener la fila existente (si ya existe)
                    if (row == null) {  // Si no existe, crearla
                        row = sheet.createRow(rowIndex);
                    }

                    // Aquí se asegura que las celdas no se sobrescriban y no se cambie el estilo
                    Cell cell0 = row.getCell(0);
                    if (cell0 == null) {
                        cell0 = row.createCell(0);
                    }
                    cell0.setCellValue(reservas.get(i));

                    Cell cell8 = row.getCell(8);
                    if (cell8 == null) {
                        cell8 = row.createCell(8);
                    }
                    cell8.setCellValue(subtotales.get(i).doubleValue());

                    // Avanzar a la siguiente fila
                    rowIndex++;
                }

                // Calcular el total con descuento
                BigDecimal totalConDescuento = totalSubtotales.subtract(totalSubtotales.multiply(descuento.divide(BigDecimal.valueOf(100))));
                BigDecimal baseImponible = totalConDescuento.divide(BigDecimal.valueOf(1.07), BigDecimal.ROUND_HALF_UP);
                BigDecimal impuestos = baseImponible.multiply(BigDecimal.valueOf(0.07));

                // Rellenar el porcentaje de descuento en la celda 45,7 (ya estaba en el modelo)
                setCellValue(sheet, 45, 6, descuento.doubleValue()/100);

                // Rellenar el valor del descuento (dinero) en la celda 45,6
                BigDecimal valorDescuento = totalSubtotales.multiply(descuento.divide(BigDecimal.valueOf(100)));
                setCellValue(sheet, 45, 7, valorDescuento.doubleValue());

                // Rellenar el total con descuento en la celda 46,7
                setCellValue(sheet, 46, 7, totalConDescuento.doubleValue());

                // Rellenar base imponible, impuestos y total
                setCellValue(sheet, 43, 7, baseImponible.doubleValue());
                setCellValue(sheet, 44, 7, impuestos.doubleValue());
                setCellValue(sheet, 47, 7, fechaEmision.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                // Guardar el archivo Excel con el nuevo nombre
                try (FileOutputStream fos = new FileOutputStream("src/main/resources/Docs/Factura_" + facturaId + ".xlsx")) {
                    workbook.write(fos);
                }
            }
        }
        ConectionDB.openConn();
    }





/*
    private void setCellValue(Sheet sheet, int rowNum, int colNum, Object value, CellStyle cellStyle) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.createCell(colNum);

        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        }

        // Asignar el estilo a la celda
        cell.setCellStyle(cellStyle);
    }
*/
    public BigDecimal calcularSubtotal(int idAsiento, Timestamp fechaInicio, Timestamp fechaFin) {
        BigDecimal tarifaHora = getTarifaHoraId(idAsiento);
        LocalDateTime inicio = fechaInicio.toLocalDateTime();
        LocalDateTime fin = fechaFin.toLocalDateTime();
        long horas = Duration.between(inicio, fin).toHours();
        return tarifaHora.multiply(BigDecimal.valueOf(horas));
    }

    private static Map<Integer, BigDecimal> tarifaHoraCache = new HashMap<>();

    private BigDecimal getTarifaHoraId(int id_asiento) {
        if (!tarifaHoraCache.containsKey(id_asiento)) {
            List<Asientos> asientos = ConectionDB.getAsientos();
            for (Asientos asiento : asientos) {
                if (asiento.getId_asiento() == id_asiento) {
                    tarifaHoraCache.put(id_asiento, asiento.getTarifa_hora());
                    return asiento.getTarifa_hora();
                }
            }
        }
        return tarifaHoraCache.getOrDefault(id_asiento, BigDecimal.ZERO);
    }

    private void setCellValue(Sheet sheet, int rowIndex, int colIndex, Object value) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        }
    }


        private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

    public void ventanaListaClientes(ActionEvent event) {
        abrirVentana("/Menus/ListaClientes.fxml", "Lista de Cliente");
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaListaReservas(ActionEvent event) {
        abrirVentana("/Menus/ListaReservas.fxml", "Listas De Reservas");
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }
    public void ventanaUsuarios(ActionEvent event) {
        abrirVentana("/Menus/ListaUsuarios.fxml", "Usuarios");
        ((Stage) BtnUsuarios.getScene().getWindow()).close();
    }
    public void salirVbox(ActionEvent event) {
        vboxFacturas.setVisible(false);
    }
}







