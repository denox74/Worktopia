package Clases;

import java.math.BigDecimal;

public class Clientes {

    private int idCliente;
    private String nombre;
    private String email;
    private String telefono;
    private boolean frecuente;
    private BigDecimal descuento;

    public Clientes(int idCliente, String nombre, String email, String telefono, boolean frecuente, BigDecimal descuento) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.frecuente = frecuente;
        this.descuento = descuento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isFrecuente() {
        return frecuente;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }
}
