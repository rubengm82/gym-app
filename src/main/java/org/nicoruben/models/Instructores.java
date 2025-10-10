package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Instructores {
    private int id;
    private String nombre;
    private String apellido;
    private String apellidos2;
    private int telefono;
    private String DNI;
    private int estado;

    // Constructor
    public Instructores(int id, String nombre, String apellido, String apellidos2, int telefono, String DNI, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.apellidos2 = apellidos2;
        this.telefono = telefono;
        this.DNI = DNI;
        this.estado = estado;
    }

    // Sobrecarga del constructor
    public Instructores(String nombre, String apellido, String apellidos2, int telefono, String DNI, int estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.apellidos2 = apellidos2;
        this.telefono = telefono;
        this.DNI = DNI;
        this.estado = estado;
    }

    // Método para crear instructor
    public static void crearInstructor(String nombre, String apellido, String apellidos2, int telefono, String DNI, int estado) {
        String sql = "INSERT INTO Instructores (nombre, apellido1, apellido2, telefono, DNI, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, apellidos2);
            stmt.setInt(4, telefono);
            stmt.setString(5, DNI);
            stmt.setInt(6, estado);

            stmt.executeUpdate();
            System.out.println("insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
    }


    //metodo para listar instructores

    public static List<Instructores> obtenerInstructores(){
        List<Instructores> instructores = new ArrayList<>();

        String sql = "SELECT * FROM Instructores";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Instructores instructor = new Instructores(
                        rs.getInt("id_inst"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getInt("telefono"),
                        rs.getString("DNI"),
                        rs.getInt("estado"));
               instructores.add(instructor);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
        }
        return instructores;

    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getApellidos2() { return apellidos2; }
    public int getTelefono() { return telefono; }
    public String getDNI() { return DNI; }
    public int getEstado() { return estado; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setApellidos2(String apellidos2) { this.apellidos2 = apellidos2; }
    public void setTelefono(int telefono) { this.telefono = telefono; }
    public void setDNI(String DNI) { this.DNI = DNI; }
    public void setEstado(int estado) { this.estado = estado; }
}
