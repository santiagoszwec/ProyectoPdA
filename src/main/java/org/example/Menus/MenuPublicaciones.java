package org.example.Menus;

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

        //el sistema valida que el mensaje no este vacio
        System.out.println("Ingrese mensaje: ");
        String mensaje = sc.nextLine();

        //sistema pregunta si quiere ingresar una imagen, es opcional
        System.out.println("Ingrese URL de la imagen: ");
        String imagenURL = sc.nextLine();

        System.out.println("La publicacion corresponde a un material? S/N ");
        String respuesta = sc.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            System.out.println("Ingrese URL del archivo: ");
            String materialURL = sc.nextLine();
            //tipo material ENUM
            //tipo Archivo ENUM

        }
    }
}
