package org.example.Menus;

import org.example.DAOS.CursoDAO;
import org.example.DAOS.TemaDAO;
import org.example.Modelos.Curso;
import org.example.Modelos.Tema;

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
                    System.out.println("Listar cursos");
                    break;

                case 3:
                    System.out.println("Modificar curso");
                    break;

                case 4:
                    System.out.println("Eliminar curso");
                    break;

                case 5:
                    System.out.println("Volviendo al menú administrador...");
                    break;

                default:
                    System.out.println("Opción inválida.");
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
}