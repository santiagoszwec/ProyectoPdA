package org.example.Modelos;
import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoCategoria;
import org.example.ENUMS.TipoMaterial;

import java.time.LocalDate;

public class Material extends Publicacion {

    private String archivoUrl;
    private TipoMaterial tipoMaterial;
    private TipoArchivo tipoArchivo;
    private String tema;

    public Material(String archivoUrl, TipoMaterial tipoMaterial, TipoArchivo tipoArchivo, String tema) {
        this.archivoUrl = archivoUrl;
        this.tipoMaterial = tipoMaterial;
        this.tipoArchivo = tipoArchivo;
        this.tema = tema;
    }

    public Material(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, boolean dadaDeBaja, String archivoUrl, TipoMaterial tipoMaterial, TipoArchivo tipoArchivo, String tema) {
        super(id, mensaje, imagenUrl, fechaPublicacion, dadaDeBaja);
        this.archivoUrl = archivoUrl;
        this.tipoMaterial = tipoMaterial;
        this.tipoArchivo = tipoArchivo;
        this.tema = tema;
    }

    public String getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public TipoArchivo getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(TipoArchivo tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + getId() +
                ", mensaje='" + getMensaje() + '\'' +
                ", fechaPublicacion=" + getFechaPublicacion() +
                ", archivoUrl='" + archivoUrl + '\'' +
                ", tipoMaterial=" + tipoMaterial +
                ", tipoArchivo=" + tipoArchivo +
                ", tema='" + tema + '\'' +
                '}';
    }
}