package org.example;
import org.example.ENUMS.TipoCategoria;

import java.time.LocalDate;


public class Publicacion {

    private int id;
    private String mensaje;
    private String imagenUrl;
    private LocalDate fechaPublicacion;
    private TipoCategoria categoria;

    public Publicacion() {
    }

    public Publicacion(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, TipoCategoria categoria) {
        this.id = id;
        this.mensaje = mensaje;
        this.imagenUrl = imagenUrl;
        this.fechaPublicacion = fechaPublicacion;
        this.categoria = categoria;
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

    public TipoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoCategoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                ", categoria=" + categoria +
                '}';
    }
}