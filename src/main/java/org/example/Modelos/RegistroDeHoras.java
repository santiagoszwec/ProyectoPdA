package org.example.Modelos;
import java.time.LocalDate;

public class RegistroDeHoras {

    private float cantidadDeHoras;
    private LocalDate fecha;

    public RegistroDeHoras(float cantidadDeHoras, LocalDate fecha) {
        this.cantidadDeHoras = cantidadDeHoras;
        this.fecha = fecha;
    }

    public float getCantidadDeHoras() {
        return cantidadDeHoras;
    }

    public void setCantidadDeHoras(float cantidadDeHoras) {
        this.cantidadDeHoras = cantidadDeHoras;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "RegistroDeHoras{" +
                "cantidadDeHoras=" + cantidadDeHoras +
                ", fecha=" + fecha +
                '}';
    }
}