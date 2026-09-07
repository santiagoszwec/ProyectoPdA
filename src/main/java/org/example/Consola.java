package org.example;

import java.util.Scanner;

public class Consola {

    public static int leerOpcion(Scanner sc) {
        String linea = sc.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Intente de nuevo.");
            return -1;
        }
    }
}