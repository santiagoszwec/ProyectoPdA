package org.example.DAOS;

import org.example.ConexionDB;
import org.example.ENUMS.TipoCategoria;
import org.example.ENUMS.TipoRol;
import org.example.Modelos.Publicacion;
import org.example.Modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PublicacionDAO {
    public static boolean crear(Publicacion publicacion) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "Insert into publicaciones (mensaje, imagenURL, fechaPublicacion) values (?,?,?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, publicacion.getMensaje());
            sentencia.setString(2, publicacion.getImagenUrl());
            sentencia.setObject(3, publicacion.getFechaPublicacion());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Publicacion> listarTodos() {

        String sql = "SELECT * FROM publicacion ORDER BY fecha_publicacion";

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet filas = sentencia.executeQuery()) {

            List<Publicacion> retorno = new ArrayList<>();

            while (filas.next()) {

                int id = filas.getInt("id");
                String mensaje = filas.getString("mensaje");
                String imagenUrl = filas.getString("imagen_url");

                LocalDate fechaPublicacion =
                        filas.getObject("fecha_publicacion", LocalDate.class);

                Publicacion publicacion = new Publicacion(id, mensaje, imagenUrl, fechaPublicacion);

                retorno.add(publicacion);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Publicacion publicacion) {
        String sql = "UPDATE publicacion SET mensaje = ?, imagenURL = ?, fecha = ? WHERE id= ?";
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, publicacion.getMensaje());
            sentencia.setString(2, publicacion.getImagenUrl());
            sentencia.setObject(3, publicacion.getFechaPublicacion());
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
}
