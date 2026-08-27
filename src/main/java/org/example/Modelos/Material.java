package org.example.Modelos;
import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoMaterial;

import java.time.LocalDate;

public class Material {

    private int id;
    private TipoArchivo archivoUrl;
    private TipoMaterial tipo;
    private LocalDate fecha;
    private String docente;

    public Material(int id, TipoArchivo archivoUrl, TipoMaterial tipo, LocalDate fecha, String docente) {
        this.id = id;
        this.archivoUrl = archivoUrl;
        this.tipo = tipo;
        this.fecha = fecha;
        this.docente = docente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoArchivo getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(TipoArchivo archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public TipoMaterial getTipo() {
        return tipo;
    }

    public void setTipo(TipoMaterial tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", archivoUrl=" + archivoUrl +
                ", tipo=" + tipo +
                ", fecha=" + fecha +
                ", docente='" + docente + '\'' +
                '}';
    }
}