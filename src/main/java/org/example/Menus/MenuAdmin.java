package org.example.Menus;

import org.example.Consola;
import org.example.DAOS.CursoDAO;
import org.example.Modelos.Curso;
import org.example.Modelos.Usuario;

import java.util.Scanner;

public class MenuAdmin {

    public static void mostrar(Scanner sc, Usuario usuarioActual) {

        int opcion;
        boolean sesionActiva = true;

        do {
            System.out.println("\n===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Gestión de cursos");
            System.out.println("2. Gestión de usuarios");
            System.out.println("3. Material de Estudio");
            System.out.println("4. Gestión de publicaciones");
            System.out.println("5. Gestión de reportes");
            System.out.println("6. Gestión de dudas y comentarios");
            System.out.println("7. Gestión de inscripciones");
            System.out.println("8. Cerrar sesión");
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
                    System.out.println("Material de Estudio");
                    MenuMateriales.mostrar(sc);
                    break;

                case 4:
                    System.out.println("Gestión de publicaciones");
                    MenuPublicaciones.mostrar(sc, usuarioActual);
                    break;

                case 5:
                    System.out.println("Gestión de reportes");
                    MenuReportes.mostrar(sc, usuarioActual);
                    break;

                case 6:
                    MenuDudas.mostrar(sc, usuarioActual);
                    break;

                case 7:
                    MenuInscripciones.mostrar(sc);
                    break;

                case 8:
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
}