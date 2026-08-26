package org.example.Modelos;
import org.example.ENUMS.EstadoDuda;
import org.example.ENUMS.TipoCategoria;

import java.time.LocalDate;

public class Duda extends Publicacion {

    private EstadoDuda estado;

    public Duda(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, TipoCategoria categoria) {
        super(id, mensaje, imagenUrl, fechaPublicacion, categoria);
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