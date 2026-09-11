package org.example.Menus;

import org.example.DAOS.ComentarioDAO;
import org.example.DAOS.DudaDAO;
import org.example.DAOS.NotificacionDAO;
import org.example.ENUMS.EstadoDuda;
import org.example.ENUMS.TipoNotificacion;
import org.example.Modelos.Comentario;
import org.example.Modelos.Duda;
import org.example.Modelos.Notificacion;
import org.example.Modelos.Usuario;
import org.example.DAOS.PublicacionDAO;
import org.example.Modelos.Publicacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuDudas {

    public static void mostrar(Scanner sc, Usuario usuarioActual) {

        int opcion;

        do {
            System.out.println("\n===== DUDAS Y COMENTARIOS =====");
            System.out.println("1. Responder publicacion");
            System.out.println("2. Responder comentario");
            System.out.println("3. Marcar duda como resuelta (para autores)");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    responderDuda(sc, usuarioActual);
                    break;

                case 2:
                    comentarRespuesta(sc, usuarioActual);
                    break;

                case 3:
                    resolverDuda(sc, usuarioActual);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void responderDuda(Scanner sc, Usuario usuarioActual) {
        List<Publicacion> publicaciones = PublicacionDAO.listarActivas();
        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones cargadas.");
            return;
        }

        System.out.println("Publicaciones disponibles:");
        for (Publicacion publicacion : publicaciones) {
            System.out.println("[" + publicacion.getId() + "] " + publicacion.getMensaje());
        }

        System.out.print("Ingrese el ID de la publicación a responder: ");
        int publicacionId = Integer.parseInt(sc.nextLine());

        Publicacion publicacion = publicaciones.stream()
                .filter(p -> p.getId() == publicacionId)
                .findFirst()
                .orElse(null);

        if (publicacion == null) {
            System.out.println("Esa publicación no existe.");
            return;
        }

        if (PublicacionDAO.esDuda(publicacion.getId())) {
            Duda duda = DudaDAO.buscarPorId(publicacion.getId());
            if (duda != null && duda.getEstado() == EstadoDuda.Resuelta) {
                System.out.println("Esta duda ya está resuelta. No se aceptan más respuestas principales.");
                return;
            }
        }

        System.out.print("Escriba su respuesta: ");
        String mensaje = sc.nextLine();

        System.out.print("URL de imagen (opcional, Enter para omitir): ");
        String imagenUrl = sc.nextLine();
        if (imagenUrl.isBlank()) {
            imagenUrl = null;
        }

        Comentario respuesta = new Comentario(mensaje, imagenUrl, LocalDate.now(), usuarioActual.getId(), publicacion.getId(), null);
        boolean creada = ComentarioDAO.crear(respuesta);
        System.out.println(creada ? "Respuesta publicada con éxito." : "No se pudo publicar la respuesta.");
    }

    private static void comentarRespuesta(Scanner sc, Usuario usuarioActual) {
        List<Publicacion> publicaciones = PublicacionDAO.listarActivas();
        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones cargadas.");
            return;
        }

        System.out.println("Publicaciones disponibles:");
        for (Publicacion publicacion : publicaciones) {
            System.out.println("[" + publicacion.getId() + "] " + publicacion.getMensaje());
        }

        System.out.print("Ingrese el ID de la publicación: ");
        int publicacionId = Integer.parseInt(sc.nextLine());

        Publicacion publicacion = publicaciones.stream()
                .filter(p -> p.getId() == publicacionId)
                .findFirst()
                .orElse(null);

        if (publicacion == null) {
            System.out.println("Esa publicación no existe.");
            return;
        }

        List<Comentario> comentarios = ComentarioDAO.listarPorPublicacionTodos(publicacion.getId());
        if (comentarios.isEmpty()) {
            System.out.println("Esta publicación todavía no tiene comentarios. No hay nada que responder.");
            return;
        }

        System.out.println("Comentarios disponibles:");
        for (Comentario comentario : comentarios) {
            String destacado = comentario.isDestacado() ? " [DESTACADA]" : "";
            String anidado = comentario.getComentarioPadreId() != null
                    ? " (respuesta a " + comentario.getComentarioPadreId() + ")"
                    : "";
            System.out.println("[" + comentario.getId() + "] " + comentario.getMensaje() + destacado + anidado);
        }

        System.out.print("Ingrese el ID del comentario que quiere responder: ");
        int comentarioId = Integer.parseInt(sc.nextLine());

        Comentario comentarioElegido = comentarios.stream()
                .filter(c -> c.getId() == comentarioId)
                .findFirst()
                .orElse(null);

        if (comentarioElegido == null) {
            System.out.println("Ese comentario no existe.");
            return;
        }

        System.out.print("Escriba su respuesta: ");
        String mensaje = sc.nextLine();

        System.out.print("URL de imagen (opcional, Enter para omitir): ");
        String imagenUrl = sc.nextLine();
        if (imagenUrl.isBlank()) {
            imagenUrl = null;
        }

        Comentario nuevo = new Comentario(mensaje, imagenUrl, LocalDate.now(), usuarioActual.getId(), publicacion.getId(), comentarioElegido.getId());
        boolean creado = ComentarioDAO.crear(nuevo);

        if (!creado) {
            System.out.println("No se pudo publicar la respuesta.");
            return;
        }

        System.out.println("Respuesta publicada con éxito.");
        mostrarComentariosAnidados(comentarioElegido.getId());
    }

    private static void resolverDuda(Scanner sc, Usuario usuarioActual) {

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


        boolean exito = DudaDAO.marcarResueltaYDestacarRespuesta(duda.getId(), respuestaElegida.getId());

        if (exito) {
            System.out.println("La duda fue marcada como Resuelta y la respuesta fue destacada.");
            

            Notificacion notificacion = new Notificacion(
                    LocalDate.now(),
                    TipoNotificacion.Respuesta,
                    "Tu respuesta a la duda '" + duda.getMensaje() + "' ha sido marcada como la más útil.",
                    respuestaElegida.getUsuarioId(),
                    duda.getId()
            );
            NotificacionDAO.crear(notificacion);
            System.out.println("Se ha notificado al autor de la respuesta.");
            
        } else {
            System.out.println("Ocurrió un error al intentar resolver la duda.");
        }
    }

    private static void mostrarComentariosAnidados(int comentarioPadreId) {
        List<Comentario> anidados = ComentarioDAO.listarRespuestas(comentarioPadreId);

        System.out.println("Comentarios bajo esta respuesta:");
        for (Comentario anidado : anidados) {
            System.out.println("  - " + anidado.getMensaje());
        }
    }


    private static Duda elegirDuda(Scanner sc, Usuario filtroUsuario) {
        List<Duda> dudas = DudaDAO.listarTodos();

        if (dudas.isEmpty()) {
            System.out.println("No hay dudas cargadas.");
            return null;
        }

        if (filtroUsuario != null) {
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
