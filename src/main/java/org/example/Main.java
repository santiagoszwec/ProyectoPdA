package org.example;

import org.example.DAOS.UsuarioDAO;
import org.example.ENUMS.TipoRol;
import org.example.Menus.MenuAdmin;
import org.example.Menus.MenuEstudiante;
import org.example.Modelos.Usuario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Migracion.migrar();

        Scanner sc = new Scanner(System.in);
        Usuario usuario;

        do{
            System.out.print("Correo: ");
            String correo = sc.nextLine();
            System.out.print("Contrasenia: ");
            String contrasenia = sc.nextLine();

            usuario = UsuarioDAO.iniciarSesion(correo, contrasenia);

            if(usuario == null){
                System.out.print("Correo o contrasenia incorrectos. Intente de nuevo");
            }

        }while(usuario == null);

        System.out.print("Bienvenido!");

        if (usuario.getRol() == TipoRol.Admin) {
            MenuAdmin.mostrar(sc);
        } else if (usuario.getRol() == TipoRol.Estudiante) {
            //MenuEstudiante.mostrar(sc);
        }

    }


}
