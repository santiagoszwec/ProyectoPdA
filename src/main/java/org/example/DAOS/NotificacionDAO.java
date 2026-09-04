package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Notificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotificacionDAO {

    public static boolean crear(Notificacion notificacion) {
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            return crear(conexion, notificacion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean crear(Connection conexion, Notificacion notificacion) throws SQLException {
        String sql = "INSERT INTO notificacion (fecha, tipo, mensaje, usuario_id) VALUES (?,?,?,?)";
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setObject(1, notificacion.getFecha());
            sentencia.setObject(2, notificacion.getTipo().toString());
            sentencia.setString(3, notificacion.getMensaje());
            sentencia.setInt(4, notificacion.getUsuarioId());
            return sentencia.executeUpdate() == 1;
        }
    }
}
