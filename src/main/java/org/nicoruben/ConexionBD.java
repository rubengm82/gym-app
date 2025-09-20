package org.nicoruben;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // URL de conexión: jdbc:mysql://<host>:<puerto>/<nombre_base>
    private static final String URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7799378";
    private static final String USUARIO = "sql7799378";
    private static final String PASSWORD = "FnXykGLe7T";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
