package org.example.DAOS;

import org.example.ConexionDB;
import org.example.ENUMS.TipoCategoria;
import org.example.ENUMS.TipoNotificacion;
import org.example.ENUMS.TipoRol;
import org.example.Modelos.Notificacion;
import org.example.Modelos.Publicacion;
import org.example.Modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PublicacionDAO {
    public static boolean crear(Publicacion publicacion) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "INSERT INTO publicacion (mensaje, imagen_url, fecha_publicacion, usuario_id) VALUES (?,?,?,?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, publicacion.getMensaje());
            sentencia.setString(2, publicacion.getImagenUrl());
            sentencia.setObject(3, publicacion.getFechaPublicacion());
            sentencia.setInt(4, publicacion.getUsuarioId());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Publicacion> listarTodos() {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "SELECT * FROM publicacion WHERE activa = TRUE ORDER BY fecha_publicacion";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Publicacion> retorno = new ArrayList<>();

            while (filas.next()) {
                int id = filas.getInt("id");
                String mensaje = filas.getString("mensaje");
                String imagenUrl = filas.getString("imagen_url");
                LocalDate fechaPublicacion = filas.getObject("fecha_publicacion", LocalDate.class);
                boolean dadaDeBaja = filas.getBoolean("activa");
                int usuarioId = filas.getInt("usuario_id");

                Publicacion publicacion = new Publicacion(id, mensaje, imagenUrl, fechaPublicacion, !dadaDeBaja, usuarioId);

                retorno.add(publicacion);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Publicacion publicacion) {
        String sql = "UPDATE publicacion SET mensaje = ?, imagen_url = ?, fecha_publicacion = ? WHERE id = ?";
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, publicacion.getMensaje());
            sentencia.setString(2, publicacion.getImagenUrl());
            sentencia.setObject(3, publicacion.getFechaPublicacion());
            sentencia.setInt(4, publicacion.getId());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminar(int id) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "DELETE FROM publicacion WHERE id = ? ";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean darDeBaja(int id, String motivo) {
        Connection conexion = null;
        try {
            conexion = ConexionDB.obtenerConexion();
            conexion.setAutoCommit(false);

            Integer autorId = obtenerAutorId(conexion, id);

            boolean publicacionOk = ejecutarUpdate(conexion,
                    "UPDATE publicacion SET activa = FALSE WHERE id = ?", id);
            ejecutarUpdate(conexion,
                    "UPDATE comentario SET activa = FALSE WHERE publicacion_id = ?", id);
            ejecutarUpdate(conexion,
                    "UPDATE reporte SET resolucion = ?, fecha_resolucion = ? WHERE publicacion_id = ? AND fecha_resolucion IS NULL",
                    motivo, LocalDate.now(), id);

            boolean notificacionOk = true;
            if (autorId != null) {
                notificacionOk = NotificacionDAO.crear(conexion, new Notificacion(
                        0, LocalDate.now(), TipoNotificacion.Baja,
                        "Su publicación ha sido dada de baja por infracción de las normas.", autorId));
            }

            if (!publicacionOk || !notificacionOk) {
                conexion.rollback();
                return false;
            }

            conexion.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackEx) {
                // ignorar error al deshacer
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException e) {
                // ignorar error al cerrar
            }
        }
    }

    private static Integer obtenerAutorId(Connection conexion, int publicacionId) throws SQLException {
        String sql = "SELECT usuario_id FROM publicacion WHERE id = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, publicacionId);
            try (ResultSet fila = sentencia.executeQuery()) {
                if (fila.next()) {
                    int autorId = fila.getInt("usuario_id");
                    return fila.wasNull() ? null : autorId;
                }
            }
        }
        throw new SQLException("No se encontró la publicación con id " + publicacionId);
    }

    private static boolean ejecutarUpdate(Connection conexion, String sql, Object... parametros) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                Object p = parametros[i];
                if (p instanceof Integer) {
                    sentencia.setInt(i + 1, (Integer) p);
                } else if (p instanceof String) {
                    sentencia.setString(i + 1, (String) p);
                } else if (p instanceof LocalDate) {
                    sentencia.setObject(i + 1, (LocalDate) p);
                } else {
                    sentencia.setObject(i + 1, p);
                }
            }
            sentencia.executeUpdate();
            return true;
        }
    }

    public static Publicacion buscarPorId(int id) {
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "SELECT * FROM publicacion WHERE id = ?";
            try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
                sentencia.setInt(1, id);
                try (ResultSet filas = sentencia.executeQuery()) {
                    if (filas.next()) {
                        int pid = filas.getInt("id");
                        String mensaje = filas.getString("mensaje");
                        String imagenUrl = filas.getString("imagen_url");
                        LocalDate fechaPublicacion = filas.getObject("fecha_publicacion", LocalDate.class);
                        boolean activa = filas.getBoolean("activa");
                        int usuarioId = filas.getInt("usuario_id");
                        return new Publicacion(pid, mensaje, imagenUrl, fechaPublicacion, !activa, usuarioId);
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean reactivar(int id) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "UPDATE publicacion SET activa = TRUE WHERE id = ?";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
