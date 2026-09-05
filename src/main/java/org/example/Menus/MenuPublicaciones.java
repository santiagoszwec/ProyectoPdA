package org.example.Menus;

import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoMaterial;

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

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 0:
                    break;

                default:
                    break;
            }

        } while (opcion != 0);
    }



    public static void crearPublicacion(Scanner sc){

        System.out.println("\n===== CREAR PUBLICACION =====");

        String mensaje;
        do{
            System.out.println("Ingrese mensaje: ");
            mensaje = sc.nextLine();

            if(mensaje.isBlank()){
                System.out.println("No se ha ingresado ningun mensaje. Ingrese S para ingresar o N para cancelar: ");
                String respuesta = sc.nextLine();
                if(respuesta.equalsIgnoreCase("N")){
                    return;
                }
            }

        }while(mensaje.isBlank());

        System.out.println("Desea adjuntar una imagen? S/N");
        String respuesta = sc.nextLine();
        String imagenURL = null;
        if(respuesta.equalsIgnoreCase("S")){
            System.out.println("Ingrese URL de la imagen: ");
            imagenURL = sc.nextLine();
        }

        System.out.println("La publicacion corresponde a un material? S/N ");
        respuesta = sc.nextLine();

        String materialURL = null;
        TipoMaterial tipoMaterial = null;
        TipoArchivo tipoArchivo = null;

        if (respuesta.equalsIgnoreCase("S")) {
            do {
                System.out.println("Ingrese el tipo de material (Apuntes/Ejercicio/Libro/Video): ");
                String tipoMaterialTemp = sc.nextLine();
                try {
                    tipoMaterial= TipoMaterial.valueOf(tipoMaterialTemp);
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo de material inválido, intente de nuevo.");
                }
            } while (tipoMaterial == null);

            do {
                System.out.println("Ingrese el tipo de archivo (JPG/PDF/PNG): ");
                String tipoArchivoTemp= sc.nextLine();
                try {
                    tipoArchivo = TipoArchivo.valueOf(tipoArchivoTemp);
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo de archivo inválido, intente de nuevo.");
                }
            } while (tipoArchivo == null);

        }
        else{

        }
    }
}
