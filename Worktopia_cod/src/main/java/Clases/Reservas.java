package Clases;

public class Reservas {
    private static int ID_reserva = 0;
    private String fecha_inicio_reserva;
    private String fecha_fin_reserva;
    private String hora_reserva;
    private Integer id_cliente;
    private Integer id_asiento;
    private Integer id_espacio;
    private Integer id_factura;
    private String estado_reserva;

    public Reservas(String fecha_inicio_reserva, String fecha_fin_reserva, String hora_reserva, Integer id_cliente, Integer id_asiento, Integer id_factura, String estado_reserva) {
        this.ID_reserva = ID_reserva;
        this.fecha_inicio_reserva = fecha_inicio_reserva;
        this.fecha_fin_reserva = fecha_fin_reserva;
        this.hora_reserva = hora_reserva;
        this.id_cliente = id_cliente;
        this.id_asiento = id_asiento;
        this.id_factura = id_factura;
        this.estado_reserva = estado_reserva;
        ++ID_reserva;
    }


}
