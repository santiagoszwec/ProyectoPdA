package org.example.Menus;

import org.example.Modelos.Curso;
import org.example.Modelos.Usuario;

import java.util.Scanner;

public class MenuCursoSeleccionado {

    public static void mostrar(
            Scanner sc,
            Usuario usuarioActual,
            Curso cursoSeleccionado) {

        int opcion;

        do {
            System.out.println("\n===== CURSO: " + cursoSeleccionado.getNombre() + " =====");
            System.out.println("1. Publicaciones");
            System.out.println("2. Materiales");
            System.out.println("3. Dudas y comentarios");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = org.example.Consola.leerOpcion(sc);

            switch (opcion) {
                case 1:
                    MenuPublicaciones.mostrar(sc, usuarioActual, cursoSeleccionado);
                    break;
                case 2:
                    MenuMateriales.mostrar(sc, usuarioActual, cursoSeleccionado);
                    break;
                case 3:
                    MenuDudas.mostrar(sc, usuarioActual, cursoSeleccionado);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }
}
