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

            String sql = "Insert into usuarios (nombre, correo, anioGeneracion, rol, contrasenia) values (?,?,?,?,?)";

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

            String sql = "SELECT * FROM Usuario order by nombre";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Usuario> retorno = new ArrayList<>();

            while (filas.next()) {
                int id = filas.getInt("id");
                String nombre = filas.getString("nombre");
                String correo = filas.getString("correo");
                int anioGeneracion = filas.getInt("anioGeneracion");
                TipoRol rol = (TipoRol) filas.getObject("rol");
                String contrasenia = filas.getString("contrasenia");

               Usuario usuario = new Usuario(id, nombre, correo, anioGeneracion, rol, contrasenia);

                retorno.add(usuario);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre = ?, correo = ?, anioGeneracion = ?, rol = ?, contrasenia = ? WHERE id= ?"; //FALTA TERMINAR
        try {
            Connection conexion = ConexionDB.obtenerConexion();
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

    public boolean eliminar(int id) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "DELETE FROM Usuario WHERE id = ? ";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
