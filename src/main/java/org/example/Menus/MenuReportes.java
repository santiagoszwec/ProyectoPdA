package org.example.Menus;

import org.example.DAOS.PublicacionDAO;
import org.example.DAOS.ReporteDAO;
import org.example.Modelos.Publicacion;
import org.example.Modelos.Reporte;

import java.util.List;
import java.util.Scanner;

public class MenuReportes {

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE REPORTES =====");
            System.out.println("1. Revisar publicaciones reportadas");
            System.out.println("2. Listar reportes pendientes");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    revisarReportes(sc);
                    break;

                case 2:
                    listarReportesPendientes();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void listarReportesPendientes() {
        List<Reporte> reportes = ReporteDAO.listarReportesAbiertos();

        if (reportes.isEmpty()) {
            System.out.println("No hay reportes pendientes.");
            return;
        }

        for (Reporte reporte : reportes) {
            System.out.println("[" + reporte.getId() + "] Publicación: " + reporte.getPublicacionId()
                    + " | Motivo: " + reporte.getMotivo());
        }
    }

    private static void revisarReportes(Scanner sc) {
        List<Reporte> reportes = ReporteDAO.listarReportesAbiertos();

        if (reportes.isEmpty()) {
            System.out.println("No hay reportes pendientes.");
            return;
        }

        System.out.println("Reportes pendientes:");
        for (Reporte reporte : reportes) {
            System.out.println("[" + reporte.getId() + "] Publicación: " + reporte.getPublicacionId()
                    + " | Motivo: " + reporte.getMotivo());
        }

        System.out.print("Ingrese el ID del reporte a revisar: ");
        int reporteId = Integer.parseInt(sc.nextLine());

        Reporte reporte = reportes.stream()
                .filter(r -> r.getId() == reporteId)
                .findFirst()
                .orElse(null);

        if (reporte == null) {
            System.out.println("No se encontró un reporte con ese ID.");
            return;
        }

        Publicacion publicacion = PublicacionDAO.buscarPorId(reporte.getPublicacionId());
        if (publicacion == null) {
            System.out.println("La publicación asociada al reporte no existe.");
            return;
        }

        System.out.println("\n---- Detalle del reporte ----");
        System.out.println("Contenido reportado: " + reporte.getContenido());
        System.out.println("Motivo del reporte: " + reporte.getMotivo());

        System.out.println("\n---- Contenido de la publicación ----");
        System.out.println("[" + publicacion.getId() + "] " + publicacion.getMensaje());
        if (publicacion.getImagenUrl() != null) {
            System.out.println("Imagen: " + publicacion.getImagenUrl());
        }
        System.out.println("Fecha: " + publicacion.getFechaPublicacion());

        System.out.print("\n¿Confirma la infracción y desea dar de baja la publicación? (S/N): ");
        String confirmacion = sc.nextLine();
        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        System.out.print("Registre el motivo de la eliminación: ");
        String motivo = sc.nextLine();

        boolean dadoDeBaja = PublicacionDAO.darDeBaja(publicacion.getId(), motivo);

        System.out.println(dadoDeBaja
                ? "Publicación dada de baja correctamente. Se notificó al autor."
                : "No se pudo dar de baja la publicación.");
    }
}
