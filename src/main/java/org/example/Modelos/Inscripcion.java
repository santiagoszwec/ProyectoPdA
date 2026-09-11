package org.example.Modelos;

import org.example.ENUMS.TipoEstado;

public class Inscripcion {

    private TipoEstado estado;

    public Inscripcion(TipoEstado estado) {
        this.estado = estado;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Progreso{" +
                "estado=" + estado +
                '}';
    }
}