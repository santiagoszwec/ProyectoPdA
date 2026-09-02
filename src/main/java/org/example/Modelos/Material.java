package org.example.Modelos;
import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoCategoria;
import org.example.ENUMS.TipoMaterial;

import java.time.LocalDate;

public class Material extends Publicacion {

    private String archivoUrl;
    private TipoMaterial tipoMaterial;
    private TipoArchivo tipoArchivo;

    public Material(String archivoUrl, TipoMaterial tipoMaterial, TipoArchivo tipoArchivo) {
        this.archivoUrl = archivoUrl;
        this.tipoMaterial = tipoMaterial;
        this.tipoArchivo = tipoArchivo;
    }

    public Material(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, TipoCategoria categoria, String archivoUrl, TipoMaterial tipoMaterial, TipoArchivo tipoArchivo) {
        super(id, mensaje, imagenUrl, fechaPublicacion, categoria);
        this.archivoUrl = archivoUrl;
        this.tipoMaterial = tipoMaterial;
        this.tipoArchivo = tipoArchivo;
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

    @Override
    public String toString() {
        return "Material{" +
                "archivoUrl='" + archivoUrl + '\'' +
                ", tipoMaterial=" + tipoMaterial +
                ", tipoArchivo=" + tipoArchivo +
                '}';
    }
}