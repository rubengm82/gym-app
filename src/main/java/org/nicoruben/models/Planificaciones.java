package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Planificaciones {

    private int id;
    private String dia;
    private String hora_inicio;
    private String hora_fin;
    private Clases clase;
    private Instructores instructor;
    private int estado;

    // CONSTRUCTORES
    public Planificaciones(int id, String dia, String hora_inicio, String hora_fin, Clases clase, Instructores instructor, int estado) {
        this.id = id;
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


    public Planificaciones() {}


    // SETTER & GETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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


    // METODOS
    @Override
    public String toString() {
        return "Planificaciones{" +
                "id=" + id +
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
            SELECT p.id AS plan_id, p.dia, p.hora_inicio, p.hora_fin, p.estado,
                    c.id AS clase_id, c.nombre AS nombre_clase, c.aforo, c.descripcion AS desc_clase, c.estado AS estado_clase,
                    i.id AS inst_id, i.nombre AS nombre_inst, i.apellido1, i.apellido2, i.DNI, i.telefono, i.estado AS estado_inst
            FROM Planificaciones p
            JOIN Clases c ON p.fk_id_clase = c.id
            JOIN Instructores i ON p.fk_id_inst = i.id
            WHERE p.dia = ?
            ORDER BY p.hora_inicio;
            """;

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dia);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Clases clase = new Clases(
                        rs.getInt("clase_id"),
                        rs.getString("nombre_clase"),
                        rs.getInt("aforo"),
                        rs.getString("desc_clase"),
                        rs.getInt("estado_clase")
                );
                Instructores instructor = new Instructores(
                        rs.getInt("inst_id"),
                        rs.getString("nombre_inst"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("DNI"),
                        rs.getString("telefono"),
                        rs.getInt("estado_inst")
                );
                Planificaciones planificacion = new Planificaciones(
                        rs.getInt("plan_id"),
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
        String sql = "DELETE FROM Planificaciones WHERE id = ?";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

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
                "AND estado = 1 " +
                "AND (? < hora_fin AND ? > hora_inicio)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, dia);
            ps.setTime(2, java.sql.Time.valueOf(horaInicio));
            ps.setTime(3, java.sql.Time.valueOf(horaFin));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                    System.out.println("Conflictos encontrados: " + total);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar planificación: " + e.getMessage());
        }

        return total;
    }


    public static void insertPlanificaciones(String dia, LocalTime horaInicio, LocalTime horaFin, int id_clase, int id_instructor){
        String sql = "INSERT INTO Planificaciones (id, dia, hora_inicio, hora_fin, fk_id_clase, fk_id_inst, estado) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
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

    // Obtener todas las planificaciones en la base de datos. Necesario este metodo para Reservas
    public static List<Planificaciones> obtenerTodasPlanificacionesActivas() {
        List<Planificaciones> planificaciones = new ArrayList<>();
        String sql = "SELECT p.id AS plan_id, p.dia, p.hora_inicio, p.hora_fin, p.estado, " +
                "cl.id AS clase_id, cl.nombre AS nombre_clase, " +
                "i.id AS inst_id, i.nombre AS nombre_instructor " +
                "FROM Planificaciones p " +
                "JOIN Clases cl ON p.fk_id_clase = cl.id " +
                "JOIN Instructores i ON p.fk_id_inst = i.id " +
                "WHERE p.estado = 1";

        try (Connection con = ConexionBD.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Clase
                Clases clase = new Clases();
                clase.setId(rs.getInt("clase_id"));
                clase.setNombre(rs.getString("nombre_clase"));

                // Instructor
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt("inst_id"));
                instructor.setNombre(rs.getString("nombre_instructor"));

                // Planificación
                Planificaciones planificacion = new Planificaciones(
                        rs.getInt("plan_id"),
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
            System.err.println("Error al obtener planificaciones activas: " + e.getMessage());
        }

        return planificaciones;
    }

}
