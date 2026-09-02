package org.example.Modelos;

import org.example.ENUMS.TipoCategoria;

import java.time.LocalDate;

public class Mensaje extends Publicacion {

    private TipoCategoria categoria;

    public Mensaje(TipoCategoria categoria) {
        this.categoria = categoria;
    }

    public Mensaje(int id, String mensaje, String imagenUrl, LocalDate fechaPublicacion, TipoCategoria categoria, TipoCategoria categoria1) {
        super(id, mensaje, imagenUrl, fechaPublicacion, categoria);
        this.categoria = categoria1;
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
        return "Mensaje{" +
                "categoria=" + categoria +
                '}';
    }
}