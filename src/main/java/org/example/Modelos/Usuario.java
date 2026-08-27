package org.example.Modelos;

import org.example.ENUMS.TipoRol;

public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private int anioDeGeneracion;
    private TipoRol rol;
    private String contrasenia;

    public Usuario(int id, String nombre, String correo, int anioDeGeneracion, TipoRol rol, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.anioDeGeneracion = anioDeGeneracion;
        this.rol = rol;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getAnioDeGeneracion() {
        return anioDeGeneracion;
    }

    public void setAnioDeGeneracion(int anioDeGeneracion) {
        this.anioDeGeneracion = anioDeGeneracion;
    }

    public TipoRol getRol() {
        return rol;
    }

    public void setRol(TipoRol rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", anioDeGeneracion=" + anioDeGeneracion +
                ", rol=" + rol +
                ", contrasenia=" + contrasenia +
                '}';
    }
}