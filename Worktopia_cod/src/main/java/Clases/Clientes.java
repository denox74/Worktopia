package Clases;

import java.math.BigDecimal;

public class Clientes {

    private int id_cliente;
    private String DNI;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String telefono;
    private String contrasenia;
    private boolean frecuente;
    private BigDecimal descuento;

    public Clientes(int id_cliente, String dni, String nombre, String primerApellido, String segundoApellido, String email, String telefono, String contrasenia, boolean frecuente, BigDecimal descuento) {
        this.id_cliente = id_cliente;
        this.DNI = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.frecuente = frecuente;
        this.descuento = descuento;
    }

    public int getIdCliente() {
        return id_cliente;
    }

    public void setIdCliente(int idCliente) {
        this.id_cliente = idCliente;
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


    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setFrecuente(boolean frecuente) {
        this.frecuente = frecuente;
    }

    @Override
    public String toString() {
        return "Clientes{" +
                "id_cliente=" + id_cliente +
                ", dni='" + DNI + '\'' +
                ", nombre='" + nombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", frecuente=" + frecuente +
                ", descuento=" + descuento +
                '}';
    }
}

