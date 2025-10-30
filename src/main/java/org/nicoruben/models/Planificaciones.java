package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Planificaciones {

    private int id_planificacion;
    private String dia;
    private String hora_inicio;
    private String hora_fin;
    private Clases clase;
    private Instructores instructor;
    private int estado;


    public Planificaciones(int id_planificacion, String dia, String hora_inicio, String hora_fin, Clases clase, Instructores instructor, int estado) {
        this.id_planificacion = id_planificacion;
        this.dia = dia;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.clase = clase;
        this.instructor = instructor;
        this.estado = estado;
    }


    public Planificaciones(String dia, String hora_inicio, String hora_fin, Clases clase, Instructores instructor) {
        this.dia = dia;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.clase = clase;
        this.instructor = instructor;
        this.estado = 1;
    }


    public int getId_planificacion() { return id_planificacion; }
    public void setId_planificacion(int id_planificacion) { this.id_planificacion = id_planificacion; }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public String getHora_inicio() { return hora_inicio; }
    public void setHora_inicio(String hora_inicio) { this.hora_inicio = hora_inicio; }

    public String getHora_fin() { return hora_fin; }
    public void setHora_fin(String hora_fin) { this.hora_fin = hora_fin; }

    public Clases getClase() { return clase; }
    public void setClase(Clases clase) { this.clase = clase; }

    public Instructores getInstructor() { return instructor; }
    public void setInstructor(Instructores instructor) { this.instructor = instructor; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Planificaciones{" +
                "id_planificacion=" + id_planificacion +
                ", dia='" + dia + '\'' +
                ", hora_inicio='" + hora_inicio + '\'' +
                ", hora_fin='" + hora_fin + '\'' +
                ", clase=" + (clase != null ? clase.getNombre() : "null") +
                ", instructor=" + (instructor != null ? instructor.getNombre() : "null") +
                ", estado=" + estado +
                '}';
    }


    public static List<Planificaciones> obtenerPlanificacionesPorDia(String dia) {
        List<Planificaciones> planificaciones = new ArrayList<>();
        String sql = """
            SELECT p.*, 
                   c.id_clase, c.nombre AS nombre_clase, c.aforo, c.descripcion AS desc_clase, c.estado AS estado_clase,
                   i.id_inst, i.nombre AS nombre_inst, i.apellido1, i.apellido2, i.DNI, i.telefono, i.estado AS estado_inst
            FROM Planificaciones p
            JOIN Clases c ON p.fk_id_clase = c.id_clase
            JOIN Instructores i ON p.fk_id_inst = i.id_inst
            WHERE p.dia = ?
            ORDER BY p.hora_inicio;
            """;

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dia);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Clases clase = new Clases(
                        rs.getInt("id_clase"),
                        rs.getString("nombre_clase"),
                        rs.getInt("aforo"),
                        rs.getString("desc_clase"),
                        rs.getInt("estado_clase")
                );
                Instructores instructor = new Instructores(
                        rs.getInt("id_inst"),
                        rs.getString("nombre_inst"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("DNI"),
                        rs.getString("telefono"),
                        rs.getInt("estado_inst")
                );
                Planificaciones planificacion = new Planificaciones(
                        rs.getInt("id_planificacion"),
                        rs.getString("dia"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fin"),
                        clase,
                        instructor,
                        rs.getInt("estado")
                );
                planificaciones.add(planificacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener planificaciones: " + e.getMessage());
        }

        return planificaciones;
    }


    public static void delPlanificacion(int id) {
        String sql = "DELETE FROM Planificaciones WHERE id_planificacion = ?";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate(); // ✅ executeUpdate para DELETE

            if (filasEliminadas > 0) {
                System.out.println("ELIMINADO correctamente.");

            } else {
                System.out.println("No se encontró la planificación con ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar planificación: " + e.getMessage());
        }
    }


    public static int verificarPlanificacion(String dia, LocalTime horaInicio, LocalTime horaFin) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total " +
                "FROM Planificaciones " +
                "WHERE dia = ? " +
                "AND (? < hora_fin AND ? > hora_inicio)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, dia);
            ps.setTime(3, java.sql.Time.valueOf(horaFin));
            ps.setTime(2, java.sql.Time.valueOf(horaInicio));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
                System.out.println("Conflictos encontrados: " + total);
                return total;

            }

        } catch (SQLException e) {
            System.err.println("Error al verificar planificación: " + e.getMessage());
        }

        return total;

    }


    public static void insertPlanificaciones(String dia, LocalTime horaInicio, LocalTime horaFin, int id_clase, int id_instructor){
        String sql = "INSERT INTO Planificaciones (id_planificacion, dia, hora_inicio, hora_fin, fk_id_clase, fk_id_inst, estado) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dia);
            ps.setTime(2, java.sql.Time.valueOf(horaInicio));
            ps.setTime(3, java.sql.Time.valueOf(horaFin));
            ps.setInt(4, id_clase);
            ps.setInt(5, id_instructor);
            ps.setInt(6, 1);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar clase: " + e.getMessage());
        }
    }
}
