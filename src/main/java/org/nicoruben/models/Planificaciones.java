package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public Planificaciones() {}

    public Planificaciones(int id_planificacion, String dia, String hora_inicio, String hora_fin, Clases clase, Instructores instructor, int estado) {
        this.id_planificacion = id_planificacion;
        this.dia = dia;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.clase = clase;
        this.instructor = instructor;
        this.estado = estado;
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

    public static List<Planificaciones> PlanificacionesLunes() {
        List<Planificaciones> planificaciones = new ArrayList<>();
        String sql = "SELECT * FROM Planificaciones WHERE dia = 'Lunes' ORDER BY hora_inicio;";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String dia = rs.getString("dia");
                String horaInicio = rs.getTime("hora_inicio").toString();
                String horaFin = rs.getTime("hora_fin").toString();

                int idClase = rs.getInt("fk_id_clase");
                int idInst = rs.getInt("fk_id_inst");

                // Supongamos que tienes métodos para obtener los objetos desde su ID:
                Clases clase = ClasesDAO.obtenerClasePorId(idClase);
                Instructores instructor = InstructoresDAO.obtenerInstructorPorId(idInst);

                Planificaciones planificacion = new Planificaciones(
                        dia, horaInicio, horaFin, clase, instructor
                );

                planificaciones.add(planificacion);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener planificaciones: " + e.getMessage());
        }

        return planificaciones;
    }


    public static List<Clases> obtenerClasePorId(int id) {
        List<Clases> clases = new ArrayList<>();
        String sql = "SELECT * FROM Clases WHERE id_clase ="+id+";";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Clases clase = new Clases(
                        rs.getInt("id_clase"),
                        rs.getString("nombre"),
                        rs.getInt("aforo"),
                        rs.getString("descripcion"),
                        rs.getInt("estado"));
                clases.add(clase);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clases: " + e.getMessage());
        }
        return clases;
    }
}
