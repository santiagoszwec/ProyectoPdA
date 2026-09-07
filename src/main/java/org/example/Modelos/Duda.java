package org.example.Modelos;
import org.example.ENUMS.EstadoDuda;
import org.example.ENUMS.TipoCategoria;

import java.time.LocalDate;

public class Duda extends Publicacion {

    private EstadoDuda estado;
    private TipoCategoria categoria;

    public Duda(EstadoDuda estado, TipoCategoria categoria) {
        this.estado = estado;
        this.categoria = categoria;
    }

    public Duda(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, boolean dadaDeBaja, EstadoDuda estado, TipoCategoria categoria) {
        super(id, mensaje, imagenUrl, fechaPublicacion, dadaDeBaja);
        this.estado = estado;
        this.categoria = categoria;
    }

    public EstadoDuda getEstado() {
        return estado;
    }

    public void setEstado(EstadoDuda estado) {
        this.estado = estado;
    }

    public TipoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoCategoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Duda{" +
                "id=" + getId() +
                ", mensaje='" + getMensaje() + '\'' +
                ", fechaPublicacion=" + getFechaPublicacion() +
                ", estado=" + estado +
                ", categoria=" + categoria +
                '}';
    }
}