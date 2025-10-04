package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Clientes {

    private int id_cliente;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String IBAN;
    private String mail;
    private String telefono;
    private int estado;

    // CONSTRUCTORES
    public Clientes(int id_cliente, String nombre, String apellido1, String apellido2, String IBAN, String mail, String telefono, int estado) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.IBAN = IBAN;
        this.mail = mail;
        this.telefono = telefono;
        this.estado = estado;
    }

    // SETTER & GETTERS
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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



    // METODOS
    public static void insertarCliente(String nombre, String apellido1, String apellido2, String IBAN, String mail, String telefono, int estado) {
        String sql = "INSERT INTO Clientes (id_cliente, nombre, apellido1, apellido2, IBAN, mail, telefono, estado) " +
                     "VALUES (NULL, '" + nombre + "', '" + apellido1 + "', '" + apellido2 + "', '" + IBAN + "', '" + mail + "', '" + telefono + "', " + estado + ")";
        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement()
        ) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    public static boolean existeMail(String mail) {
        boolean existe = false;
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM Clientes WHERE mail = ?")) {
            ps.setString(1, mail);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static List<Clientes> obtenerTodosClientes() {
        List<Clientes> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Clientes cliente = new Clientes(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("IBAN"),
                        rs.getString("mail"),
                        rs.getString("telefono"),
                        rs.getInt("estado"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
        }
        return clientes;
    }

    public static void actualizarEstado(int id_cliente, int nuevoEstado) {
        try (Connection connection = ConexionBD.conectar();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE Clientes SET estado = ? WHERE id_cliente = ?")) {

            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, id_cliente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}