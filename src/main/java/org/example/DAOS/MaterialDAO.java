package org.example.DAOS;

import org.example.ConexionDB;
import org.example.ENUMS.TipoArchivo;
import org.example.ENUMS.TipoMaterial;
import org.example.Modelos.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    public static boolean crear(Material material) {

        String sql = "INSERT INTO material (id, archivo_url, tipo_material, tipo_archivo) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, material.getId());
            sentencia.setString(2, material.getArchivoUrl());
            sentencia.setString(3, material.getTipoMaterial().toString());
            sentencia.setString(4, material.getTipoArchivo().toString());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Material> listarTodos() {

        String sql = "SELECT * FROM material";

        try (Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet filas = sentencia.executeQuery()) {

            List<Material> retorno = new ArrayList<>();

            while (filas.next()) {

                int id = filas.getInt("id");

                String archivoUrl =
                        filas.getString("archivo_url");

                TipoMaterial tipoMaterial =
                        TipoMaterial.valueOf(filas.getString("tipo_material"));

                TipoArchivo tipoArchivo =
                        TipoArchivo.valueOf(filas.getString("tipo_archivo"));

                Material material = new Material(archivoUrl, tipoMaterial, tipoArchivo);

                retorno.add(material);
            }

            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizar(Material material) {

        String sql = "UPDATE material SET archivo_url = ?, tipo_material = ?, tipo_archivo = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, material.getArchivoUrl());
            sentencia.setString(2, material.getTipoMaterial().toString());
            sentencia.setString(3, material.getTipoArchivo().toString());
            sentencia.setInt(4, material.getId());

            int filasAfectadas = sentencia.executeUpdate();

            return filasAfectadas == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean eliminar(int id) {

        String sql = "DELETE FROM material WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}