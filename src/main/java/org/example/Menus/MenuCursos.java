package org.example.Menus;

import org.example.DAOS.CursoDAO;
import org.example.DAOS.TemaDAO;
import org.example.Modelos.Curso;
import org.example.Modelos.Tema;

import java.util.List;
import java.util.Scanner;

public class MenuCursos {

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE CURSOS =====");
            System.out.println("1. Agregar nuevo curso");
            System.out.println("2. Listar cursos");
            System.out.println("3. Modificar curso");
            System.out.println("4. Eliminar curso");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    agregarCurso(sc);
                    break;

                case 2:
                    listarCursos();
                    break;

                case 3:
                    System.out.println("Modificar curso");
                    break;

                case 4:
                    eliminarCurso(sc);
                    break;

                case 5:
                    System.out.println("Volviendo al menú administrador...");
                    break;
            }

        } while (opcion != 0);
    }

    private static void agregarCurso(Scanner sc) {

        System.out.println("\n===== AGREGAR NUEVO CURSO =====");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Semestre: ");
        int semestre = Integer.parseInt(sc.nextLine());

        System.out.print("Año: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Créditos: ");
        int creditos = Integer.parseInt(sc.nextLine());

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        Curso curso = new Curso(nombre, semestre, anio, creditos, descripcion);

        int cursoId = CursoDAO.crear(curso);

        if (cursoId > 0) {
            System.out.println("\nCurso creado correctamente.");
            System.out.print("¿Desea agregar temas de estudio? (S/N): ");
            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                agregarTemas(sc, cursoId);
            }
        } else {
            System.out.println("\nNo se pudo crear el curso.");
        }
    }
    private static void agregarTemas(Scanner sc, int cursoId) {

        String respuesta;

        do {
            System.out.print("Nombre del tema: ");
            String nombreTema = sc.nextLine();

            Tema tema = new Tema(cursoId, nombreTema);

            boolean creado = TemaDAO.crear(tema);

            if (creado) {
                System.out.println("Tema agregado correctamente.");
            } else {
                System.out.println("No se pudo agregar el tema.");
            }

            System.out.print("¿Desea agregar otro tema? (S/N): ");
            respuesta = sc.nextLine();

        } while (respuesta.equalsIgnoreCase("S"));
    }
    private static void listarCursos() {

        System.out.println("\n===== LISTA DE CURSOS =====");

        List<Curso> cursos = CursoDAO.listarTodos();

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        for (Curso curso : cursos) {
            System.out.println(
                    "ID: " + curso.getId() +
                            " | Nombre: " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio() +
                            " | Créditos: " + curso.getCreditos()
            );
        }
    }
    private static void eliminarCurso(Scanner sc) {

        System.out.println("\n===== ELIMINAR CURSO =====");

        List<Curso> cursos = CursoDAO.listarTodos();

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        System.out.println("\nCursos disponibles:");

        for (Curso curso : cursos) {
            System.out.println(
                    "ID: " + curso.getId() +
                            " | Nombre: " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio());
        }

        System.out.print("\nIngrese el ID del curso a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        Curso cursoSeleccionado = null;

        for (Curso curso : cursos) {
            if (curso.getId() == id) {
                cursoSeleccionado = curso;
                break;
            }
        }

        if (cursoSeleccionado == null) {
            System.out.println("No se encontró ningún curso con ese ID.");
            return;
        }

        System.out.println("\nCurso seleccionado: " + cursoSeleccionado.getNombre());

        System.out.print("¿Está seguro de que desea eliminar este curso? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean eliminado = CursoDAO.desactivar(id);

        if (eliminado) {
            System.out.println("Curso eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el curso.");
        }
    }
}