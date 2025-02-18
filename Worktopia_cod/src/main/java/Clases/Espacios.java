package Clases;

public class Espacios {

    private int id_espacio;
    private String nombre;

    public Espacios(int id_espacio, String nombre) {
        this.id_espacio = id_espacio;
        this.nombre = nombre;
    }

    public int getId_espacio() {
        return id_espacio;
    }

    public void setId_espacio(int id_espacio) {
        this.id_espacio = id_espacio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Espacios{" +
                "id_espacio=" + id_espacio +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

