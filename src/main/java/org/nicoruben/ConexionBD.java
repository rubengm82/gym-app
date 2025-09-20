package org.nicoruben;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/***BBDD***";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    public static Connection connectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
