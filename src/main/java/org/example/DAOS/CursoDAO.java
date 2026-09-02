package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public static int crear(Curso curso) {
        String sql = "INSERT INTO curso (nombre, semestre, anio, creditos, descripcion) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conexion = ConexionDB.obtenerConexion();

            PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, curso.getNombre());
            sentencia.setInt(2, curso.getSemestre());
            sentencia.setInt(3, curso.getAnio());
            sentencia.setInt(4, curso.getCreditos());
            sentencia.setString(5, curso.getDescripcion());

            sentencia.executeUpdate();

            ResultSet claves = sentencia.getGeneratedKeys();

            if (claves.next()) {
                return claves.getInt(1);
            }

            throw new RuntimeException("No se pudo obtener el ID del curso.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean eliminar(int id) {

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sqlTemas = "DELETE FROM tema WHERE curso_id = ?";

            PreparedStatement eliminarTemas = conexion.prepareStatement(sqlTemas);

            eliminarTemas.setInt(1, id);
            eliminarTemas.executeUpdate();

            String sqlCurso = "DELETE FROM curso WHERE id = ?";

            PreparedStatement eliminarCurso = conexion.prepareStatement(sqlCurso);

            eliminarCurso.setInt(1, id);

            return eliminarCurso.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Curso> listarTodos() {

        String sql = "SELECT * FROM curso ORDER BY id";

        try (Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            List<Curso> cursos = new ArrayList<>();

            while (resultado.next()) {

                Curso curso = new Curso(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getInt("semestre"),
                        resultado.getInt("anio"),
                        resultado.getInt("creditos"),
                        resultado.getString("descripcion")
                );
                cursos.add(curso);
            }
            return cursos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}