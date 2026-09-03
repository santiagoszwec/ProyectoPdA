package org.example.Modelos;
import org.example.ENUMS.TipoCategoria;

import java.time.LocalDate;


public class Publicacion {

    private int id;
    private String mensaje;
    private String imagenUrl;
    private LocalDate fechaPublicacion;
    private TipoCategoria categoria;
    private boolean dadaDeBaja;

    public Publicacion() {
    }

    public Publicacion(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, TipoCategoria categoria, boolean dadaDeBaja) {
        this.id = id;
        this.mensaje = mensaje;
        this.imagenUrl = imagenUrl;
        this.fechaPublicacion = fechaPublicacion;
        this.categoria = categoria;
        this.dadaDeBaja = dadaDeBaja;
    }

    public Publicacion(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, TipoCategoria categoria) {
        this(id, mensaje, imagenUrl, fechaPublicacion, categoria, false);
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

    public boolean isDadaDeBaja() {
        return dadaDeBaja;
    }

    public void setDadaDeBaja(boolean dadaDeBaja) {
        this.dadaDeBaja = dadaDeBaja;
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