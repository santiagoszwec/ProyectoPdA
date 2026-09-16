package org.example.Menus;

import org.example.Consola;
import org.example.DAOS.CursoDAO;
import org.example.DAOS.InscripcionDAO;
import org.example.Modelos.Curso;
import org.example.Modelos.Inscripcion;
import org.example.Modelos.Usuario;

import java.util.List;
import java.util.Scanner;

public class MenuAdmin {

    public static void mostrar(Scanner sc, Usuario usuarioActual) {

        int opcion;
        boolean sesionActiva = true;

        do {
            System.out.println("\n===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Gestión de cursos");
            System.out.println("2. Gestión de usuarios");
            System.out.println("3. Seleccionar curso");
            System.out.println("4. Gestión de reportes");
            System.out.println("5. Gestión de inscripciones");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = Consola.leerOpcion(sc);

            switch (opcion) {

                case 1:
                    MenuCursos.mostrar(sc);
                    break;

                case 2:
                    System.out.println("Gestión de usuarios");
                    MenuUsuarios.mostrar(sc);
                    break;

                case 3:
                    seleccionarCurso(sc, usuarioActual);
                    break;

                case 4:
                    MenuReportes.mostrar(sc, usuarioActual);
                    break;

                case 5:
                    MenuInscripciones.mostrar(sc);
                    break;

                case 6:
                    System.out.print("¿Desea cerrar sesión? S/N: ");
                    String confirmacion = sc.nextLine();
                    if (confirmacion.equalsIgnoreCase("S")) {
                        System.out.println("Sesión cerrada.");
                        sesionActiva = false;
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                    break;
                default:
                    break;
            }

        } while (sesionActiva);
    }

    private static void seleccionarCurso(Scanner sc, Usuario usuarioActual) {

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

            System.out.println("[" + curso.getId() + "] " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio() +
                            " | Créditos: " + curso.getCreditos() +
                            " | Estado: " + inscripcion.getEstado());
        }

        System.out.print("\nIngrese el ID del curso: ");
        int cursoId = Consola.leerOpcion(sc);
        boolean inscripto = false;

        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getCursoId() == cursoId) {
                inscripto = true;
                break;
            }
        }

        if (!inscripto) {
            System.out.println("No estás inscripto en ese curso.");
            return;
        }
        Curso cursoSeleccionado = CursoDAO.buscarPorId(cursoId);

        if (cursoSeleccionado == null) {
            System.out.println("El curso no existe.");
            return;
        }
        MenuCursoSeleccionado.mostrar(sc, usuarioActual, cursoSeleccionado);
    }
}
