package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Clientes {

    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String IBAN;
    private String mail;
    private String telefono;
    private int estado;

    // CONSTRUCTORES
    public Clientes(int id, String nombre, String apellido1, String apellido2, String IBAN, String mail, String telefono, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.IBAN = IBAN;
        this.mail = mail;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Clientes() {}


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


    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////
    public static void insertarCliente(String nombre, String apellido1, String apellido2, String IBAN, String mail, String telefono, int estado, String hashedPassword) {
        String sql = "INSERT INTO Clientes (id, nombre, apellido1, apellido2, IBAN, mail, telefono, estado, password) " +
                "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConexionBD.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido1);
            stmt.setString(3, apellido2);
            stmt.setString(4, IBAN);
            stmt.setString(5, mail);
            stmt.setString(6, telefono);
            stmt.setInt(7, estado);
            stmt.setString(8, hashedPassword);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }


    // Comprobar si exite el email
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

    // LEER
    public static List<Clientes> obtenerTodosClientes() {
        List<Clientes> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Clientes cliente = new Clientes(
                        rs.getInt("id"),
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

    // EDITAR - UPDATE ESTADO OCULTO
    public static void actualizarEstado(int id, int nuevoEstado) {
        try (Connection connection = ConexionBD.conectar();
              PreparedStatement stmt = connection.prepareStatement(
                      "UPDATE Clientes SET estado = ? WHERE id = ?")) {

            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // EDITAR - UPDATE
    public static boolean actualizarCliente(Clientes cliente) {
        boolean exito = false;

        String sql = "UPDATE Clientes SET nombre = ?, apellido1 = ?, apellido2 = ?, IBAN = ?, mail = ?, telefono = ?, estado = ? " +
                        "WHERE id = ?";

        try (Connection connection = ConexionBD.conectar();
              PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido1());
            ps.setString(3, cliente.getApellido2());
            ps.setString(4, cliente.getIBAN());
            ps.setString(5, cliente.getMail());
            ps.setString(6, cliente.getTelefono());
            ps.setInt(7, cliente.getEstado());
            ps.setInt(8, cliente.getId());

            exito = ps.executeUpdate() > 0; // true si se actualizó al menos una fila

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }

        return exito;
    }

    // EDITAR - UPDATE CON CONTRASEÑA
    public static boolean actualizarClienteConPassword(Clientes cliente, String hashedPassword) {
        boolean exito = false;

        String sql = "UPDATE Clientes SET nombre = ?, apellido1 = ?, apellido2 = ?, IBAN = ?, mail = ?, telefono = ?, estado = ?, password = ? " +
                "WHERE id = ?";

        try (Connection connection = ConexionBD.conectar();
              PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido1());
            ps.setString(3, cliente.getApellido2());
            ps.setString(4, cliente.getIBAN());
            ps.setString(5, cliente.getMail());
            ps.setString(6, cliente.getTelefono());
            ps.setInt(7, cliente.getEstado());
            ps.setString(8, hashedPassword);
            ps.setInt(9, cliente.getId());

            exito = ps.executeUpdate() > 0; // true si se actualizó al menos una fila

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente con contraseña: " + e.getMessage());
        }

        return exito;
    }

    public static int contarClientesPorEstado(int estado) {
        int total = 0;

        String sql = "SELECT COUNT(*) FROM Clientes WHERE estado = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al contar clientes: " + e.getMessage());
        }

        return total;
    }

}