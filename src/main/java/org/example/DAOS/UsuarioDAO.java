package org.example.DAOS;

import org.example.ConexionDB;
import org.example.ENUMS.TipoRol;
import org.example.Modelos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public static boolean crear(Usuario usuario) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "INSERT INTO usuario (nombre, correo, anio_de_generacion, rol, contrasenia) VALUES (?,?,?,?,?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getCorreo());
            sentencia.setInt(3, usuario.getAnioDeGeneracion());
            sentencia.setObject(4, usuario.getRol());
            sentencia.setString(5, usuario.getContrasenia());
            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Usuario> listarTodos() {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "SELECT * FROM usuario ORDER BY nombre";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Usuario> retorno = new ArrayList<>();

            while (filas.next()) {
                int id = filas.getInt("id");
                String nombre = filas.getString("nombre");
                String correo = filas.getString("correo");
                int anioGeneracion = filas.getInt("anio_de_generacion");
                TipoRol rol = TipoRol.valueOf(filas.getString("rol"));
                String contrasenia = filas.getString("contrasenia");
                boolean activo = filas.getBoolean("activo");

                Usuario usuario = new Usuario(id, nombre, correo, anioGeneracion, rol, contrasenia, activo);
                retorno.add(usuario);
            }
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Usuario> listarActivos() {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "SELECT * FROM usuario WHERE activo = TRUE ORDER BY nombre";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Usuario> retorno = new ArrayList<>();

            while (filas.next()) {
                Usuario usuario = new Usuario(
                        filas.getInt("id"),
                        filas.getString("nombre"),
                        filas.getString("correo"),
                        filas.getInt("anio_de_generacion"),
                        TipoRol.valueOf(filas.getString("rol")),
                        filas.getString("contrasenia"),
                        filas.getBoolean("activo")
                );

                retorno.add(usuario);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, anio_de_generacion = ?, rol = ?, contrasenia = ? WHERE id = ?";
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getCorreo());
            sentencia.setInt(3, usuario.getAnioDeGeneracion());
            sentencia.setObject(4, usuario.getRol());
            sentencia.setString(5, usuario.getContrasenia());
            sentencia.setInt(6, usuario.getId());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean desactivar(int id) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "UPDATE usuario SET activo = FALSE WHERE id = ?";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Usuario iniciarSesion(String correo, String contrasenia){
        try{
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasenia = ? AND activo = TRUE";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, correo);
            sentencia.setString(2, contrasenia);

            ResultSet fila = sentencia.executeQuery();
            if(fila.next()){
                boolean activo = fila.getBoolean("activo");

                return new Usuario(
                        fila.getInt("id"),
                        fila.getString("nombre"),
                        fila.getString("correo"),
                        fila.getInt("anio_de_generacion"),
                        TipoRol.valueOf(fila.getString("rol")),
                        fila.getString("contrasenia"),
                        activo);
            }
            else{
                return null;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarRol(int userId, TipoRol nuevoRol) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();


            String sql = "UPDATE usuario SET rol = ? WHERE id = ?";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setObject(1, nuevoRol);

            sentencia.setInt(2, userId);

            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}