package org.example.DAOS;

import org.example.ConexionDB;
import org.example.ENUMS.TipoEstado;
import org.example.Modelos.Inscripcion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {

    public static boolean existeInscripcion(int usuarioId, int cursoId) {
        String sql = "SELECT 1 FROM inscripcion WHERE usuario_id = ? AND curso_id = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, usuarioId);
            sentencia.setInt(2, cursoId);
            try (ResultSet filas = sentencia.executeQuery()) {
                return filas.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean inscribir(int usuarioId, int cursoId) {
        String sql = "INSERT INTO inscripcion (estado, usuario_id, curso_id) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, TipoEstado.Cursando.name());
            sentencia.setInt(2, usuarioId);
            sentencia.setInt(3, cursoId);
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Inscripcion> listarPorUsuario(int usuarioId) {
        String sql = "SELECT * FROM inscripcion WHERE usuario_id = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, usuarioId);
            try (ResultSet filas = sentencia.executeQuery()) {
                List<Inscripcion> retorno = new ArrayList<>();
                while (filas.next()) {
                    retorno.add(new Inscripcion(
                            filas.getInt("id"),
                            TipoEstado.valueOf(filas.getString("estado")),
                            filas.getInt("usuario_id"),
                            filas.getInt("curso_id")
                    ));
                }
                return retorno;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}