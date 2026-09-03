package org.example.DAOS;

import org.example.ConexionDB;
import org.example.ENUMS.EstadoDuda;
import org.example.ENUMS.TipoCategoria;
import org.example.Modelos.Duda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DudaDAO {

    // Duda hereda de Publicacion por tabla dividida: primero se inserta en
    // "publicacion" (para obtener el id autogenerado) y luego en "duda" con ese mismo id.
    public static boolean crear(Duda duda) {
        String sqlPublicacion = "INSERT INTO publicacion (mensaje, imagen_url, fecha_publicacion) VALUES (?,?,?)";
        String sqlDuda = "INSERT INTO duda (id, estado, categoria) VALUES (?,?,?)";

        try {
            Connection conexion = ConexionDB.obtenerConexion();

            PreparedStatement sentenciaPublicacion = conexion.prepareStatement(sqlPublicacion, Statement.RETURN_GENERATED_KEYS);
            sentenciaPublicacion.setString(1, duda.getMensaje());
            sentenciaPublicacion.setString(2, duda.getImagenUrl());
            sentenciaPublicacion.setObject(3, duda.getFechaPublicacion());
            sentenciaPublicacion.executeUpdate();

            ResultSet generadas = sentenciaPublicacion.getGeneratedKeys();
            if (!generadas.next()) {
                return false;
            }

            int id = generadas.getInt(1);
            duda.setId(id);

            PreparedStatement sentenciaDuda = conexion.prepareStatement(sqlDuda);
            sentenciaDuda.setInt(1, id);
            sentenciaDuda.setObject(2, duda.getEstado());
            sentenciaDuda.setObject(3, duda.getCategoria());

            return sentenciaDuda.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Duda> listarTodos() {
        String sql = "SELECT p.id, p.mensaje, p.imagen_url, p.fecha_publicacion, p.activa, d.estado, d.categoria " +
                "FROM publicacion p JOIN duda d ON d.id = p.id " +
                "WHERE p.activa = TRUE ORDER BY p.fecha_publicacion";

        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Duda> retorno = new ArrayList<>();

            while (filas.next()) {
                retorno.add(mapearDuda(filas));
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Duda buscarPorId(int id) {
        String sql = "SELECT p.id, p.mensaje, p.imagen_url, p.fecha_publicacion, p.activa, d.estado, d.categoria " +
                "FROM publicacion p JOIN duda d ON d.id = p.id " +
                "WHERE p.id = ?";

        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);

            ResultSet fila = sentencia.executeQuery();

            if (fila.next()) {
                return mapearDuda(fila);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Duda mapearDuda(ResultSet filas) throws SQLException {
        int id = filas.getInt("id");
        String mensaje = filas.getString("mensaje");
        String imagenUrl = filas.getString("imagen_url");
        LocalDate fechaPublicacion = filas.getObject("fecha_publicacion", LocalDate.class);
        boolean activa = filas.getBoolean("activa");
        EstadoDuda estado = EstadoDuda.valueOf(filas.getString("estado"));
        TipoCategoria categoria = TipoCategoria.valueOf(filas.getString("categoria"));

        return new Duda(id, mensaje, imagenUrl, fechaPublicacion, !activa, estado, categoria);
    }
}
