package org.example.Modelos;
import org.example.ENUMS.EstadoDuda;
import org.example.ENUMS.TipoCategoria;

import java.time.LocalDate;

public class Duda extends Publicacion {

    private EstadoDuda estado;
    private TipoCategoria categoria;

    public Duda(EstadoDuda estado) {
        this.estado = estado;
    }

    public Duda(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, TipoCategoria categoria, EstadoDuda estado) {
        super(id, mensaje, imagenUrl, fechaPublicacion, categoria);
        this.estado = estado;
    }

    public EstadoDuda getEstado() {
        return estado;
    }

    public void setEstado(EstadoDuda estado) {
        this.estado = estado;
    }

    @Override
    public TipoCategoria getCategoria() {
        return categoria;
    }

    @Override
    public void setCategoria(TipoCategoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Duda{" +
                "estado=" + estado +
                ", categoria=" + categoria +
                '}';
    }
}