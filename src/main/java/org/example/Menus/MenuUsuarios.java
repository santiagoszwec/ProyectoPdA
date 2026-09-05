package org.example.Menus;

import org.example.DAOS.CursoDAO;
import org.example.DAOS.UsuarioDAO;
import org.example.Modelos.Curso;
import org.example.Modelos.Usuario;

import java.util.List;
import java.util.Scanner;

public class MenuUsuarios {

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE USUARIOS =====");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Modificar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:

                    break;
                case 2:
                    listarUsuarios(sc);
                    break;

                case 3:

                    break;

                case 4:
                    eliminarUsuario(sc);
                    break;

                case 0:

                    break;
            }

        } while (opcion != 0);
    }

    private static void listarUsuarios(Scanner sc) {

        System.out.println("\n===== USUARIOS REGISTRADOS =====");

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.print("¿Desea buscar o filtrar usuarios? (S/N): ");
        String respuesta = sc.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            filtrarUsuarios(sc);
            return;
        }

        for (Usuario usuario : usuarios) {
            mostrarUsuario(usuario);
        }
    }

    private static void filtrarUsuarios(Scanner sc) {

        System.out.println("\n===== BUSCAR / FILTRAR USUARIOS =====");
        System.out.println("1. Buscar por nombre");
        System.out.println("2. Buscar por correo");
        System.out.println("3. Filtrar por rol");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        boolean encontrado = false;

        switch (opcion) {

            case 1:
                System.out.print("Ingrese el nombre a buscar: ");
                String nombre = sc.nextLine().toLowerCase();

                for (Usuario usuario : usuarios) {
                    if (usuario.getNombre().toLowerCase().contains(nombre)) {
                        mostrarUsuario(usuario);
                        encontrado = true;
                    }
                }
                break;

            case 2:
                System.out.print("Ingrese el correo a buscar: ");
                String correo = sc.nextLine().toLowerCase();

                for (Usuario usuario : usuarios) {
                    if (usuario.getCorreo().toLowerCase().contains(correo)) {
                        mostrarUsuario(usuario);
                        encontrado = true;
                    }
                }
                break;

            case 3:
                System.out.print("Ingrese el rol (Admin/Estudiante): ");
                String rol = sc.nextLine();

                for (Usuario usuario : usuarios) {
                    if (usuario.getRol().toString().equalsIgnoreCase(rol)) {
                        mostrarUsuario(usuario);
                        encontrado = true;
                    }
                }
                break;

            case 0:
                return;

            default:
                System.out.println("Opción inválida.");
                return;
        }
        if (!encontrado) {
            System.out.println("No se encontraron usuarios con ese criterio.");
        }
    }
    private static void mostrarUsuario(Usuario usuario) {
        System.out.println(
                "ID: " + usuario.getId() +
                        " | Nombre: " + usuario.getNombre() +
                        " | Correo: " + usuario.getCorreo() +
                        " | Año: " + usuario.getAnioDeGeneracion() +
                        " | Rol: " + usuario.getRol());
    }
    private static void eliminarUsuario(Scanner sc) {

        System.out.println("\n===== ELIMINAR USUARIO =====");

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("\nUsuarios disponibles:");

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }

        System.out.print("\nIngrese el ID del usuario a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        Usuario usuarioSeleccionado = null;

        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuarioSeleccionado = usuario;
                break;
            }
        }

        if (usuarioSeleccionado == null) {
            System.out.println("No se encontró ningún usuario con ese ID.");
            return;
        }

        System.out.println("\nUsaurio seleccionado: " + usuarioSeleccionado.getNombre());

        System.out.print("¿Está seguro de que desea eliminar este usuario? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean eliminado = UsuarioDAO.desactivar(id);

        if (eliminado) {
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el usuario.");
        }
    }
}