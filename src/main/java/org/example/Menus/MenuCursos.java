package org.example.Menus;

import org.example.Consola;
import org.example.DAOS.CursoDAO;
import org.example.Modelos.Curso;

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

            opcion = Consola.leerOpcion(sc);

            switch (opcion) {

                case 1:
                    agregarCurso(sc);
                    break;

                case 2:
                    listarCursos(sc);
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

            /*if (respuesta.equalsIgnoreCase("S")) {
                agregarTemas(sc, cursoId);
            }*/
        } else {
            System.out.println("\nNo se pudo crear el curso.");
        }
    }
    /*private static void agregarTemas(Scanner sc, int cursoId) {

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
    }*/
    private static void listarCursos(Scanner sc) {

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
                            " | Créditos: " + curso.getCreditos() +
                            " | Descripcion: " + curso.getDescripcion()
            );
        }

        System.out.println("Desea aplicar un filtro? S/N");
        String respuesta = sc.nextLine();

        if(!respuesta.equalsIgnoreCase("S")){
            return;
        }
        boolean seguirFiltrando;
        do{
            int filtro;
            do {
                System.out.println("Filtrar por: ");
                System.out.println("1. Nombre");
                System.out.println("2. Semestre");
                System.out.print("Seleccione una opción: ");
                filtro = Integer.parseInt(sc.nextLine());

                if (filtro < 1 || filtro > 3) {
                    System.out.println("Opción inválida, intente de nuevo.");
                }
            } while (filtro < 1 || filtro> 2);

            boolean cursosEncontrados = false;
            String nombre;
            switch(filtro){
                case 1:{
                    System.out.print("Ingrese el nombre del curso a buscar: ");
                    String busqueda = sc.nextLine().toLowerCase();
                    System.out.println("\nResultados:");
                    for(int i =0; i<cursos.size(); i++){
                        if (cursos.get(i).getNombre().toLowerCase().contains(busqueda)) {
                            System.out.println(
                                    "ID: " + cursos.get(i).getId() +
                                            " | Nombre: " + cursos.get(i).getNombre() +
                                            " | Semestre: " + cursos.get(i).getSemestre() +
                                            " | Año: " + cursos.get(i).getAnio() +
                                            " | Créditos: " + cursos.get(i).getCreditos() +
                                            " | Descripcion: " + cursos.get(i).getDescripcion());
                            cursosEncontrados = true;
                        }
                    }
                    if (!cursosEncontrados) {
                        System.out.println("No se encontraron cursos con ese nombre.");
                    }
                }
                break;
                case 2:
                {
                    System.out.print("Ingrese numero del semestre del curso a buscar: ");
                    int semestre = Integer.parseInt(sc.nextLine());
                    System.out.println("\nResultados:");
                    for(int i =0; i<cursos.size(); i++){
                        if (cursos.get(i).getSemestre() == semestre) {
                            System.out.println(
                                    "ID: " + cursos.get(i).getId() +
                                            " | Nombre: " + cursos.get(i).getNombre() +
                                            " | Semestre: " + cursos.get(i).getSemestre() +
                                            " | Año: " + cursos.get(i).getAnio() +
                                            " | Créditos: " + cursos.get(i).getCreditos() +
                                            " | Descripcion: " + cursos.get(i).getDescripcion());
                            cursosEncontrados = true;
                        }
                    }
                    if (!cursosEncontrados) {
                        System.out.println("No se encontraron cursos del numero del semestre ingresado.");
                    }
                }
                default:
                    break;
            }

            System.out.print("¿Desea intentar con otro filtro? S/N: ");
            seguirFiltrando = sc.nextLine().equalsIgnoreCase("S");


        }while(seguirFiltrando);



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

        System.out.print("\nIngrese el ID del curso a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        Curso cursoSeleccionado = null;

        for (Curso curso : cursos) {
            if (curso.getId() == id) {
                cursoSeleccionado = curso;
                break;
            }
        }

        if (cursoSeleccionado == null) {
            System.out.println("No se encontró ningún curso con ese ID.");
            return;
        }

        System.out.println("\nCurso seleccionado: " + cursoSeleccionado.getNombre());

        System.out.print("¿Está seguro de que desea eliminar este curso? (S/N): ");
        String confirmacion = sc.nextLine();

        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean eliminado = CursoDAO.desactivar(id);

        if (eliminado) {
            System.out.println("Curso eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el curso.");
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
                System.out.println("Año invalido. Intente de nuevo");
            }
        } while (true);

        String creditosTemp;
        do {
            System.out.print("Ingrese nuevos creditos, enter para mantener: ");
            creditosTemp= sc.nextLine();

            if (creditosTemp.isBlank()) {
                break;
            }

            int creditosNum = Integer.parseInt(creditosTemp);
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

    }
}