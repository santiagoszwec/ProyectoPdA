package org.example.Menus;

import org.example.Consola;
import org.example.DAOS.PublicacionDAO;
import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoMaterial;
import org.example.Modelos.Publicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuMateriales {

    private static final TipoMaterial[] TIPOS_MATERIAL = {
            TipoMaterial.Apuntes,
            TipoMaterial.Ejercicio,
            TipoMaterial.Libro,
            TipoMaterial.Video
    };

    private static final TipoArchivo[] TIPOS_ARCHIVO = {
            TipoArchivo.JPG,
            TipoArchivo.PDF,
            TipoArchivo.PNG
    };

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== MATERIAL DE ESTUDIO =====");
            System.out.println("1. Ver todos los materiales");
            System.out.println("2. Filtrar por etiquetas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Consola.leerOpcion(sc);

            switch (opcion) {
                case 1:
                    MenuPublicaciones.mostrarPublicaciones(PublicacionDAO.listarMateriales());
                    break;
                case 2:
                    filtrarPorEtiquetas(sc);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void filtrarPorEtiquetas(Scanner sc) {

        System.out.println("\n===== FILTRAR MATERIAL POR ETIQUETAS =====");

        System.out.println("\nSeleccione los TIPOS DE MATERIAL (etiquetas).");
        System.out.println("Ingrese los números separados por coma. Ejemplo: 1,3");
        System.out.println("(Enter sin escribir = mostrar todos los tipos)");
        for (int i = 0; i < TIPOS_MATERIAL.length; i++) {
            System.out.println("  " + (i + 1) + ". " + TIPOS_MATERIAL[i]);
        }
        System.out.print("Su selección: ");
        List<TipoMaterial> tiposMaterial = leerTipoMaterial(sc);

        System.out.println("\nSeleccione los TIPOS DE ARCHIVO (etiquetas).");
        System.out.println("Ingrese los números separados por coma. Ejemplo: 2,3");
        System.out.println("(Enter sin escribir = mostrar todos los archivos)");
        for (int i = 0; i < TIPOS_ARCHIVO.length; i++) {
            System.out.println("  " + (i + 1) + ". " + TIPOS_ARCHIVO[i]);
        }
        System.out.print("Su selección: ");
        List<TipoArchivo> tiposArchivo = leerTipoArchivo(sc);

        List<Publicacion> materiales = PublicacionDAO.filtrarMaterialesPorEtiquetas(tiposMaterial, tiposArchivo);

        if (materiales.isEmpty()) {
            System.out.println("\nNo se encontraron materiales con las etiquetas seleccionadas.");
        } else {
            System.out.println("\n===== MATERIALES CON LAS ETIQUETAS SELECCIONADAS =====");
            MenuPublicaciones.mostrarPublicaciones(materiales);
        }
    }

    private static List<TipoMaterial> leerTipoMaterial(Scanner sc) {
        List<TipoMaterial> seleccion = new ArrayList<>();
        boolean valida = false;

        while (!valida) {
            String linea = sc.nextLine().trim();

            if (linea.isEmpty()) {
                return seleccion;
            }

            seleccion = new ArrayList<>();
            valida = true;

            for (String parte : linea.split(",")) {
                try {
                    int numero = Integer.parseInt(parte.trim());
                    if (numero >= 1 && numero <= TIPOS_MATERIAL.length) {
                        seleccion.add(TIPOS_MATERIAL[numero - 1]);
                    } else {
                        valida = false;
                    }
                } catch (NumberFormatException e) {
                    valida = false;
                }
            }

            if (!valida) {
                System.out.print("Selección inválida. Ingrese números separados por coma (1-" + TIPOS_MATERIAL.length + "): ");
            }
        }

        return seleccion;
    }

    private static List<TipoArchivo> leerTipoArchivo(Scanner sc) {
        List<TipoArchivo> seleccion = new ArrayList<>();
        boolean valida = false;

        while (!valida) {
            String linea = sc.nextLine().trim();

            if (linea.isEmpty()) {
                return seleccion;
            }

            seleccion = new ArrayList<>();
            valida = true;

            for (String parte : linea.split(",")) {
                try {
                    int numero = Integer.parseInt(parte.trim());
                    if (numero >= 1 && numero <= TIPOS_ARCHIVO.length) {
                        seleccion.add(TIPOS_ARCHIVO[numero - 1]);
                    } else {
                        valida = false;
                    }
                } catch (NumberFormatException e) {
                    valida = false;
                }
            }

            if (!valida) {
                System.out.print("Selección inválida. Ingrese números separados por coma (1-" + TIPOS_ARCHIVO.length + "): ");
            }
        }

        return seleccion;
    }
}