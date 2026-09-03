package org.example.Menus;

import org.example.DAOS.CursoDAO;
import org.example.Modelos.Curso;

import java.util.Scanner;

public class MenuAdmin {

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Gestión de cursos");
            System.out.println("2. Gestión de usuarios");
            System.out.println("3. Gestión de materiales");
            System.out.println("4. Gestión de publicaciones");
            System.out.println("5. Gestión de reportes");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    MenuCursos.mostrar(sc);
                    break;

                case 2:
                    System.out.println("Gestión de usuarios");
                    MenuUsuarios.mostrar(sc);
                    break;

                case 3:
                    System.out.println("Gestión de materiales");
                    // MenuMateriales.mostrar(sc);
                    break;

                case 4:
                    System.out.println("Gestión de publicaciones");
                    MenuPublicaciones.mostrar(sc);
                    break;

                case 5:
                    System.out.println("Gestión de reportes");
                    // MenuReportes.mostrar(sc);
                    break;

                case 0:
                    System.out.println("Sesión cerrada.");
                    break;
            }

        } while (opcion != 0);
    }
}