package org.example.Menus;

import org.example.DAOS.CursoDAO;
import org.example.DAOS.InscripcionDAO;
import org.example.Modelos.Curso;
import org.example.Modelos.Inscripcion;
import org.example.Modelos.Usuario;
import org.example.DAOS.NotificacionDAO;
import org.example.Modelos.Notificacion;

import java.util.List;
import java.util.Scanner;

public class MenuEstudiante {

    public static void mostrar(Scanner sc, Usuario usuarioActual) {

        int opcion;

        do {
            System.out.println("\n===== MENÚ ESTUDIANTE =====");
            System.out.println("1. Dudas y comentarios");
            System.out.println("2. Mis cursos");
            System.out.println("3. Mis notificaciones");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    //MenuDudas.mostrar(sc, usuarioActual, cursoSeleccionado);
                    break;

                case 2:
                    misCursos(usuarioActual);
                    break;

                case 3:
                    misNotificaciones(usuarioActual);
                    break;


                case 0:
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void misCursos(Usuario usuarioActual) {

        System.out.println("\n===== MIS CURSOS =====");

        List<Inscripcion> inscripciones = InscripcionDAO.listarPorUsuario(usuarioActual.getId());

        if (inscripciones.isEmpty()) {
            System.out.println("No tenés cursos cargados todavía.");
            return;
        }

        for (Inscripcion inscripcion : inscripciones) {
            Curso curso = CursoDAO.buscarPorId(inscripcion.getCursoId());
            if (curso == null) {
                continue;
            }
            System.out.println(
                    "[" + curso.getId() + "] " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio() +
                            " | Créditos: " + curso.getCreditos() +
                            " | Estado: " + inscripcion.getEstado());
        }
    }
    private static void misNotificaciones(Usuario usuarioActual) {

        System.out.println("\n===== MIS NOTIFICACIONES =====");

        List<Notificacion> notificaciones = NotificacionDAO.listarPorUsuario(usuarioActual.getId());

        if (notificaciones.isEmpty()) {
            System.out.println("No tenés notificaciones.");
            return;
        }

        for (Notificacion notificacion : notificaciones) {
            System.out.println(
                    "[" + notificacion.getFecha() + "] (" + notificacion.getTipo() + ") " +
                            notificacion.getMensaje());
        }
    }
}