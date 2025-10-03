package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
        String sql = "INSERT INTO Clientes (id_cliente, nombre, apellido1, apellido2, IBAN, email, telefono, estado) " +
                     "VALUES (NULL, '" + nombre + "', '" + apellido1 + "', '" + apellido2 + "', '" + IBAN + "', '" + mail + "', '" + telefono + "', " + estado + ")";
        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement()
        ) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

}