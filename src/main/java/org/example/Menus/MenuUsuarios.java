package org.example.Menus;

import org.example.Consola;
import org.example.DAOS.CursoDAO;
import org.example.DAOS.SuspensionDAO;
import org.example.DAOS.UsuarioDAO;
import org.example.ENUMS.TipoRol;
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
            System.out.println("3. Cambiar rol de usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Suspender usuario");
            System.out.println("6. Reactivar usuario");
            System.out.println("7. Modificar usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = Consola.leerOpcion(sc);

            switch (opcion) {
                case 1:
                    agregarUsuario(sc);
                    break;
                case 2:
                    listarUsuarios(sc);
                    break;

                case 3:
                    modificarRol(sc);
                    break;

                case 4:
                    eliminarUsuario(sc);
                    break;

                case 5:
                    suspenderUsuario(sc);
                    break;

                case 6:
                    reactivarUsuario(sc);
                    break;

                case 7:
                    modificarUsuario(sc);
                    break;


                case 0:

                    break;
            }

        } while (opcion != 0);
    }
    private static void agregarUsuario(Scanner sc) {
        System.out.println("\n===== CREAR USUARIO =====");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Correo: ");
        String correo = sc.nextLine();

        System.out.print("Año de generación: ");
        int anioDeGeneracion;

        try {
            anioDeGeneracion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El año debe ser un número.");
            return;
        }

        System.out.println("Rol:");
        System.out.println("1. Estudiante");
        System.out.println("2. Administrador");
        System.out.print("Seleccione un rol: ");

        int opcionRol;
        try {
            opcionRol = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar una opción válida.");
            return;
        }

        TipoRol rol;
        switch (opcionRol) {
            case 1:
                rol = TipoRol.Estudiante;
                break;
            case 2:
                rol = TipoRol.Admin;
                break;
            default:
                System.out.println("Opción de rol inválida.");
                return;
        }

        System.out.print("Contraseña: ");
        String contrasenia = sc.nextLine();

        Usuario usuario = new Usuario(nombre, correo, anioDeGeneracion, rol, contrasenia);
        int usuarioId = UsuarioDAO.crear(usuario);
        if(usuarioId > 0){
            System.out.println("Usuario creado con éxito.");
        }
        else{
            System.out.println("Error al crear usuario.");
        }
    }

    private static void listarUsuarios(Scanner sc) {

        System.out.println("\n===== USUARIOS REGISTRADOS =====");

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (Usuario usuario : usuarios) {
            mostrarUsuario(usuario);
        }
    }
//    private static void filtrarUsuarios(Scanner sc) {
//
//        System.out.println("\n===== BUSCAR / FILTRAR USUARIOS =====");
//        System.out.println("1. Buscar por nombre");
//        System.out.println("2. Buscar por correo");
//        System.out.println("3. Filtrar por rol");
//        System.out.println("0. Volver");
//        System.out.print("Seleccione una opción: ");
//
//        int opcion = Consola.leerOpcion(sc);
//
//        List<Usuario> usuarios = UsuarioDAO.listarActivos();
//
//        if (usuarios.isEmpty()) {
//            System.out.println("No hay usuarios registrados.");
//            return;
//        }
//
//        boolean encontrado = false;
//
//        switch (opcion) {
//
//            case 1:
//                System.out.print("Ingrese el nombre a buscar: ");
//                String nombre = sc.nextLine().toLowerCase();
//
//                for (Usuario usuario : usuarios) {
//                    if (usuario.getNombre().toLowerCase().contains(nombre)) {
//                        mostrarUsuario(usuario);
//                        encontrado = true;
//                    }
//                }
//                break;
//
//            case 2:
//                System.out.print("Ingrese el correo a buscar: ");
//                String correo = sc.nextLine().toLowerCase();
//
//                for (Usuario usuario : usuarios) {
//                    if (usuario.getCorreo().toLowerCase().contains(correo)) {
//                        mostrarUsuario(usuario);
//                        encontrado = true;
//                    }
//                }
//                break;
//
//            case 3:
//                System.out.print("Ingrese el rol (Admin/Estudiante): ");
//                String rol = sc.nextLine();
//
//                for (Usuario usuario : usuarios) {
//                    if (usuario.getRol().toString().equalsIgnoreCase(rol)) {
//                        mostrarUsuario(usuario);
//                        encontrado = true;
//                    }
//                }
//                break;
//
//            case 0:
//                return;
//
//            default:
//                System.out.println("Opción inválida.");
//                return;
//        }
//        if (!encontrado) {
//            System.out.println("No se encontraron usuarios con ese criterio.");
//        }
//    }
    private static void mostrarUsuario(Usuario usuario) {
        System.out.println(
                "ID: " + usuario.getId() +
                        " | Nombre: " + usuario.getNombre() +
                        " | Correo: " + usuario.getCorreo() +
                        " | Año: " + usuario.getAnioDeGeneracion() +
                        " | Rol: " + usuario.getRol());
    }
    private static void modificarRol(Scanner sc) {

        System.out.println("\n===== MODIFICAR ROL DE USUARIO =====");

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (Usuario usuario : usuarios) {
            mostrarUsuario(usuario);
        }

        System.out.print("\nIngrese el ID del usuario a modificar: ");
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

        System.out.println("\nUsuario seleccionado: " + usuarioSeleccionado.getNombre() +
                " | Rol actual: " + usuarioSeleccionado.getRol());

        TipoRol nuevoRol;

        if (usuarioSeleccionado.getRol() == TipoRol.Admin) {
            System.out.println("1. Revocar rol de Administrador");
            System.out.print("Seleccione una opción: ");

            int opcion = Consola.leerOpcion(sc);

            if (opcion != 1) {
                System.out.println("Operación cancelada.");
                return;
            }

            nuevoRol = TipoRol.Estudiante;
        } else {
            System.out.println("1. Asignar rol de Administrador");
            System.out.print("Seleccione una opción: ");

            int opcion = Consola.leerOpcion(sc);

            if (opcion != 1) {
                System.out.println("Operación cancelada.");
                return;
            }

            nuevoRol = TipoRol.Admin;
        }

        System.out.print("¿Confirma la acción? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean actualizado = UsuarioDAO.cambiarRol(usuarioSeleccionado.getId(), nuevoRol);

        if (actualizado) {
            System.out.println("Rol actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el rol.");
        }
    }
    private static void modificarUsuario(Scanner sc) {

        System.out.println("\n===== MODIFICAR USUARIO =====");

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (Usuario usuario : usuarios) {
            mostrarUsuario(usuario);
        }

        System.out.print("\nIngrese el ID del usuario a modificar: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un número.");
            return;
        }

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

        System.out.println("\nUsuario seleccionado: " + usuarioSeleccionado.getNombre());

        System.out.print("¿Desea modificar el nombre? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            System.out.print("Nuevo nombre: ");
            usuarioSeleccionado.setNombre(sc.nextLine());
        }

        System.out.print("¿Desea modificar el correo? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            System.out.print("Nuevo correo: ");
            String correo = sc.nextLine();
            if (!correo.contains("@")) {
                System.out.println("El correo ingresado no es válido. Operación cancelada.");
                return;
            }
            usuarioSeleccionado.setCorreo(correo);
        }

        System.out.print("¿Desea modificar el año de generación? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            System.out.print("Nuevo año de generación: ");
            try {
                usuarioSeleccionado.setAnioDeGeneracion(Integer.parseInt(sc.nextLine()));
            } catch (NumberFormatException e) {
                System.out.println("El año debe ser un número. Operación cancelada.");
                return;
            }
        }

        System.out.print("¿Desea modificar la contraseña? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            System.out.print("Nueva contraseña: ");
            usuarioSeleccionado.setContrasenia(sc.nextLine());
        }

        System.out.print("¿Confirma los cambios? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean actualizado = UsuarioDAO.actualizar(usuarioSeleccionado);

        if (actualizado) {
            System.out.println("Datos del usuario modificados correctamente.");
        } else {
            System.out.println("No se pudo actualizar el usuario.");
        }
    }

    private static void suspenderUsuario(Scanner sc) {

        System.out.println("\n===== SUSPENDER USUARIO =====");

        List<Usuario> usuarios = UsuarioDAO.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (Usuario usuario : usuarios) {
            mostrarUsuario(usuario);
        }

        System.out.print("\nIngrese el ID del usuario a suspender: ");
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

        System.out.println("\nUsuario seleccionado: " + usuarioSeleccionado.getNombre());

        System.out.print("Registre el motivo de la suspensión: ");
        String motivo = sc.nextLine();

        System.out.print("Ingrese la duración de la suspensión en días: ");
        int dias = Integer.parseInt(sc.nextLine());

        System.out.print("¿Confirma la suspensión de la cuenta? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean suspendido = SuspensionDAO.suspender(usuarioSeleccionado.getId(), motivo, dias);

        if (suspendido) {
            System.out.println("Cuenta suspendida correctamente. Se notificó al usuario.");
        } else {
            System.out.println("No se pudo suspender la cuenta.");
        }
    }

    private static void reactivarUsuario(Scanner sc) {

        System.out.println("\n===== REACTIVAR USUARIO =====");

        List<Usuario> suspendidos = SuspensionDAO.listarSuspendidos();

        if (suspendidos.isEmpty()) {
            System.out.println("No hay usuarios suspendidos.");
            return;
        }

        System.out.println("\nUsuarios suspendidos:");

        for (Usuario usuario : suspendidos) {
            mostrarUsuario(usuario);
        }

        System.out.print("\nIngrese el ID del usuario a reactivar: ");
        int id = Integer.parseInt(sc.nextLine());

        Usuario usuarioSeleccionado = null;

        for (Usuario usuario : suspendidos) {
            if (usuario.getId() == id) {
                usuarioSeleccionado = usuario;
                break;
            }
        }

        if (usuarioSeleccionado == null) {
            System.out.println("No se encontró ningún usuario suspendido con ese ID.");
            return;
        }

        System.out.println("\nUsuario seleccionado: " + usuarioSeleccionado.getNombre());

        System.out.print("¿Confirma la reactivación de la cuenta? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean reactivado = SuspensionDAO.levantar(usuarioSeleccionado.getId());

        if (reactivado) {
            System.out.println("Cuenta reactivada correctamente. Se notificó al usuario.");
        } else {
            System.out.println("No se pudo reactivar la cuenta.");
        }
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