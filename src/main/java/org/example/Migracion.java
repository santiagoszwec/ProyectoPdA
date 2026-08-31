package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Migracion {

    public static void migrar() {
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement()) {

            String[] tablas = {
                "CREATE TABLE IF NOT EXISTS usuario (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  nombre VARCHAR(100) NOT NULL," +
                "  correo VARCHAR(100) NOT NULL," +
                "  anio_de_generacion INT NOT NULL," +
                "  rol VARCHAR(20) NOT NULL," +
                "  contrasenia INT NOT NULL" +
                ")",

                "CREATE TABLE IF NOT EXISTS curso (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  nombre VARCHAR(100) NOT NULL," +
                "  semestre INT NOT NULL," +
                "  anio INT NOT NULL," +
                "  creditos INT NOT NULL" +
                ")",

                "CREATE TABLE IF NOT EXISTS publicacion (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  mensaje TEXT NOT NULL," +
                "  imagen_url VARCHAR(255)," +
                "  fecha_publicacion DATE NOT NULL," +
                "  categoria VARCHAR(30) NOT NULL" +
                ")",

                "CREATE TABLE IF NOT EXISTS mensaje (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  publicacion_id INT NOT NULL," +
                "  FOREIGN KEY (publicacion_id) REFERENCES publicacion(id)" +
                ")",

                "CREATE TABLE IF NOT EXISTS duda (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  publicacion_id INT NOT NULL," +
                "  estado VARCHAR(20) NOT NULL," +
                "  FOREIGN KEY (publicacion_id) REFERENCES publicacion(id)" +
                ")",

                "CREATE TABLE IF NOT EXISTS comentario (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  mensaje TEXT NOT NULL," +
                "  imagen_url VARCHAR(255)," +
                "  fecha_publicacion DATE NOT NULL," +
                "  comentario_padre_id INT," +
                "  FOREIGN KEY (comentario_padre_id) REFERENCES comentario(id)" +
                ")",

                "CREATE TABLE IF NOT EXISTS material (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  archivo_url VARCHAR(20) NOT NULL," +
                "  tipo VARCHAR(20) NOT NULL," +
                "  fecha DATE NOT NULL," +
                "  docente VARCHAR(100) NOT NULL" +
                ")",

                "CREATE TABLE IF NOT EXISTS notificacion (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  fecha DATE NOT NULL," +
                "  tipo VARCHAR(20) NOT NULL," +
                "  mensaje TEXT NOT NULL" +
                ")",

                "CREATE TABLE IF NOT EXISTS progreso (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  estado VARCHAR(20) NOT NULL" +
                ")",

                "CREATE TABLE IF NOT EXISTS registro_de_horas (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  cantidad_de_horas FLOAT NOT NULL," +
                "  fecha DATE NOT NULL" +
                ")",

                "CREATE TABLE IF NOT EXISTS reporte (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  contenido TEXT NOT NULL," +
                "  motivo TEXT NOT NULL," +
                "  resolucion TEXT," +
                "  fecha_reporte DATE NOT NULL," +
                "  fecha_resolucion DATE" +
                ")"
            };

            for (String sql : tablas) {
                stmt.executeUpdate(sql);
                String nombreTabla = sql.split("IF NOT EXISTS ")[1].split(" \\(")[0];
                System.out.println("Tabla creada: " + nombreTabla);
            }

            System.out.println("Migracion completada exitosamente.");

        } catch (SQLException e) {
            System.err.println("Error en la migracion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        migrar();
    }
}

