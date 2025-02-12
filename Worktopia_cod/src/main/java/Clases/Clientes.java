package Clases;

public class Clientes {

    private static int ID = 0;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String usuario;
    private String contrasena;

    public Clientes(String nombre, String apellido, String dni, String telefono, String email, String direccion, String ciudad, String provincia, String pais, String cp, String usuario, String contrasena) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;

        this.usuario = usuario;
        this.contrasena = contrasena;
        ++ID;
    }


    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }


    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
