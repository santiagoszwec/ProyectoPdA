package org.example.Menus;
import org.example.DAOS.PublicacionDAO;
import org.example.Modelos.Publicacion;
import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoMaterial;
import java.util.List;
import java.util.Scanner;
import org.example.DAOS.DudaDAO;
import org.example.DAOS.MensajeDAO;
import org.example.DAOS.MaterialDAO;
import org.example.ENUMS.EstadoDuda;
import org.example.ENUMS.TipoCategoria;
import org.example.ENUMS.TipoMaterial;
import org.example.ENUMS.TipoArchivo;
import org.example.Modelos.Duda;
import org.example.Modelos.Mensaje;
import org.example.Modelos.Material;
import org.example.Modelos.Usuario;
import java.time.LocalDate;

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
                    menuListarPublicaciones(sc);
                    break;

                case 3:

                    break;

                case 4:
                    darDeBajaPublicacion(sc);
                    break;

                case 0:
                    break;

                default:
                    break;
            }

        } while (opcion != 0);
    }

    private static void menuListarPublicaciones(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== LISTAR PUBLICACIONES =====");
            System.out.println("1. Todas las publicaciones");
            System.out.println("2. Mensajes");
            System.out.println("3. Dudas");
            System.out.println("4. Materiales");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    mostrarPublicaciones(PublicacionDAO.listarActivas());
                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }


    private static void mostrarPublicaciones(List<Publicacion> publicaciones) {

        if (publicaciones.isEmpty()) {
            System.out.println("\nNo se encontraron publicaciones.");
            return;
        }

        System.out.println("\n===== PUBLICACIONES =====");

        for (Publicacion publicacion : publicaciones) {
            System.out.println(publicacion);
        }
    }

    private static void menuMensajes(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== MENSAJES =====");
            System.out.println("1. Mostrar todos los mensajes");
            System.out.println("2. Filtrar por categoría");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    mostrarPublicaciones(PublicacionDAO.listarMensajes());
                    break;

                case 2:
                    filtrarMensajesPorCategoria(sc);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }


    private static void filtrarMensajesPorCategoria(Scanner sc) {

        System.out.println("\n===== FILTRAR MENSAJES =====");

        System.out.println("1. Ejercicio");
        System.out.println("2. Examen");
        System.out.println("3. Reunión");
        System.out.print("Seleccione una categoría: ");

        int opcion = Integer.parseInt(sc.nextLine());
        String categoria;

        switch (opcion) {
            case 1:
                categoria = "Ejercicio";
                break;

            case 2:
                categoria = "Examen";
                break;

            case 3:
                categoria = "Reunion";
                break;

            default:
                System.out.println("Categoría inválida.");
                return;
        }
        mostrarPublicaciones(PublicacionDAO.filtrarMensajesPorCategoria(categoria));
    }

    private static void menuDudas(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== DUDAS =====");
            System.out.println("1. Mostrar todas las dudas");
            System.out.println("2. Filtrar por categoría");
            System.out.println("3. Filtrar por estado");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    mostrarPublicaciones(PublicacionDAO.listarDudas());
                    break;

                case 2:
                    filtrarDudasPorCategoria(sc);
                    break;

                case 3:
                    filtrarDudasPorEstado(sc);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void filtrarDudasPorCategoria(Scanner sc) {

        System.out.println("\n===== FILTRAR DUDAS POR CATEGORÍA =====");
        System.out.println("1. Ejercicio");
        System.out.println("2. Examen");
        System.out.println("3. Reunión");
        System.out.print("Seleccione una categoría: ");

        int opcion = Integer.parseInt(sc.nextLine());
        String categoria;

        switch (opcion) {
            case 1:
                categoria = "Ejercicio";
                break;

            case 2:
                categoria = "Examen";
                break;

            case 3:
                categoria = "Reunion";
                break;

            default:
                System.out.println("Categoría inválida.");
                return;
        }

        mostrarPublicaciones(PublicacionDAO.filtrarDudasPorCategoria(categoria));
    }


    private static void filtrarDudasPorEstado(Scanner sc) {

        System.out.println("\n===== FILTRAR DUDAS POR ESTADO =====");
        System.out.println("1. Abierta");
        System.out.println("2. Resuelta");
        System.out.print("Seleccione un estado: ");

        int opcion = Integer.parseInt(sc.nextLine());
        String estado;

        switch (opcion) {
            case 1:
                estado = "Abierta";
                break;
            case 2:
                estado = "Resuelta";
                break;
            default:
                System.out.println("Estado inválido.");
                return;
        }

        mostrarPublicaciones(PublicacionDAO.filtrarDudasPorEstado(estado));
    }

    private static void menuMateriales(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== MATERIALES =====");
            System.out.println("1. Mostrar todos los materiales");
            System.out.println("2. Filtrar por tipo de material");
            System.out.println("3. Filtrar por tipo de archivo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    mostrarPublicaciones(PublicacionDAO.listarMateriales());
                    break;

                case 2:
                    filtrarMaterialesPorTipo(sc);
                    break;

                case 3:
                    filtrarMaterialesPorArchivo(sc);
                    break;

                case 0:
                    break;
            }

        } while (opcion != 0);
    }


    private static void filtrarMaterialesPorTipo(Scanner sc) {

        System.out.println("\n===== FILTRAR POR TIPO DE MATERIAL =====");
        System.out.println("1. Apuntes");
        System.out.println("2. Ejercicio");
        System.out.println("3. Libro");
        System.out.println("4. Video");
        System.out.print("Seleccione un tipo: ");

        int opcion = Integer.parseInt(sc.nextLine());
        String tipoMaterial;

        switch (opcion) {
            case 1:
                tipoMaterial = "Apuntes";
                break;

            case 2:
                tipoMaterial = "Ejercicio";
                break;

            case 3:
                tipoMaterial = "Libro";
                break;

            case 4:
                tipoMaterial = "Video";
                break;

            default:
                System.out.println("Tipo de material inválido.");
                return;
        }

        mostrarPublicaciones(PublicacionDAO.filtrarMaterialesPorTipo(tipoMaterial));
    }


    private static void filtrarMaterialesPorArchivo(Scanner sc) {

        System.out.println("\n===== FILTRAR POR TIPO DE ARCHIVO =====");
        System.out.println("1. PDF");
        System.out.println("2. JPG");
        System.out.println("3. PNG");
        System.out.print("Seleccione un tipo de archivo: ");

        int opcion = Integer.parseInt(sc.nextLine());
        String tipoArchivo;

        switch (opcion) {
            case 1:
                tipoArchivo = "PDF";
                break;

            case 2:
                tipoArchivo = "JPG";
                break;

            case 3:
                tipoArchivo = "PNG";
                break;

            default:
                System.out.println("Tipo de archivo inválido.");
                return;
        }
        mostrarPublicaciones(PublicacionDAO.filtrarMaterialesPorArchivo(tipoArchivo));
    }

    private static void darDeBajaPublicacion(Scanner sc) {

        List<Publicacion> publicaciones = PublicacionDAO.listarActivas();

        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones activas para dar de baja.");
            return;
        }

        System.out.println("\n===== PUBLICACIONES ACTIVAS =====");

        for (Publicacion publicacion : publicaciones) {
            System.out.println("[" + publicacion.getId() + "] " + publicacion.getMensaje());
        }

        System.out.print("\nIngrese el ID de la publicación a dar de baja: ");
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

        System.out.print("¿Confirma que desea dar de baja esta publicación? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        System.out.print("Ingrese el motivo de la baja: ");
        String motivo = sc.nextLine();

        boolean dadoDeBaja = PublicacionDAO.darDeBaja(id, motivo);

        System.out.println(dadoDeBaja
                ? "Publicación dada de baja correctamente."
                : "No se pudo dar de baja la publicación.");
    }

    private static void crearPublicacion(Scanner sc, Usuario usuarioActual) {

        System.out.println("\n===== CREAR PUBLICACION =====");

        String mensaje;
        do {
            System.out.print("Ingrese mensaje: ");
            mensaje = sc.nextLine();

            if (mensaje.isBlank()) {
                System.out.print("No se ha ingresado ningún mensaje. Ingrese S para ingresar o N para cancelar: ");
                String respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("N")) {
                    return;
                }
            }
        } while (mensaje.isBlank());

        System.out.print("¿Desea adjuntar una imagen? S/N: ");
        String respuestaImagen = sc.nextLine();
        String imagenUrl = null;
        if (respuestaImagen.equalsIgnoreCase("S")) {
            System.out.print("Ingrese URL de la imagen: ");
            imagenUrl = sc.nextLine();
        }

        int tipoPublicacion;
        do {
            System.out.println("¿Qué tipo de publicación es?");
            System.out.println("1. Duda");
            System.out.println("2. Mensaje");
            System.out.println("3. Material");
            System.out.print("Seleccione una opción: ");
            tipoPublicacion = Integer.parseInt(sc.nextLine());

            if (tipoPublicacion < 1 || tipoPublicacion > 3) {
                System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (tipoPublicacion < 1 || tipoPublicacion > 3);

        boolean creada;

        switch (tipoPublicacion) {

            case 1: {
                TipoCategoria categoria = pedirCategoria(sc);

                Duda duda = new Duda(EstadoDuda.Abierta, categoria);
                duda.setMensaje(mensaje);
                duda.setImagenUrl(imagenUrl);
                duda.setFechaPublicacion(LocalDate.now());
                duda.setUsuarioId(usuarioActual.getId());

                creada = DudaDAO.crear(duda);
                break;
            }

            case 2: {
                TipoCategoria categoria = pedirCategoria(sc);

                Mensaje publicacionMensaje = new Mensaje(categoria);
                publicacionMensaje.setMensaje(mensaje);
                publicacionMensaje.setImagenUrl(imagenUrl);
                publicacionMensaje.setFechaPublicacion(LocalDate.now());
                publicacionMensaje.setUsuarioId(usuarioActual.getId());

                creada = MensajeDAO.crear(publicacionMensaje);
                break;
            }

            case 3: {
                System.out.print("Ingrese URL del archivo: ");
                String archivoUrl = sc.nextLine();

                TipoMaterial tipoMaterial = null;
                do {
                    System.out.print("Ingrese el tipo de material (Apuntes/Ejercicio/Libro/Video): ");
                    try {
                        tipoMaterial = TipoMaterial.valueOf(sc.nextLine().trim());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de material inválido, intente de nuevo.");
                    }
                } while (tipoMaterial == null);

                TipoArchivo tipoArchivo = null;
                do {
                    System.out.print("Ingrese el tipo de archivo (JPG/PDF/PNG): ");
                    try {
                        tipoArchivo = TipoArchivo.valueOf(sc.nextLine().trim());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de archivo inválido, intente de nuevo.");
                    }
                } while (tipoArchivo == null);

                System.out.print("Ingrese el tema: ");
                String tema = sc.nextLine();

                Material material = new Material(archivoUrl, tipoMaterial, tipoArchivo, tema);
                material.setMensaje(mensaje);
                material.setImagenUrl(imagenUrl);
                material.setFechaPublicacion(LocalDate.now());
                material.setUsuarioId(usuarioActual.getId());

                creada = MaterialDAO.crear(material);
                break;
            }

            default:
                creada = false;
        }

        System.out.println(creada ? "Publicación creada correctamente." : "No se pudo crear la publicación.");
    }

    public static TipoCategoria pedirCategoria(Scanner sc){
        TipoCategoria categoria = null;
        do {
            System.out.print("Ingrese la categoría (Ejercicio/Examen/Reunion): ");
            try {
                categoria = TipoCategoria.valueOf(sc.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println("Categoría inválida, intente de nuevo.");
            }
        } while (categoria == null);
        return categoria;

    }




}
