package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Tema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TemaDAO {

    public static boolean crear(Tema tema) {

        String sql = "INSERT INTO tema (curso_id, nombre) VALUES (?, ?)";

        try {
            Connection conexion = ConexionDB.obtenerConexion();

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, tema.getCursoId());
            sentencia.setString(2, tema.getNombre());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}