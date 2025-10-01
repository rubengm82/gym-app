package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Administradores {

    private int id_user;
    private String mail;
    private String rol;
    private String password;
    private int estado;

    // CONSTRUCTORES
    public Administradores(int id_user, String mail, String rol, String password, int estado) {
        this.id_user = id_user;
        this.mail = mail;
        this.rol = rol;
        this.password = password;
        this.estado = estado;
    }

    // SETTER & GETTERS
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    // METODOS
    public static List<Administradores> obtenerTodosAdministradores() {
        List<Administradores> administradores = new ArrayList<>();
        String sql = "SELECT * FROM Administradores WHERE estado = 1";

        try (Connection connection = ConexionBD.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Administradores administrador = new Administradores(
                        rs.getInt("id_user"),
                        rs.getString("mail"),
                        rs.getString("rol"),
                        rs.getString("password"),
                        rs.getInt("estado"));
                administradores.add(administrador);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener administradores: " + e.getMessage());
        }
        return administradores;
    }

}
