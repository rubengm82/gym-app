package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Instructores {

    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String DNI;
    private String telefono;
    private int estado;


    // CONSTRUCTORES
    public Instructores(int id, String nombre, String apellido1, String apellido2, String DNI, String telefono, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.DNI = DNI;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Instructores(String nombre, String apellido1, String apellido2, String DNI, String telefono, int estado) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.DNI = DNI;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Instructores() {}


    // SETTER & GETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }




    @Override
    public String toString() {
        return this.nombre + ' '+ this.apellido1;
    }

    // =====================
    // MÉTODOS DE BASE DE DATOS
    // =====================

    // INSERTAR NUEVO INSTRUCTOR
    public static void insertarInstructor(String nombre, String apellido1, String apellido2, String DNI, String telefono, int estado) {
        String sql = "INSERT INTO Instructores (nombre, apellido1, apellido2, DNI, telefono, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido1);
            ps.setString(3, apellido2);
            ps.setString(4, DNI);
            ps.setString(5, telefono);
            ps.setInt(6, estado);

            ps.executeUpdate();
            System.out.println("Instructor insertado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar instructor: " + e.getMessage());
        }
    }

    // OBTENER TODOS LOS INSTRUCTORES
    public static List<Instructores> obtenerInstructores() {
        List<Instructores> instructores = new ArrayList<>();
        String sql = "SELECT * FROM Instructores";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Instructores instructor = new Instructores(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("DNI"),
                        rs.getString("telefono"),
                        rs.getInt("estado")
                );
                instructores.add(instructor);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener instructores: " + e.getMessage());
        }

        return instructores;
    }

    // ACTUALIZAR ESTADO (BAJA / ALTA)
    public static void actualizarEstado(int id, int nuevoEstado) {
        String sql = "UPDATE Instructores SET estado = ? WHERE id = ?";

        try (Connection connection = ConexionBD.conectar();
              PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar estado del instructor: " + e.getMessage());
        }
    }

    // ACTUALIZAR DATOS DE INSTRUCTOR
    public static boolean actualizarInstructor(Instructores instructor) {
        boolean exito = false;
        String sql = "UPDATE Instructores SET nombre = ?, apellido1 = ?, apellido2 = ?, DNI = ?, telefono = ?, estado = ? " +
                "WHERE id = ?";

        try (Connection connection = ConexionBD.conectar();
              PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, instructor.getNombre());
            ps.setString(2, instructor.getApellido1());
            ps.setString(3, instructor.getApellido2());
            ps.setString(4, instructor.getDNI());
            ps.setString(5, instructor.getTelefono());
            ps.setInt(6, instructor.getEstado());
            ps.setInt(7, instructor.getId());

            exito = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar instructor: " + e.getMessage());
        }

        return exito;
    }

    // COMPROBAR SI EXISTE UN DNI REPETIDO
    public static boolean existeDNI(String dni) {
        boolean existe = false;
        String sql = "SELECT 1 FROM Instructores WHERE DNI = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = true;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar DNI del instructor: " + e.getMessage());
        }

        return existe;
    }

}
