package org.example;

import org.example.DAOS.SuspensionDAO;
import org.example.DAOS.UsuarioDAO;
import org.example.ENUMS.TipoRol;
import org.example.Menus.MenuAdmin;
import org.example.Menus.MenuEstudiante;
import org.example.Modelos.Suspension;
import org.example.Modelos.Usuario;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Migracion.migrar();

        Scanner sc = new Scanner(System.in);
        Usuario usuario;

        while (true) {

            do {
                System.out.print("Correo: ");
                String correo = sc.nextLine();
                System.out.print("Contrasenia: ");
                String contrasenia = sc.nextLine();

                usuario = UsuarioDAO.iniciarSesion(correo, contrasenia);

                if (usuario == null) {
                    System.out.print("Correo o contrasenia incorrectos. Intente de nuevo\n");
                } else if (!usuario.isActivo()) {
                    Suspension suspension = SuspensionDAO.obtenerSuspensionActiva(usuario.getId());

                    if (suspension != null && !suspension.getFechaFin().isBefore(LocalDate.now())) {
                        System.out.println("Tu cuenta está suspendida hasta " + suspension.getFechaFin()
                                + ". Motivo: " + suspension.getMotivo());
                        usuario = null;
                    } else if (suspension != null) {
                        SuspensionDAO.levantar(usuario.getId());
                        usuario.setActivo(true);
                        System.out.println("Suspensión expirada. Bienvenido de nuevo.");
                    } else {
                        System.out.println("Cuenta desactivada. Contacte al administrador.");
                        usuario = null;
                    }
                }

            } while (usuario == null);

            System.out.print("Bienvenido!\n");

            if (usuario.getRol() == TipoRol.Admin) {
                MenuAdmin.mostrar(sc, usuario);
            } else if (usuario.getRol() == TipoRol.Estudiante) {
                MenuEstudiante.mostrar(sc, usuario);
            }

        }while(usuario == null);

        System.out.print("Bienvenido!");

        if (usuario.getRol() == TipoRol.Admin) {
            MenuAdmin.mostrar(sc, usuario);
        } else if (usuario.getRol() == TipoRol.Estudiante) {
            MenuEstudiante.mostrar(sc, usuario);
        }
    }


}
