package org.example;

public class Curso {

    private int id;
    private String nombre;
    private int semestre;
    private int anio;
    private int creditos;

    public Curso(int id, String nombre, int semestre, int anio, int creditos) {
        this.id = id;
        this.nombre = nombre;
        this.semestre = semestre;
        this.anio = anio;
        this.creditos = creditos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", semestre=" + semestre +
                ", anio=" + anio +
                ", creditos=" + creditos +
                '}';
    }
}