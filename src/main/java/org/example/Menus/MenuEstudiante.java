package org.example.Menus;

import org.example.Modelos.Usuario;

import java.util.Scanner;

public class MenuEstudiante {

    public static void mostrar(Scanner sc, Usuario usuarioActual) {

        int opcion;

        do {
            System.out.println("\n===== MENÚ ESTUDIANTE =====");
            System.out.println("1. Dudas y comentarios");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    MenuDudas.mostrar(sc, usuarioActual);
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
}
