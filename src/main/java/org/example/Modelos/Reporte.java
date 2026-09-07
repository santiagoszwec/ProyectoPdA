package org.example.Modelos;
import java.time.LocalDate;

public class Reporte {

    private int id;
    private String contenido;
    private String motivo;
    private String resolucion;
    private LocalDate fechaReporte;
    private LocalDate fechaResolucion;
    private int publicacionId;

    public Reporte(int id, String contenido, String motivo, String resolucion, LocalDate fechaReporte, LocalDate fechaResolucion) {
        this.id = id;
        this.contenido = contenido;
        this.motivo = motivo;
        this.resolucion = resolucion;
        this.fechaReporte = fechaReporte;
        this.fechaResolucion = fechaResolucion;
    }

    public Reporte(int id, String contenido, String motivo, String resolucion, LocalDate fechaReporte, LocalDate fechaResolucion, int publicacionId) {
        this.id = id;
        this.contenido = contenido;
        this.motivo = motivo;
        this.resolucion = resolucion;
        this.fechaReporte = fechaReporte;
        this.fechaResolucion = fechaResolucion;
        this.publicacionId = publicacionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public int getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(int publicacionId) {
        this.publicacionId = publicacionId;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", motivo='" + motivo + '\'' +
                ", resolucion='" + resolucion + '\'' +
                ", fechaReporte=" + fechaReporte +
                ", fechaResolucion=" + fechaResolucion +
                '}';
    }
}