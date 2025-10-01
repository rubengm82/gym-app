package org.nicoruben.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // alwaysdata.com - DataBase MySQL Free
    // Base de Datos: rubengmpineda_gym
    // Usuario: 431396
    // Password: LaPineda2526

    // URL de conexión: jdbc:mysql://<host>:<puerto>/<nombre_base>
    private static final String URL = "jdbc:mysql://mysql-rubengmpineda.alwaysdata.net:3306/rubengmpineda_gym?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "431396_gym";
    private static final String PASSWORD = "LaPineda2526";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

}