package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Inscripcion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InscripcionDAO {

    public static boolean crear(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscripcion (estado, usuario_id, curso_id) VALUES (?,?,?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, inscripcion.getEstado().name());
            sentencia.setInt(2, inscripcion.getUsuarioId());
            sentencia.setInt(3, inscripcion.getCursoId());

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean usuarioExisteYActivo(int usuarioId) {
        String sql = "SELECT activo FROM usuario WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, usuarioId);

            try (ResultSet fila = sentencia.executeQuery()) {
                return fila.next() && fila.getBoolean("activo");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cursoExisteYActivo(int cursoId) {
        String sql = "SELECT activo FROM curso WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, cursoId);

            try (ResultSet fila = sentencia.executeQuery()) {
                return fila.next() && fila.getBoolean("activo");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existeInscripcion(int usuarioId, int cursoId) {
        String sql = "SELECT 1 FROM inscripcion WHERE usuario_id = ? AND curso_id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, usuarioId);
            sentencia.setInt(2, cursoId);

            try (ResultSet fila = sentencia.executeQuery()) {
                return fila.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}