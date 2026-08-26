package org.example;

import org.example.ENUMS.TipoEstado;

public class Progreso {

    private TipoEstado estado;

    public Progreso(TipoEstado estado) {
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