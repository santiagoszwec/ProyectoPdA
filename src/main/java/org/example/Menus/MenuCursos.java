package org.example.Menus;

import org.example.DAOS.CursoDAO;
import org.example.DAOS.TemaDAO;
import org.example.Modelos.Curso;
import org.example.Modelos.Tema;

import java.util.List;
import java.util.Scanner;

public class MenuCursos {

    public static void mostrar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE CURSOS =====");
            System.out.println("1. Agregar nuevo curso");
            System.out.println("2. Listar cursos");
            System.out.println("3. Modificar curso");
            System.out.println("4. Eliminar curso");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    agregarCurso(sc);
                    break;

                case 2:
                    listarCursos();
                    break;

                case 3:
                    modificarCurso(sc);
                    break;

                case 4:
                    eliminarCurso(sc);
                    break;

                case 5:
                    System.out.println("Volviendo al menú administrador...");
                    break;
            }

        } while (opcion != 0);
    }

    private static void agregarCurso(Scanner sc) {

        System.out.println("\n===== AGREGAR NUEVO CURSO =====");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Semestre: ");
        int semestre = Integer.parseInt(sc.nextLine());

        System.out.print("Año: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Créditos: ");
        int creditos = Integer.parseInt(sc.nextLine());

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        Curso curso = new Curso(nombre, semestre, anio, creditos, descripcion);

        int cursoId = CursoDAO.crear(curso);

        if (cursoId > 0) {
            System.out.println("\nCurso creado correctamente.");
            System.out.print("¿Desea agregar temas de estudio? (S/N): ");
            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                agregarTemas(sc, cursoId);
            }
        } else {
            System.out.println("\nNo se pudo crear el curso.");
        }
    }
    private static void agregarTemas(Scanner sc, int cursoId) {

        String respuesta;

        do {
            System.out.print("Nombre del tema: ");
            String nombreTema = sc.nextLine();

            Tema tema = new Tema(cursoId, nombreTema);

            boolean creado = TemaDAO.crear(tema);

            if (creado) {
                System.out.println("Tema agregado correctamente.");
            } else {
                System.out.println("No se pudo agregar el tema.");
            }

            System.out.print("¿Desea agregar otro tema? (S/N): ");
            respuesta = sc.nextLine();

        } while (respuesta.equalsIgnoreCase("S"));
    }
    private static void listarCursos() {

        System.out.println("\n===== LISTA DE CURSOS =====");

        List<Curso> cursos = CursoDAO.listarTodos();

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        for (Curso curso : cursos) {
            System.out.println(
                    "ID: " + curso.getId() +
                            " | Nombre: " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio() +
                            " | Créditos: " + curso.getCreditos()
            );
        }
    }
    private static void eliminarCurso(Scanner sc) {

        System.out.println("\n===== ELIMINAR CURSO =====");

        List<Curso> cursos = CursoDAO.listarTodos();

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        System.out.println("\nCursos disponibles:");

        for (Curso curso : cursos) {
            System.out.println(
                    "ID: " + curso.getId() +
                            " | Nombre: " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio());
        }

        System.out.print("Ingrese el ID del curso a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean eliminado = CursoDAO.eliminar(id);

        if (eliminado) {
            System.out.println("Curso eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún curso con ese ID.");
        }
    }


    private static void modificarCurso(Scanner sc){
        System.out.println("\n===== MODIFICAR CURSO =====");

        List<Curso> cursos = CursoDAO.listarTodos();

        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        System.out.println("\nCursos disponibles:");

        for (Curso curso : cursos) {
            System.out.println(
                    "ID: " + curso.getId() +
                            " | Nombre: " + curso.getNombre() +
                            " | Semestre: " + curso.getSemestre() +
                            " | Año: " + curso.getAnio());
        }
        boolean existeCurso = false;
        Curso cursoTemp = null;

        do{
            System.out.print("Ingrese el ID del curso a modificar: ");
            int id = Integer.parseInt(sc.nextLine());

            for(int i =0; i< cursos.size(); i++){
                if(cursos.get(i).getId() == id){
                    existeCurso = true;
                    cursoTemp = cursos.get(i);
                    break;
                }
            }

            if(existeCurso == false){
                System.out.println("No se encontro ningun curso con el ID ingresado. Desea volver ingresar? S/N");
                String respuesta = sc.nextLine();
                if(respuesta.equalsIgnoreCase("N")){
                    return;
                }
            }
        }while(existeCurso == false);

        System.out.print("Ingrese nuevo nombre, enter para mantener: ");
        String nombre = sc.nextLine();
        if (!nombre.isBlank()) {
            cursoTemp.setNombre(nombre);
        }

        String semestre;
        do {
            System.out.print("Ingrese nuevo semestre (1 a 6), enter para mantener: ");
            semestre = sc.nextLine();

            if (semestre.isBlank()) {
                break;
            }

            int semestreNum = Integer.parseInt(semestre);
            if (semestreNum >= 1 && semestreNum <= 6) {
                cursoTemp.setSemestre(semestreNum);
                break;
            } else {
                System.out.println("Semestre inválido, debe estar entre 1 y 6.");
            }
        } while (true);

        int anioActual = java.time.LocalDate.now().getYear();

        String anio;
        do {
            System.out.print("Ingrese nuevo anio, enter para mantener: ");
            anio = sc.nextLine();

            if (anio.isBlank()) {
                break;
            }

            int anioNuevo = Integer.parseInt(anio);
            if (anioNuevo >= 2008 && anioNuevo <= anioActual) {
                cursoTemp.setAnio(anioNuevo);
                break;
            } else {
                System.out.println("Año invalido, no puede ser superior al actual");
            }
        } while (true);

        String creditosInput;
        do {
            System.out.print("Ingrese nuevos creditos, enter para mantener: ");
            creditosInput = sc.nextLine();

            if (creditosInput.isBlank()) {
                break;
            }

            int creditosNum = Integer.parseInt(creditosInput);
            if (creditosNum >= 1 && creditosNum <= 20) {
                cursoTemp.setCreditos(creditosNum);
                break;
            } else {
                System.out.println("Cantidad de créditos inválidos");
            }
        } while (true);

        System.out.print("Ingrese nueva descripcion, enter para mantener: ");
        String descripcion = sc.nextLine();
        if (!descripcion.isBlank()) {
            cursoTemp.setDescripcion(descripcion);
        }
        System.out.println("\nDatos a guardar:");
        System.out.println(
                "ID: " + cursoTemp.getId() +
                        " | Nombre: " + cursoTemp.getNombre() +
                        " | Semestre: " + cursoTemp.getSemestre() +
                        " | Año: " + cursoTemp.getAnio() +
                        " | Créditos: " + cursoTemp.getCreditos() +
                        " | Descripción: " + cursoTemp.getDescripcion());

        System.out.print("¿Confirmar modificación? S/N: ");
        String confirmar = sc.nextLine();

        if (!confirmar.equalsIgnoreCase("S")) {
            System.out.println("Modificación cancelada.");
            return;
        }

        boolean modificar = CursoDAO.actualizar(cursoTemp);

        if (modificar) {
            System.out.println("Curso modificado correctamente.");
        } else {
            System.out.println("No se encontró ningún curso con ese ID.");
        }
        //git push origin angie
    }
}