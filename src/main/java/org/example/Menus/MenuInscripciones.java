package org.example.Menus;

import org.example.Consola;
import org.example.DAOS.CursoDAO;
import org.example.DAOS.InscripcionDAO;
import org.example.DAOS.UsuarioDAO;
import org.example.ENUMS.TipoEstado;
import org.example.Modelos.Curso;
import org.example.Modelos.Inscripcion;
import org.example.Modelos.Usuario;

import java.util.List;
import java.util.Scanner;

public class MenuInscripciones {

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE INSCRIPCIONES =====");
            System.out.println("1. Listar inscripciones");
            System.out.println("2. Cambiar estado de una inscripción");
            System.out.println("3. Eliminar inscripción");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = Consola.leerOpcion(sc);

            switch (opcion) {

                case 1:
                    listarInscripciones(sc);
                    break;

                case 2:
                    cambiarEstado(sc);
                    break;

                case 3:
                    eliminarInscripcion(sc);
                    break;

                default:
                    break;
            }

        } while (opcion != 0);
    }

    private static void listarInscripciones(Scanner sc) {

        System.out.println("\n===== LISTAR INSCRIPCIONES =====");

        int filtro;
        do {
            System.out.println("Filtrar por: ");
            System.out.println("1. Todas");
            System.out.println("2. Usuario");
            System.out.println("3. Curso");
            System.out.println("4. Estado");
            System.out.print("Seleccione una opción: ");
            filtro = Consola.leerOpcion(sc);

            if (filtro < 1 || filtro > 4) {
                System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (filtro < 1 || filtro > 4);

        List<Inscripcion> inscripciones;

        switch (filtro) {
            case 2: {
                Integer usuarioId = pedirUsuario(sc);
                if (usuarioId == null) {
                    return;
                }
                inscripciones = InscripcionDAO.listarPorUsuario(usuarioId);
            }
            break;
            case 3: {
                Integer cursoId = pedirCurso(sc);
                if (cursoId == null) {
                    return;
                }
                inscripciones = InscripcionDAO.listarPorCurso(cursoId);
            }
            break;
            case 4: {
                TipoEstado estado = pedirEstado(sc);
                if (estado == null) {
                    return;
                }
                inscripciones = InscripcionDAO.listarPorEstado(estado);
            }
            break;
            default:
                inscripciones = InscripcionDAO.listarTodas();
                break;
        }

        mostrarInscripciones(inscripciones);
    }

    private static void cambiarEstado(Scanner sc) {

        System.out.println("\n===== CAMBIAR ESTADO DE INSCRIPCIÓN =====");

        List<Inscripcion> inscripciones = InscripcionDAO.listarTodas();

        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return;
        }

        mostrarInscripciones(inscripciones);

        System.out.print("\nIngrese el ID de la inscripción: ");
        int inscripcionId;
        try {
            inscripcionId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Inscripcion seleccionada = null;
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getId() == inscripcionId) {
                seleccionada = inscripcion;
                break;
            }
        }

        if (seleccionada == null) {
            System.out.println("No se encontró ninguna inscripción con ese ID.");
            return;
        }

        TipoEstado nuevoEstado = siguienteEstado(seleccionada.getEstado());

        if (nuevoEstado == null) {
            System.out.println("La inscripción ya tiene estado " + seleccionada.getEstado() +
                    " y no puede cambiarse a otro estado.");
            return;
        }

        System.out.println("\nInscripción seleccionada: " + describir(seleccionada));
        System.out.println("Estado actual: " + seleccionada.getEstado() +
                " | Estado nuevo: " + nuevoEstado);

        System.out.print("¿Desea confirmar el cambio? S/N: ");
        if (!sc.nextLine().equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean actualizado = InscripcionDAO.actualizarEstado(seleccionada.getId(), nuevoEstado);

        if (actualizado) {
            System.out.println("Estado de la inscripción actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el estado de la inscripción.");
        }
    }

    private static void eliminarInscripcion(Scanner sc) {

        System.out.println("\n===== ELIMINAR INSCRIPCIÓN =====");

        List<Inscripcion> inscripciones = InscripcionDAO.listarTodas();

        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return;
        }

        mostrarInscripciones(inscripciones);

        System.out.print("\nIngrese el ID de la inscripción a eliminar: ");
        int inscripcionId;
        try {
            inscripcionId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Inscripcion seleccionada = null;
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getId() == inscripcionId) {
                seleccionada = inscripcion;
                break;
            }
        }

        if (seleccionada == null) {
            System.out.println("No se encontró ninguna inscripción con ese ID.");
            return;
        }

        System.out.println("\nInscripción seleccionada: " + describir(seleccionada));

        System.out.print("¿Está seguro de que desea eliminar esta inscripción? (S/N): ");
        if (!sc.nextLine().equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        try {
            boolean eliminado = InscripcionDAO.eliminar(seleccionada.getId());
            if (eliminado) {
                System.out.println("Inscripción eliminada correctamente.");
            } else {
                System.out.println("No se pudo eliminar la inscripción.");
            }
        } catch (RuntimeException e) {
            System.out.println("No se pudo eliminar la inscripción porque tiene registros asociados.");
        }
    }

    private static void mostrarInscripciones(List<Inscripcion> inscripciones) {

        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones que coincidan con el criterio seleccionado.");
            return;
        }

        List<Usuario> usuarios = UsuarioDAO.listarTodos();
        List<Curso> cursos = CursoDAO.listarTodos();

        System.out.println("\nInscripciones:");

        for (Inscripcion inscripcion : inscripciones) {
            String nombreUsuario = usuarios.stream()
                    .filter(u -> u.getId() == inscripcion.getUsuarioId())
                    .map(Usuario::getNombre)
                    .findFirst()
                    .orElse("ID " + inscripcion.getUsuarioId());

            String nombreCurso = cursos.stream()
                    .filter(c -> c.getId() == inscripcion.getCursoId())
                    .map(Curso::getNombre)
                    .findFirst()
                    .orElse("ID " + inscripcion.getCursoId());

            System.out.println(
                    "ID: " + inscripcion.getId() +
                            " | Usuario: " + nombreUsuario +
                            " | Curso: " + nombreCurso +
                            " | Estado: " + inscripcion.getEstado());
        }
    }

    private static Integer pedirUsuario(Scanner sc) {

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios activos registrados.");
            return null;
        }

        System.out.println("\nUsuarios disponibles:");

        for (Usuario usuario : usuarios) {
            System.out.println(
                    "ID: " + usuario.getId() +
                            " | Nombre: " + usuario.getNombre() +
                            " | Correo: " + usuario.getCorreo());
        }

        int usuarioId = -1;
        do {
            System.out.print("\nIngrese el ID del usuario: ");
            try {
                usuarioId = Integer.parseInt(sc.nextLine().trim());

                if (!InscripcionDAO.usuarioExisteYActivo(usuarioId)) {
                    System.out.println("El usuario no existe o está inactivo. Intente de nuevo.");
                    usuarioId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido, intente de nuevo.");
                usuarioId = -1;
            }
        } while (usuarioId == -1);

        return usuarioId;
    }

    private static Integer pedirCurso(Scanner sc) {

        List<Curso> cursos = CursoDAO.listarTodos();

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos activos registrados.");
            return null;
        }

        System.out.println("\nCursos disponibles:");

        for (Curso curso : cursos) {
            System.out.println(
                    "ID: " + curso.getId() +
                            " | Nombre: " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio());
        }

        int cursoId = -1;
        do {
            System.out.print("\nIngrese el ID del curso: ");
            try {
                cursoId = Integer.parseInt(sc.nextLine().trim());

                if (!InscripcionDAO.cursoExisteYActivo(cursoId)) {
                    System.out.println("El curso no existe o está inactivo. Intente de nuevo.");
                    cursoId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido, intente de nuevo.");
                cursoId = -1;
            }
        } while (cursoId == -1);

        return cursoId;
    }

    private static TipoEstado pedirEstado(Scanner sc) {

        int opcion;
        do {
            System.out.println("\nEstados disponibles:");
            System.out.println("1. " + TipoEstado.Cursando);
            System.out.println("2. " + TipoEstado.Cursada);
            System.out.println("3. " + TipoEstado.Aprobada);
            System.out.print("Seleccione el estado: ");
            opcion = Consola.leerOpcion(sc);

            if (opcion < 1 || opcion > 3) {
                System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (opcion < 1 || opcion > 3);

        switch (opcion) {
            case 1:
                return TipoEstado.Cursando;
            case 2:
                return TipoEstado.Cursada;
            default:
                return TipoEstado.Aprobada;
        }
    }

    private static TipoEstado siguienteEstado(TipoEstado actual) {
        switch (actual) {
            case Cursando:
                return TipoEstado.Cursada;
            case Cursada:
                return TipoEstado.Aprobada;
            default:
                return null;
        }
    }

    private static String describir(Inscripcion inscripcion) {
        String nombreUsuario = "ID " + inscripcion.getUsuarioId();
        String nombreCurso = "ID " + inscripcion.getCursoId();

        for (Usuario usuario : UsuarioDAO.listarTodos()) {
            if (usuario.getId() == inscripcion.getUsuarioId()) {
                nombreUsuario = usuario.getNombre();
                break;
            }
        }

        for (Curso curso : CursoDAO.listarTodos()) {
            if (curso.getId() == inscripcion.getCursoId()) {
                nombreCurso = curso.getNombre();
                break;
            }
        }

        return "Usuario: " + nombreUsuario + " | Curso: " + nombreCurso +
                " | Estado: " + inscripcion.getEstado();
    }
}