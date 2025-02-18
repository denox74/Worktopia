package Clases;

public class FacturaReservas {
    private Integer id_factura;
    private Integer id_reserva;

    public FacturaReservas(Integer id_factura, Integer id_reserva) {
        this.id_factura = id_factura;
        this.id_reserva = id_reserva;
    }

    public Integer getId_factura() {
        return id_factura;
    }

    public void setId_factura(Integer id_factura) {
        this.id_factura = id_factura;
    }

    public Integer getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Integer id_reserva) {
        this.id_reserva = id_reserva;
    }
}
