package org.example.Menus;

import org.example.Consola;
import org.example.DAOS.PublicacionDAO;
import org.example.DAOS.ReporteDAO;
import org.example.Modelos.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.example.DAOS.ComentarioDAO;

import static org.example.Menus.MenuPublicaciones.mostrarPublicaciones;

public class MenuReportes {

    public static void mostrar(Scanner sc, Usuario usuarioActual) {

        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE REPORTES =====");
            System.out.println("1. Revisar publicaciones reportadas");
            System.out.println("2. Revisar comentarios reportados");
            System.out.println("3. Listar reportes pendientes");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Consola.leerOpcion(sc);

            switch (opcion) {

                case 1:
                    revisarReportesPublicaciones(sc);
                    break;

                case 2:
                    revisarReportesComentarios(sc);
                    break;

                case 3:
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
            String objetivo = reporte.getComentarioId() != null
                    ? "Comentario " + reporte.getComentarioId()
                    : "Publicación " + reporte.getPublicacionId();
            System.out.println("[" + reporte.getId() + "] " + objetivo + " | Motivo: " + reporte.getMotivo());
        }
    }

    private static void revisarReportesPublicaciones(Scanner sc) {
        List<Reporte> reportes = ReporteDAO.listarReportesPublicacionesAbiertos();

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

        System.out.println("\n1. Confirmar infracción (aprobar)");
        System.out.println("2. Rechazar reporte");
        System.out.println("3. Cancelar");
        System.out.print("Seleccione una opción: ");

        int opcion = Consola.leerOpcion(sc);

        switch (opcion) {
            case 1:
                System.out.print("Registre el motivo de la eliminación: ");
                String motivo = sc.nextLine();

                boolean dadoDeBaja = PublicacionDAO.darDeBaja(publicacion.getId(), motivo);

                System.out.println(dadoDeBaja
                        ? "Reporte aprobado y publicación dada de baja correctamente. Se notificó al autor."
                        : "No se pudo dar de baja la publicación.");
                break;

            case 2:
                System.out.print("Registre la decisión de rechazo: ");
                String decision = sc.nextLine();

                boolean resuelto = ReporteDAO.resolver(reporte.getId(), decision);

                System.out.println(resuelto
                        ? "Reporte rechazado y cerrado correctamente."
                        : "No se pudo procesar el reporte.");
                break;

            default:
                System.out.println("Operación cancelada.");
                break;
        }
    }

    private static void revisarReportesComentarios(Scanner sc) {
        List<Reporte> reportes = ReporteDAO.listarReportesComentariosAbiertos();

        if (reportes.isEmpty()) {
            System.out.println("No hay reportes de comentarios pendientes.");
            return;
        }

        System.out.println("Reportes de comentarios pendientes:");
        for (Reporte reporte : reportes) {
            System.out.println("[" + reporte.getId() + "] Comentario: " + reporte.getComentarioId()
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

        Comentario comentario = ComentarioDAO.buscarPorId(reporte.getComentarioId());
        if (comentario == null) {
            System.out.println("El comentario asociado al reporte no existe.");
            return;
        }

        System.out.println("\n---- Detalle del reporte ----");
        System.out.println("Contenido reportado: " + reporte.getContenido());
        System.out.println("Motivo del reporte: " + reporte.getMotivo());

        System.out.println("\n---- Comentario ----");
        System.out.println("[" + comentario.getId() + "] " + comentario.getMensaje());

        System.out.println("\n1. Confirmar infracción (eliminar)");
        System.out.println("2. Rechazar reporte");
        System.out.println("3. Cancelar");
        System.out.print("Seleccione una opción: ");

        int opcion = Consola.leerOpcion(sc);

        switch (opcion) {
            case 1:
                darDeBajaComentario(sc, comentario);
                break;

            case 2:
                System.out.print("Registre la decisión de rechazo: ");
                String decision = sc.nextLine();

                boolean resuelto = ReporteDAO.resolver(reporte.getId(), decision);

                System.out.println(resuelto
                        ? "Reporte rechazado y cerrado correctamente."
                        : "No se pudo procesar el reporte.");
                break;

            default:
                System.out.println("Operación cancelada.");
                break;
        }
    }

    private static void darDeBajaComentario(Scanner sc, Comentario comentario) {
        System.out.print("Registre el motivo de la eliminación: ");
        String motivo = sc.nextLine();

        boolean eliminado = ComentarioDAO.darDeBaja(comentario.getId(), motivo);

        System.out.println(eliminado
                ? "Reporte aprobado y comentario eliminado correctamente. Se notificó al autor."
                : "No se pudo eliminar el comentario.");
    }
}