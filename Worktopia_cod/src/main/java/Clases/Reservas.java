package Clases;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Reservas {
    private int id_reserva;
    private Timestamp fecha_hora_inicio;
    private Timestamp fecha_hora_fin;
    private int id_cliente;
    private int id_asiento;

    public Reservas(int id_reserva, int id_cliente, int id_asiento, Timestamp fecha_hora_inicio, Timestamp fecha_hora_fin) {
        this.id_reserva = id_reserva;
        this.id_cliente = id_cliente;
        this.id_asiento = id_asiento;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
    }

    public Timestamp getFecha_hora_inicio() {
        return fecha_hora_inicio;
    }

    public void setFecha_hora_inicio(Timestamp fecha_hora_inicio) {
        this.fecha_hora_inicio = fecha_hora_inicio;
    }

    public Timestamp getFecha_hora_fin() {
        return fecha_hora_fin;
    }

    public void setFecha_hora_fin(Timestamp fecha_hora_fin) {
        this.fecha_hora_fin = fecha_hora_fin;
    }

    @Override
    public String toString() {
        return "Reservas{" +
                "id_reserva=" + id_reserva +
                ", id_cliente=" + id_cliente +
                ", id_asiento=" + id_asiento +
                ", fecha_hora_inicio=" + fecha_hora_inicio +
                ", fecha_hora_fin=" + fecha_hora_fin +
                '}';
    }
}
