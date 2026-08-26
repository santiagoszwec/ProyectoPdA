package org.example;
import java.time.LocalDate;

public class Comentario {

    private int id;
    private String mensaje;
    private String imagenUrl;
    private LocalDate fechaPublicacion;

    private Comentario comentarioPadre;

    public Comentario(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, Comentario comentarioPadre) {
        this.id = id;
        this.mensaje = mensaje;
        this.imagenUrl = imagenUrl;
        this.fechaPublicacion = fechaPublicacion;
        this.comentarioPadre = comentarioPadre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Comentario getComentarioPadre() {
        return comentarioPadre;
    }

    public void setComentarioPadre(Comentario comentarioPadre) {
        this.comentarioPadre = comentarioPadre;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                ", comentarioPadre=" + comentarioPadre +
                '}';
    }
}
