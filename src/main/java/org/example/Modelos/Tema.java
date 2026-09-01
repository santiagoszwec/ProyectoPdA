package org.example.Modelos;

public class Tema {
    private int cursoId;
    private String nombre;

    public Tema(int cursoId, String nombre) {
        this.cursoId = cursoId;
        this.nombre = nombre;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "cursoId=" + cursoId +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
