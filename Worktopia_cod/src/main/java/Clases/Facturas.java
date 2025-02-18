package Clases;

import java.math.BigDecimal;

public class Facturas {
    private Integer id_factura;
    private BigDecimal precio_total;
    private Boolean tiene_descuento;
    private String fecha_hora_emision;
    private String estado;
    private String fecha_pago;

    public Facturas(Integer id_factura, BigDecimal precio_total, Boolean tiene_descuento, String fecha_hora_emision, String estado, String fecha_pago) {
        this.id_factura = id_factura;
        this.precio_total = precio_total;
        this.tiene_descuento = tiene_descuento;
        this.fecha_hora_emision = fecha_hora_emision;
        this.estado = estado;
        this.fecha_pago = fecha_pago;
    }

    public Integer getId_factura() {
        return id_factura;
    }

    public void setId_factura(Integer id_factura) {
        this.id_factura = id_factura;
    }

    public BigDecimal getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(BigDecimal precio_total) {
        this.precio_total = precio_total;
    }

    public Boolean getTiene_descuento() {
        return tiene_descuento;
    }

    public void setTiene_descuento(Boolean tiene_descuento) {
        this.tiene_descuento = tiene_descuento;
    }

    public String getFecha_hora_emision() {
        return fecha_hora_emision;
    }

    public void setFecha_hora_emision(String fecha_hora_emision) {
        this.fecha_hora_emision = fecha_hora_emision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    @Override
    public String toString() {
        return "Facturas{" +
                "id_factura=" + id_factura +
                ", precio_total=" + precio_total +
                ", tiene_descuento=" + tiene_descuento +
                ", fecha_hora_emision='" + fecha_hora_emision + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha_pago='" + fecha_pago + '\'' +
                '}';
    }
}
