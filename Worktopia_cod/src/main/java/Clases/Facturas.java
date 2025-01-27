package Clases;

public class Facturas {
    private Integer numero_factura;
    private String fecha_factura;
    private Double total_factura;
    private String estado_factura;
    private String fecha_pago;
    private Integer id_cliente;

    public Facturas(Integer numero_factura, String fecha_factura, Double total_factura, String estado_factura, String fecha_pago, Integer id_cliente) {
        this.numero_factura = numero_factura;
        this.fecha_factura = fecha_factura;
        this.total_factura = total_factura;
        this.estado_factura = estado_factura;
        this.fecha_pago = fecha_pago;
        this.id_cliente = id_cliente;
    }




}
