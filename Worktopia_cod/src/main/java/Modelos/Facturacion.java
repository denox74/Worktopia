package Modelos;

import Clases.Facturas;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ConexionDB.ConectionDB;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.io.IOException;

public class Facturacion {

    private static ReservasPanel ReservasP;
    @FXML
    private Button AgregarClientes;
    @FXML
    private Button Reservas;
    @FXML
    private Button ListaReservas;
    @FXML
    private Button ListaClientes;
    @FXML
    private Button btnGenerar;
    @FXML
    private TextField facturaBuscar;
    @FXML
    private VBox contenedorDatos;
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
    private void generarFactura() {
        String idFactura = facturaBuscar.getText();
        if (idFactura != null && !idFactura.trim().isEmpty()) {
            GeneradorFactura.generarFactura(idFactura);
        } else {
            System.out.println("Ingrese un ID de factura válido.");
        }
    }

    @FXML
    public void initialize() {
        colNFactura.setCellValueFactory(new PropertyValueFactory<>("id_factura"));
        colFechaFactura.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_emision"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precio_total"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colDescuento.setCellValueFactory(new PropertyValueFactory<>("descuento"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFechaPago.setCellValueFactory(new PropertyValueFactory<>("fecha_hora_pago"));


        ObservableList<Facturas> facturasList = FXCollections.observableArrayList(ConectionDB.getFacturas());
        tablaFacturas.setItems(facturasList);
    }

    public Facturacion() {
        this.ReservasP = new ReservasPanel();
    }


    public void ventanaRegistro(ActionEvent event) {
        RegistroUsuarios();
        ((Stage) AgregarClientes.getScene().getWindow()).close();
    }

    public void ventanaReservas(ActionEvent event) {
        Reservas();
        ((Stage) Reservas.getScene().getWindow()).close();
    }

    public void ventanaListaClientes(ActionEvent event) {
        ListaUsuarios();
        ((Stage) ListaClientes.getScene().getWindow()).close();
    }

    public void ventanaListaReservas(ActionEvent event) {
        ListaReservas();
        ((Stage) ListaReservas.getScene().getWindow()).close();
    }

    public void btnDescarga(ActionEvent event) {
        contenedorDatos.setVisible(true);
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

    public void Reservas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menus/Reservas.fxml"));
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


    public class GeneradorFactura {
        public static void generarFactura(String idFactura) {
            Connection conn = null;
            try {
                conn = ConectionDB.getConn();
                if (conn == null) {
                    System.out.println("No hay conexión a la base de datos");
                    return;
                }

                String query = "SELECT * FROM Factura WHERE id_factura = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, idFactura);
                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("No se encontró la factura con ID: " + idFactura);
                    return;
                }

                String fecha = rs.getString("fecha");
                double total = rs.getDouble("total");
                int idCliente = rs.getInt("id_cliente");

                String queryCliente = "SELECT nombre, primerApellido, segundoApellido, email FROM Clientes WHERE id_cliente = ?";
                pstmt = conn.prepareStatement(queryCliente);
                pstmt.setInt(1, idCliente);
                ResultSet rsCliente = pstmt.executeQuery();

                String nombre = "";
                String apellido1 = "";
                String apellido2 = "";
                String email = "";

                if (rsCliente.next()) {
                    nombre = rsCliente.getString("nombre");
                    apellido1 = rsCliente.getString("primerApellido");
                    apellido2 = rsCliente.getString("segundoApellido");
                    email = rsCliente.getString("email");
                }

                File plantilla = new File("Modelo_factura.docx");
                FileInputStream fis = new FileInputStream(plantilla);
                XWPFDocument documento = new XWPFDocument(fis);

                for (XWPFParagraph paragraph : documento.getParagraphs()) {
                    for (XWPFRun run : paragraph.getRuns()) {
                        String text = run.getText(0);
                        if (text != null) {
                            text = text.replace("{{ID_FACTURA}}", idFactura)
                                    .replace("{{FECHA}}", fecha)
                                    .replace("{{TOTAL}}", String.valueOf(total))
                                    .replace("{{NOMBRE}}", nombre + " " + apellido1 + " " + apellido2)
                                    .replace("{{EMAIL}}", email);
                            run.setText(text, 0);
                        }
                    }
                }

                fis.close();
                File facturaGenerada = new File("Factura_" + idFactura + ".docx");
                FileOutputStream fos = new FileOutputStream(facturaGenerada);
                documento.write(fos);
                fos.close();
                documento.close();

                System.out.println("Factura generada: " + facturaGenerada.getAbsolutePath());

                // Abrir la factura generada automáticamente
                Desktop.getDesktop().open(facturaGenerada);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
