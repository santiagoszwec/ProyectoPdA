package org.example;
import org.example.ENUMS.EstadoDuda;

public class Duda extends Publicacion {

    private EstadoDuda estado;

    public Duda(EstadoDuda estado) {
        this.estado = estado;
    }

    public EstadoDuda getEstado() {
        return estado;
    }

    public void setEstado(EstadoDuda estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Duda{" +
                "estado=" + estado +
                '}';
    }
}