package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Clases {
    private int id_clase;
    private String nombre;
    private int aforo;
    private String descripcion;
    private int estado;

    private String nombreInstructorCompleto;

    // CONSTRUCTORES
    public Clases(int id_clase, String nombre, int aforo, String descripcion, int estado) {
        this.id_clase = id_clase;
        this.nombre = nombre;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Clases() {}

    // SETTERS & GETTERS
    public int getId_clase() { return id_clase; }
    public void setId_clase(int id_clase) { this.id_clase = id_clase; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }
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

    @Override
    public String toString() {
        return this.nombre;
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

    // INSERTAR - NUEVA CLASE
    public static void insertarClase(String nombre, int aforo, String descripcion, int estado) {
        String sql = "INSERT INTO Clases (nombre, aforo, descripcion, estado) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, aforo);
            ps.setString(3, descripcion);
            ps.setInt(4, estado);

            ps.executeUpdate();
            System.out.println("Clase insertada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar clase: " + e.getMessage());
        }
    }

    // ACTUALIZAR DATOS DE LA CLASE
    public static boolean actualizarClase(Clases clase) {
        boolean exito = false;

        // SQL para actualizar la tabla Clases
        String sql = "UPDATE Clases SET nombre = ?, aforo = ?, descripcion = ?, estado = ? WHERE id_clase = ?";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, clase.getNombre());
            ps.setInt(2, clase.getAforo());
            ps.setString(3, clase.getDescripcion());
            ps.setInt(4, clase.getEstado());
            ps.setInt(5, clase.getId_clase());

            exito = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar clase: " + e.getMessage());
        }

        return exito;
    }


}
