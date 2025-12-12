package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;


public class Administradores {

    private int id;
    private String mail;
    private String rol;
    private String password;
    private int estado;

    // CONSTRUCTORES
    public Administradores(int id, String mail, String rol, String password, int estado) {
        this.id = id;
        this.mail = mail;
        this.rol = rol;
        this.password = password;
        this.estado = estado;
    }

    public Administradores() {}


    // SETTER & GETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                        rs.getInt("id"),
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


    //Metodo inicio de session
    public static int verificarSession(String mail, String password) {
        int autenticacion = 0;
        String sql = "SELECT COUNT(*) FROM Administradores WHERE mail = ? AND password = ? AND estado = 1";
        try (Connection connection = ConexionBD.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, mail);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    autenticacion = rs.getInt(1);
                    //Guardar el objeto ??? revisar mas tarde
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener administradores: " + e.getMessage());
        }
        return autenticacion;
    }

}


