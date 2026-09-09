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
