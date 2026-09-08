package org.example.Menus;

import org.example.DAOS.ComentarioDAO;
import org.example.DAOS.DudaDAO;
import org.example.DAOS.NotificacionDAO;
import org.example.ENUMS.EstadoDuda;
import org.example.ENUMS.TipoCategoria;
import org.example.ENUMS.TipoNotificacion;
import org.example.Modelos.Comentario;
import org.example.Modelos.Duda;
import org.example.Modelos.Notificacion;
import org.example.Modelos.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuDudas {

    public static void mostrar(Scanner sc, Usuario usuarioActual) {

        int opcion;

        do {
            System.out.println("\n===== DUDAS Y COMENTARIOS =====");
            System.out.println("1. Crear duda");
            System.out.println("2. Listar dudas");
            System.out.println("3. Responder una duda");
            System.out.println("4. Comentar una respuesta");
            System.out.println("5. Marcar duda como resuelta (para autores)");
            if (usuarioActual.getRol().name().equalsIgnoreCase("Admin")) {
                System.out.println("6. Eliminar comentario inapropiado (Solo Admin)");
            }
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    crearDuda(sc, usuarioActual);
                    break;

                case 2:
                    listarDudas();
                    break;

                case 3:
                    responderDuda(sc, usuarioActual);
                    break;

                case 4:
                    comentarRespuesta(sc, usuarioActual);
                    break;

                case 5:
                    resolverDuda(sc, usuarioActual);
                    break;

                case 6:
                    if (usuarioActual.getRol().name().equalsIgnoreCase("Admin")) {
                        eliminarComentario(sc);
                    } else {
                        System.out.println("Opción inválida.");
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void crearDuda(Scanner sc, Usuario usuarioActual) {
        System.out.print("Mensaje de la duda: ");
        String mensaje = sc.nextLine();

        System.out.print("URL de imagen (opcional, Enter para omitir): ");
        String imagenUrl = sc.nextLine();
        if (imagenUrl.isBlank()) {
            imagenUrl = null;
        }

        System.out.print("Categoría (Ejercicio, Examen, Reunion): ");
        TipoCategoria categoria = TipoCategoria.valueOf(sc.nextLine().trim());

        Duda duda = new Duda(EstadoDuda.Abierta, categoria, usuarioActual.getId());
        duda.setMensaje(mensaje);
        duda.setImagenUrl(imagenUrl);
        duda.setFechaPublicacion(LocalDate.now());

        boolean creada = DudaDAO.crear(duda);
        System.out.println(creada ? "Duda creada con éxito." : "No se pudo crear la duda.");
    }

    private static void listarDudas() {
        List<Duda> dudas = DudaDAO.listarTodos();

        if (dudas.isEmpty()) {
            System.out.println("No hay dudas cargadas.");
            return;
        }

        for (Duda duda : dudas) {
            System.out.println("[" + duda.getId() + "] " + duda.getMensaje() + " (" + duda.getEstado() + " - " + duda.getCategoria() + ")");
        }
    }

    private static void responderDuda(Scanner sc, Usuario usuarioActual) {
        Duda duda = elegirDuda(sc, null);
        if (duda == null) {
            return;
        }
        
        if (duda.getEstado() == EstadoDuda.Resuelta) {
            System.out.println("Esta duda ya está resuelta. No se aceptan más respuestas principales.");
            return;
        }

        System.out.print("Escriba su respuesta: ");
        String mensaje = sc.nextLine();

        System.out.print("URL de imagen (opcional, Enter para omitir): ");
        String imagenUrl = sc.nextLine();
        if (imagenUrl.isBlank()) {
            imagenUrl = null;
        }

        Comentario respuesta = new Comentario(mensaje, imagenUrl, LocalDate.now(), usuarioActual.getId(), duda.getId(), null);
        boolean creada = ComentarioDAO.crear(respuesta);
        System.out.println(creada ? "Respuesta publicada con éxito." : "No se pudo publicar la respuesta.");
    }

    private static void comentarRespuesta(Scanner sc, Usuario usuarioActual) {
        Duda duda = elegirDuda(sc, null);
        if (duda == null) {
            return;
        }

        List<Comentario> respuestas = ComentarioDAO.listarPorPublicacion(duda.getId());
        if (respuestas.isEmpty()) {
            System.out.println("Esta duda todavía no tiene respuestas. No hay nada que comentar.");
            return;
        }

        System.out.println("Respuestas disponibles:");
        for (Comentario respuesta : respuestas) {
            String destacado = respuesta.isDestacado() ? " [DESTACADA]" : "";
            System.out.println("[" + respuesta.getId() + "] " + respuesta.getMensaje() + destacado);
        }

        System.out.print("Ingrese el ID de la respuesta que quiere comentar: ");
        int respuestaId = Integer.parseInt(sc.nextLine());

        Comentario respuestaElegida = respuestas.stream()
                .filter(r -> r.getId() == respuestaId)
                .findFirst()
                .orElse(null);

        if (respuestaElegida == null) {
            System.out.println("Esa respuesta no existe.");
            return;
        }

        System.out.print("Escriba su comentario: ");
        String mensaje = sc.nextLine();

        System.out.print("URL de imagen (opcional, Enter para omitir): ");
        String imagenUrl = sc.nextLine();
        if (imagenUrl.isBlank()) {
            imagenUrl = null;
        }

        Comentario comentario = new Comentario(mensaje, imagenUrl, LocalDate.now(), usuarioActual.getId(), duda.getId(), respuestaElegida.getId());
        boolean creado = ComentarioDAO.crear(comentario);

        if (!creado) {
            System.out.println("No se pudo publicar el comentario.");
            return;
        }

        System.out.println("Comentario publicado con éxito.");
        mostrarComentariosAnidados(respuestaElegida.getId());
    }

    private static void resolverDuda(Scanner sc, Usuario usuarioActual) {
        // Filtrar para mostrar solo las dudas que creó este usuario (o todas si es Admin)
        Duda duda = elegirDuda(sc, usuarioActual);
        if (duda == null) {
            return;
        }

        if (duda.getEstado() == EstadoDuda.Resuelta) {
            System.out.println("Esta duda ya está marcada como resuelta.");
            return;
        }

        List<Comentario> respuestas = ComentarioDAO.listarPorPublicacion(duda.getId());
        if (respuestas.isEmpty()) {
            System.out.println("Esta duda no tiene respuestas, no puedes marcarla como resuelta aún.");
            return;
        }

        System.out.println("Respuestas disponibles para destacar:");
        for (Comentario respuesta : respuestas) {
            System.out.println("[" + respuesta.getId() + "] " + respuesta.getMensaje());
        }

        System.out.print("Ingrese el ID de la respuesta más útil: ");
        int respuestaId = Integer.parseInt(sc.nextLine());

        Comentario respuestaElegida = respuestas.stream()
                .filter(r -> r.getId() == respuestaId)
                .findFirst()
                .orElse(null);

        if (respuestaElegida == null) {
            System.out.println("Esa respuesta no existe.");
            return;
        }

        // Marcar la duda como resuelta
        boolean dudaResuelta = DudaDAO.marcarComoResuelta(duda.getId());
        // Destacar el comentario
        boolean comentarioDestacado = ComentarioDAO.marcarComoDestacado(respuestaElegida.getId());

        if (dudaResuelta && comentarioDestacado) {
            System.out.println("La duda fue marcada como Resuelta y la respuesta fue destacada.");
            
            // Enviar notificación al autor de la respuesta
            Notificacion notificacion = new Notificacion(
                    0, // ID autogenerado
                    LocalDate.now(),
                    TipoNotificacion.Respuesta,
                    "Tu respuesta a la duda '" + duda.getMensaje() + "' ha sido marcada como la más útil."
            );
            notificacion.setUsuarioId(respuestaElegida.getUsuarioId());
            NotificacionDAO.crear(notificacion);
            System.out.println("Se ha notificado al autor de la respuesta.");
            
        } else {
            System.out.println("Ocurrió un error al intentar resolver la duda.");
        }
    }

    private static void eliminarComentario(Scanner sc) {
        Duda duda = elegirDuda(sc, null);
        if (duda == null) {
            return;
        }

        List<Comentario> respuestas = ComentarioDAO.listarPorPublicacion(duda.getId());
        if (respuestas.isEmpty()) {
            System.out.println("Esta duda no tiene respuestas o comentarios para eliminar.");
            return;
        }

        System.out.println("Comentarios disponibles en esta duda:");
        for (Comentario respuesta : respuestas) {
            System.out.println("[" + respuesta.getId() + "] (Respuesta de Usuario " + respuesta.getUsuarioId() + ") " + respuesta.getMensaje());
            List<Comentario> anidados = ComentarioDAO.listarRespuestas(respuesta.getId());
            for (Comentario anidado : anidados) {
                System.out.println("  -> [" + anidado.getId() + "] (Anidado de Usuario " + anidado.getUsuarioId() + ") " + anidado.getMensaje());
            }
        }

        System.out.print("Ingrese el ID del comentario (o respuesta) que desea eliminar: ");
        int comentarioId = Integer.parseInt(sc.nextLine());
        
        Comentario comentarioAEliminar = ComentarioDAO.buscarPorId(comentarioId);
        
        if (comentarioAEliminar == null || comentarioAEliminar.getPublicacionId() != duda.getId()) {
            System.out.println("No se encontró ese comentario en esta duda.");
            return;
        }

        System.out.print("Ingrese el motivo de la eliminación: ");
        String motivo = sc.nextLine();

        boolean eliminado = ComentarioDAO.darDeBaja(comentarioId);
        if (eliminado) {
            System.out.println("Comentario eliminado lógicamente con éxito (junto con sus respuestas si las tuviera).");

            Notificacion notificacion = new Notificacion(
                    0, 
                    LocalDate.now(),
                    TipoNotificacion.Baja,
                    "Tu comentario ha sido eliminado por un administrador. Motivo: " + motivo
            );
            notificacion.setUsuarioId(comentarioAEliminar.getUsuarioId());
            NotificacionDAO.crear(notificacion);
            System.out.println("Se ha notificado al autor del comentario.");
        } else {
            System.out.println("No se pudo eliminar el comentario.");
        }
    }

    private static void mostrarComentariosAnidados(int comentarioPadreId) {
        List<Comentario> anidados = ComentarioDAO.listarRespuestas(comentarioPadreId);

        System.out.println("Comentarios bajo esta respuesta:");
        for (Comentario anidado : anidados) {
            System.out.println("  - " + anidado.getMensaje());
        }
    }

    // Permitir elegir una duda. Si se pasa un usuario (para resolver), solo se muestran sus dudas.
    private static Duda elegirDuda(Scanner sc, Usuario filtroUsuario) {
        List<Duda> dudas = DudaDAO.listarTodos();

        if (dudas.isEmpty()) {
            System.out.println("No hay dudas cargadas.");
            return null;
        }

        // Filtrar si es necesario (el admin puede ver todas si quisieramos, pero aquí filtramos por autor para simplificar)
        if (filtroUsuario != null && !filtroUsuario.getRol().name().equalsIgnoreCase("Admin")) {
            dudas.removeIf(d -> d.getUsuarioId() != filtroUsuario.getId());
            if (dudas.isEmpty()) {
                System.out.println("No tienes dudas creadas para resolver.");
                return null;
            }
        }

        System.out.println("Dudas disponibles:");
        for (Duda duda : dudas) {
            System.out.println("[" + duda.getId() + "] " + duda.getMensaje() + " (" + duda.getEstado() + ")");
        }

        System.out.print("Ingrese el ID de la duda: ");
        int dudaId = Integer.parseInt(sc.nextLine());

        return dudas.stream()
                .filter(d -> d.getId() == dudaId)
                .findFirst()
                .orElse(null);
    }
}
