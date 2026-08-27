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

            String sql = "Insert into publicaciones (mensaje, imagenURL, fechaPublicacion, categoria) values (?,?,?,?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, publicacion.getMensaje());
            sentencia.setString(2, publicacion.getImagenUrl());
            sentencia.setObject(3, publicacion.getFechaPublicacion());
            sentencia.setObject(4, publicacion.getCategoria());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Publicacion> listarTodos() {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "SELECT * FROM publicaciones order by fechaPublicacion";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Publicacion> retorno = new ArrayList<>();

            while (filas.next()) {
                int id = filas.getInt("id");
                String mensaje = filas.getString("mensaje");
                String imagenURL = filas.getString("imagenURL");
                LocalDate fechaPublicacion = (LocalDate) filas.getObject("fecha");
                TipoCategoria categoria = (TipoCategoria) filas.getObject("categoria");

                Publicacion publicacion = new Publicacion(id, mensaje, imagenURL, fechaPublicacion, categoria);

                retorno.add(publicacion);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Publicacion publicacion) {
        String sql = "UPDATE publicaciones SET mensaje = ?, imagenURL = ?, fecha = ?, categoria = ? WHERE id= ?";
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, publicacion.getMensaje());
            sentencia.setString(2, publicacion.getImagenUrl());
            sentencia.setObject(3, publicacion.getFechaPublicacion());
            sentencia.setObject(4, publicacion.getCategoria());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminar(int id) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "DELETE FROM publicaciones WHERE id = ? ";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
