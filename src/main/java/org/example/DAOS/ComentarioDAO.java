package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Comentario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    public static boolean crear(Comentario comentario) {

        String sql = "INSERT INTO comentario (mensaje, imagen_url, fecha_publicacion, usuario_id, publicacion_id, comentario_padre_id, destacado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, comentario.getMensaje());
            sentencia.setString(2, comentario.getImagenUrl());
            sentencia.setObject(3, comentario.getFechaPublicacion());
            sentencia.setInt(4, comentario.getUsuarioId());
            sentencia.setInt(5, comentario.getPublicacionId());

            if (comentario.getComentarioPadreId() != null) {
                sentencia.setInt(6, comentario.getComentarioPadreId());
            } else {
                sentencia.setNull(6, Types.INTEGER);
            }
            
            sentencia.setBoolean(7, comentario.isDestacado());

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Comentario> listarPorPublicacion(int publicacionId) {

        String sql = "SELECT * FROM comentario " +
                     "WHERE publicacion_id = ? AND comentario_padre_id IS NULL AND activa = TRUE " +
                     "ORDER BY destacado DESC, fecha_publicacion ASC";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, publicacionId);

            ResultSet filas = sentencia.executeQuery();

            List<Comentario> comentarios = new ArrayList<>();

            while (filas.next()) {
                comentarios.add(mapearComentario(filas));
            }

            return comentarios;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Comentario> listarRespuestas(int comentarioPadreId) {

        String sql = "SELECT * FROM comentario " +
                     "WHERE comentario_padre_id = ? AND activa = TRUE " +
                     "ORDER BY fecha_publicacion ASC";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, comentarioPadreId);

            ResultSet filas = sentencia.executeQuery();

            List<Comentario> comentarios = new ArrayList<>();

            while (filas.next()) {
                comentarios.add(mapearComentario(filas));
            }

            return comentarios;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean marcarComoDestacado(int comentarioId) {
        String sql = "UPDATE comentario SET destacado = TRUE WHERE id = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, comentarioId);
            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Comentario buscarPorId(int comentarioId) {
        String sql = "SELECT * FROM comentario WHERE id = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, comentarioId);
            ResultSet filas = sentencia.executeQuery();

            if (filas.next()) {
                return mapearComentario(filas);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean darDeBaja(int comentarioId) {
        String sql = "UPDATE comentario SET activa = FALSE WHERE id = ? OR comentario_padre_id = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, comentarioId);
            sentencia.setInt(2, comentarioId);
            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Comentario mapearComentario(ResultSet fila) throws SQLException {

        int padreId = fila.getInt("comentario_padre_id");
        Integer comentarioPadreId = fila.wasNull() ? null : padreId;

        return new Comentario(
                fila.getInt("id"),
                fila.getString("mensaje"),
                fila.getString("imagen_url"),
                fila.getObject("fecha_publicacion", LocalDate.class),
                fila.getInt("usuario_id"),
                fila.getInt("publicacion_id"),
                comentarioPadreId,
                fila.getBoolean("destacado"),
                fila.getBoolean("activa")
        );
    }
}
