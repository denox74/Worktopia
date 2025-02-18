package Clases;

import java.math.BigDecimal;

public class Asientos {

    private int id_asiento;
    private String estado;
    private String nombre;
    private BigDecimal tarifa_hora;
    private int id_espacio;

    public Asientos(int id_asiento, String estado, String nombre, BigDecimal tarifa_hora, int id_espacio) {
        this.id_asiento = id_asiento;
        this.estado = estado;
        this.nombre = nombre;
        this.tarifa_hora = tarifa_hora;
        this.id_espacio = id_espacio;
    }

    public int getId_asiento() {
        return id_asiento;
    }

    public void setId_asiento(int id_asiento) {
        this.id_asiento = id_asiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getTarifa_hora() {
        return tarifa_hora;
    }

    public void setTarifa_hora(BigDecimal tarifa_hora) {
        this.tarifa_hora = tarifa_hora;
    }

    public int getId_espacio() {
        return id_espacio;
    }

    public void setId_espacio(int id_espacio) {
        this.id_espacio = id_espacio;
    }

    @Override
    public String toString() {
        return "Asientos{" +
                "id_asiento=" + id_asiento +
                ", estado='" + estado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tarifa_hora=" + tarifa_hora +
                ", id_espacio=" + id_espacio +
                '}';
    }
}