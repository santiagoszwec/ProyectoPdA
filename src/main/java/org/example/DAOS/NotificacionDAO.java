package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Notificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO {

    public static boolean crear(Notificacion notificacion) {
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            return insertarNotificacion(conexion, notificacion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean insertarNotificacion(Connection conexion, Notificacion notificacion) throws SQLException {

        String sql = "INSERT INTO notificacion (fecha, tipo, mensaje, usuario_id, publicacion_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setObject(1, notificacion.getFecha());
            sentencia.setString(2, notificacion.getTipo().toString());
            sentencia.setString(3, notificacion.getMensaje());
            sentencia.setInt(4, notificacion.getUsuarioId());
            sentencia.setInt(5, notificacion.getPublicacionId());

            return sentencia.executeUpdate() == 1;
        }
    }
    public static List<Notificacion> listarPorUsuario(int usuarioId) {
        String sql = "SELECT * FROM notificacion WHERE usuario_id = ? ORDER BY id DESC";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, usuarioId);

            try (ResultSet filas = sentencia.executeQuery()) {
                List<Notificacion> retorno = new ArrayList<>();
                while (filas.next()) {
                    retorno.add(new Notificacion(
                            filas.getInt("id"),
                            filas.getObject("fecha", java.time.LocalDate.class),
                            org.example.ENUMS.TipoNotificacion.valueOf(filas.getString("tipo")),
                            filas.getString("mensaje"),
                            filas.getInt("usuario_id"),
                            filas.getInt("publicacion_id")
                    ));
                }
                return retorno;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}