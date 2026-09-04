package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Comentario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    public static boolean crear(Comentario comentario) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "INSERT INTO comentario (mensaje, imagen_url, fecha_publicacion, usuario_id, publicacion_id, comentario_padre_id) VALUES (?,?,?,?,?,?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, comentario.getMensaje());
            sentencia.setString(2, comentario.getImagenUrl());
            sentencia.setObject(3, comentario.getFechaPublicacion());
            sentencia.setInt(4, comentario.getUsuarioId());
            sentencia.setInt(5, comentario.getPublicacionId());

            if (comentario.getComentarioPadreId() == null) {
                sentencia.setNull(6, Types.INTEGER);
            } else {
                sentencia.setInt(6, comentario.getComentarioPadreId());
            }

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Respuestas de primer nivel de una duda/publicación (sin comentario padre).
    public static List<Comentario> listarPorPublicacion(int publicacionId) {
        String sql = "SELECT * FROM comentario WHERE publicacion_id = ? AND comentario_padre_id IS NULL ORDER BY fecha_publicacion";
        return listar(sql, publicacionId);
    }

    // Comentarios anidados bajo una respuesta puntual.
    public static List<Comentario> listarRespuestas(int comentarioPadreId) {
        String sql = "SELECT * FROM comentario WHERE comentario_padre_id = ? ORDER BY fecha_publicacion";
        return listar(sql, comentarioPadreId);
    }

    private static List<Comentario> listar(String sql, int parametro) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, parametro);

            ResultSet filas = sentencia.executeQuery();

            List<Comentario> retorno = new ArrayList<>();

            while (filas.next()) {
                int id = filas.getInt("id");
                String mensaje = filas.getString("mensaje");
                String imagenUrl = filas.getString("imagen_url");
                LocalDate fechaPublicacion = filas.getObject("fecha_publicacion", LocalDate.class);
                int usuarioId = filas.getInt("usuario_id");
                int publicacionId = filas.getInt("publicacion_id");
                Integer comentarioPadreId = (Integer) filas.getObject("comentario_padre_id");
                boolean activa = filas.getBoolean("activa");

                Comentario comentario = new Comentario(id, mensaje, imagenUrl, fechaPublicacion, usuarioId, publicacionId, comentarioPadreId, activa);
                retorno.add(comentario);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
