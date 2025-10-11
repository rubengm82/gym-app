package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Clases {
    private int id_clase;
    private String nombre;
    private LocalTime duracion;
    private String dia;
    private int aforo;
    private int fkIdInst;
    private String descripcion;
    private int estado;

    private String nombreInstructorCompleto;

    // CONSTRUCTORES
    public Clases(int id_clase, String nombre, LocalTime duracion, String dia, int aforo, int fkIdInst, String descripcion, int estado) {
        this.id_clase = id_clase;
        this.nombre = nombre;
        this.duracion = duracion;
        this.dia = dia;
        this.aforo = aforo;
        this.fkIdInst = fkIdInst;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // SETTERS & GETTERS
    public int getId_clase() { return id_clase; }
    public void setId_clase(int id_clase) { this.id_clase = id_clase; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalTime getDuracion() { return duracion; }
    public void setDuracion(LocalTime duracion) { this.duracion = duracion; }
    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }
    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }
    public int getFkIdInst() { return fkIdInst; }
    public void setFkIdInst(int fkIdInst) { this.fkIdInst = fkIdInst; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    // Getters y setters EXTRAS
    public String getNombreInstructorCompleto() {
        return nombreInstructorCompleto;
    }
    public void setNombreInstructorCompleto(String nombreInstructorCompleto) {
        this.nombreInstructorCompleto = nombreInstructorCompleto;
    }

    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////

    // LEER
    public static List<Clases> obtenerTodasClases() {
        List<Clases> clases = new ArrayList<>();
        String sql = "SELECT * FROM Clases";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Clases clase = new Clases(
                        rs.getInt("id_clase"),
                        rs.getString("nombre"),
                        rs.getObject("duracion", java.time.LocalTime.class),
                        rs.getString("dia"),
                        rs.getInt("aforo"),
                        rs.getInt("fk_id_inst"),
                        rs.getString("descripcion"),
                        rs.getInt("estado"));
                clases.add(clase);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clases: " + e.getMessage());
        }
        return clases;
    }

    // EDITAR - UPDATE ESTADO OCULTO
    public static void actualizarEstado(int id_clase, int nuevoEstado) {
        try (Connection connection = ConexionBD.conectar();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE Clases SET estado = ? WHERE id_clase = ?")) {

            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, id_clase);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
