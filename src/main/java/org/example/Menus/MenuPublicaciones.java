package org.example.Menus;

import org.example.DAOS.PublicacionDAO;
import org.example.Modelos.Publicacion;

import java.util.List;
import java.util.Scanner;

public class MenuPublicaciones {

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE PUBLICACIONES=====");
            System.out.println("1. Crear Publicacion");
            System.out.println("2. Listar Publicaciones");
            System.out.println("3. Editar Publicacion");
            System.out.println("4. Dar de baja Publicacion");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:

                    break;

                case 2:
                    listarPublicaciones();
                    break;

                case 3:

                    break;

                case 4:
                    darDeBajaPublicacion(sc);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void listarPublicaciones() {
        List<Publicacion> publicaciones = PublicacionDAO.listarTodos();

        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones activas.");
            return;
        }

        for (Publicacion publicacion : publicaciones) {
            System.out.println("[" + publicacion.getId() + "] " + publicacion.getMensaje());
        }
    }

    private static void darDeBajaPublicacion(Scanner sc) {
        List<Publicacion> publicaciones = PublicacionDAO.listarTodos();

        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones activas para dar de baja.");
            return;
        }

        System.out.println("Publicaciones activas:");
        for (Publicacion publicacion : publicaciones) {
            System.out.println("[" + publicacion.getId() + "] " + publicacion.getMensaje());
        }

        System.out.print("Ingrese el ID de la publicación a dar de baja: ");
        int id = Integer.parseInt(sc.nextLine());

        Publicacion publicacion = publicaciones.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (publicacion == null) {
            System.out.println("No se encontró una publicación con ese ID.");
            return;
        }

        System.out.println("Publicación seleccionada: " + publicacion.getMensaje());

        System.out.print("¿Confirma la infracción y desea dar de baja esta publicación? (S/N): ");
        String confirmacion = sc.nextLine();
        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        System.out.print("Registre el motivo de la eliminación: ");
        String motivo = sc.nextLine();

        boolean dadoDeBaja = PublicacionDAO.darDeBaja(id, motivo);

        System.out.println(dadoDeBaja
                ? "Publicación dada de baja correctamente. Se notificó al autor."
                : "No se pudo dar de baja la publicación.");
    }
}
