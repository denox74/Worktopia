package Clases;

public class Asientos {
    private Integer numero_asiento;
    private String tipo_asiento;
    private String estado_asiento;
    private Integer id_espacio;

    public Asientos(Integer numero_asiento, String tipo_asiento, String estado_asiento, Integer id_espacio) {
        this.numero_asiento = numero_asiento;
        this.tipo_asiento = tipo_asiento;
        this.estado_asiento = estado_asiento;
        this.id_espacio = id_espacio;
    }

}