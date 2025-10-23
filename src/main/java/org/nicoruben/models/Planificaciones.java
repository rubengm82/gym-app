package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Planificaciones {

    private int id_planificacion;
    private String dia;
    private String hora_inicio;
    private String hora_fin;
    private Clases clase;
    private Instructores instructor;
    private int estado;

    // CONSTRUCTORES
    public Planificaciones(int id_planificacion, String dia, String hora_inicio, String hora_fin, Clases clase, Instructores instructor, int estado) {
        this.id_planificacion = id_planificacion;
        this.dia = dia;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.clase = clase;
        this.instructor = instructor;
        this.estado = estado;
    }

    public Planificaciones() {}


    // SETTER & GETTERS
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


    // METODOS
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


    // RUBEN ---- Insertar planificación en la base de datos. Necesario este metodo para Reservas
    public static boolean insertarPlanificacion(Planificaciones planificacion) {
        String sql = "INSERT INTO Planificaciones (dia, hora_inicio, hora_fin, fk_id_clase, fk_id_inst, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        boolean exito = false; // Variable para controlar el resultado

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, planificacion.getDia());
            ps.setString(2, planificacion.getHora_inicio());
            ps.setString(3, planificacion.getHora_fin());
            ps.setInt(4, planificacion.getClase().getId_clase());
            ps.setInt(5, planificacion.getInstructor().getId());
            ps.setInt(6, planificacion.getEstado());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        planificacion.setId_planificacion(rs.getInt(1));
                    }
                }
                exito = true; // Actualizar variable
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar planificación: " + e.getMessage());
        }

        return exito; // Único return
    }


    // RUBEN ---- Obtener todas las planificaciones en la base de datos. Necesario este metodo para Reservas
    public static List<Planificaciones> obtenerTodasPlanificacionesActivas() {
        List<Planificaciones> planificaciones = new ArrayList<>();
        String sql = "SELECT p.id_planificacion, p.dia, p.hora_inicio, p.hora_fin, p.estado, " +
                "cl.id_clase, cl.nombre AS nombre_clase, " +
                "i.id_inst, i.nombre AS nombre_instructor " +
                "FROM Planificaciones p " +
                "JOIN Clases cl ON p.fk_id_clase = cl.id_clase " +
                "JOIN Instructores i ON p.fk_id_inst = i.id_inst " +
                "WHERE p.estado = 1";

        try (Connection con = ConexionBD.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Clase
                Clases clase = new Clases();
                clase.setId_clase(rs.getInt("id_clase"));
                clase.setNombre(rs.getString("nombre_clase"));

                // Instructor
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt("id_inst"));
                instructor.setNombre(rs.getString("nombre_instructor"));

                // Planificación
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
            System.err.println("Error al obtener planificaciones activas: " + e.getMessage());
        }

        return planificaciones;
    }

}
