package org.example.DAOS;

import org.example.ConexionDB;
import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoMaterial;
import org.example.Modelos.Material;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    public static boolean crear(Material material) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "Insert into Material (archivoURL, tipo, fecha, docente) values (?,?,?,?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setObject(1, material.getArchivoUrl());
            sentencia.setObject(2, material.getTipo());
            sentencia.setObject(3, material.getFecha());
            sentencia.setString(4, material.getDocente());
            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Material> listarTodos() {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

                String sql = "SELECT * FROM Material order by fecha";
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet filas = sentencia.executeQuery();

            List<Material> retorno = new ArrayList<>();

            while (filas.next()) {
                int id = filas.getInt("id");
                TipoArchivo archivoUrl = (TipoArchivo) filas.getObject("archivoURL");
                TipoMaterial tipo = (TipoMaterial) filas.getObject("tipo");
                LocalDate fecha = (LocalDate) filas.getObject("fecha");
                String docente = filas.getString("docente");

                Material material = new Material(id, archivoUrl, tipo, fecha, docente);

                retorno.add(material);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Material material) {
        String sql = "UPDATE Material SET archivoUrl = ?, tipo = ?, fecha = ?, docente = ? WHERE id= ?";
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setObject(1, material.getArchivoUrl());
            sentencia.setObject(2, material.getTipo());
            sentencia.setObject(3, material.getFecha());
            sentencia.setObject(4, material.getDocente());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminar(int id) {
        try {
            Connection conexion = ConexionDB.obtenerConexion();

            String sql = "DELETE FROM Material WHERE id = ? ";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
