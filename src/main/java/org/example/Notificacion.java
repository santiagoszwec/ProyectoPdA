package org.example;
import org.example.ENUMS.TipoNotificacion;

import java.time.LocalDate;

public class Notificacion {

    private int id;
    private LocalDate fecha;
    private TipoNotificacion tipo;
    private String mensaje;

    public Notificacion(int id, LocalDate fecha, TipoNotificacion tipo, String mensaje) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", tipo=" + tipo +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
