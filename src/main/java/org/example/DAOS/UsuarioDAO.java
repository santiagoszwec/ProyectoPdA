package org.example.DAOS;

import org.example.ConexionDB;
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

    public static List<Categoria> listarTodos() {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "SELECT * FROM Categorias order by id";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Categoria> retorno = new ArrayList<>();

            while (filas.next()) {
                int id = filas.getInt("id");
                String nombre = filas.getString("nombre");

                Categoria categoria = new Categoria(id, nombre);

                retorno.add(categoria);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Categoria categoria) {
        String sql = "UPDATE Categorias SET nombre = ? WHERE id= ?";
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, categoria.getNombre());
            sentencia.setInt(2, categoria.getId());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminar(int id) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "DELETE FROM Categorias WHERE id = ? ";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
