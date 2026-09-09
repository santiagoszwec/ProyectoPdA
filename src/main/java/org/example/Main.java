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
        int cont = 0;
        String contraseniaNueva;
        String confirmarContrasenia;
        while (true) {

            do {
                System.out.print("Correo: ");
                String correo = sc.nextLine();
                System.out.print("Contrasenia: ");
                String contrasenia = sc.nextLine();

                usuario = UsuarioDAO.iniciarSesion(correo, contrasenia);

                if (usuario == null) {
                    System.out.print("Correo o contrasenia incorrectos. Intente de nuevo\n");
                    if(cont >= 1)
                    {
                        cont = 0;
                        System.out.print("Olvido su contrasenia? S/N\n");
                        String respuesta = sc.nextLine();
                        if(respuesta.equalsIgnoreCase("S"))
                        {
                            Usuario usuarioAResetear = UsuarioDAO.buscarPorCorreo(correo);

                            if (usuarioAResetear == null) {
                                System.out.println("No existe ningún usuario con ese correo.");
                            } else {
                                boolean contraseniaConfirmada = false;
                                do {
                                    System.out.print("\n === REESTABLECER CONTRASENIA ===");
                                    System.out.print("\n Ingrese nueva contrasenia: ");
                                    contraseniaNueva = sc.nextLine();
                                    System.out.print("\n Ingrese de nuevo la contrasenia para confirmar: ");
                                    confirmarContrasenia = sc.nextLine();
                                    if (!contraseniaNueva.equals(confirmarContrasenia)) {
                                        System.out.println("\nLas contraseñas no coinciden. Intente de nuevo.");
                                    }else{
                                        usuarioAResetear.setContrasenia(contraseniaNueva);
                                        boolean actualizado = UsuarioDAO.actualizar(usuarioAResetear);

                                        if (actualizado) {
                                            System.out.println("\nContrasenia reestablecida.");
                                        } else {
                                            System.out.println("\nNo se pudo actualizar la contraseña.");
                                        }
                                        contraseniaConfirmada = true;
                                    }
                                } while (!contraseniaConfirmada);
                            }
                        }
                    }
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
                cont ++;
            } while (usuario == null);

            System.out.print("Bienvenido!\n");

            if (usuario.getRol() == TipoRol.Admin) {
                MenuAdmin.mostrar(sc, usuario);
            } else if (usuario.getRol() == TipoRol.Estudiante) {
                MenuEstudiante.mostrar(sc, usuario);
            }

        }
    }


}
