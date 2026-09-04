package org.example.Modelos;
import org.example.ENUMS.TipoNotificacion;

import java.time.LocalDate;

public class Notificacion {

    private int id;
    private LocalDate fecha;
    private TipoNotificacion tipo;
    private String mensaje;
    private int usuarioId;

    public Notificacion(int id, LocalDate fecha, TipoNotificacion tipo, String mensaje) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public Notificacion(int id, LocalDate fecha, TipoNotificacion tipo, String mensaje, int usuarioId) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.usuarioId = usuarioId;
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
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
