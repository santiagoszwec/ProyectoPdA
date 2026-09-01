package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}