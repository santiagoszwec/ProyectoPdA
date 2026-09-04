package org.example.DAOS;

import org.example.ConexionDB;
import org.example.Modelos.Reporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public static List<Reporte> listarReportesAbiertos() {
        String sql = "SELECT * FROM reporte WHERE fecha_resolucion IS NULL ORDER BY fecha_reporte";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet filas = sentencia.executeQuery()) {

            List<Reporte> retorno = new ArrayList<>();
            while (filas.next()) {
                retorno.add(mapearReporte(filas));
            }
            return retorno;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Reporte buscarPorId(int id) {
        String sql = "SELECT * FROM reporte WHERE id = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            try (ResultSet filas = sentencia.executeQuery()) {
                if (filas.next()) {
                    return mapearReporte(filas);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Reporte mapearReporte(ResultSet filas) throws SQLException {
        int id = filas.getInt("id");
        String contenido = filas.getString("contenido");
        String motivo = filas.getString("motivo");
        String resolucion = filas.getString("resolucion");
        LocalDate fechaReporte = filas.getObject("fecha_reporte", LocalDate.class);
        LocalDate fechaResolucion = filas.getObject("fecha_resolucion", LocalDate.class);
        int publicacionId = filas.getInt("publicacion_id");
        return new Reporte(id, contenido, motivo, resolucion, fechaReporte, fechaResolucion, publicacionId);
    }
}
