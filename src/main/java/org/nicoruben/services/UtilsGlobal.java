package org.nicoruben.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UtilsGlobal {

    /**
     * Cambia la escena del Stage actual.
     *
     * @param sourceNode Nodo que disparo el evento (ej: Button)
     * @param fxmlFile   Nombre del archivo FXML a cargar, ejemplo "xxx.fxml"
     * @throws IOException Si no se encuentra el FXML
     */
    public static void goToSceneWithButton(Node sourceNode, String fxmlFile) throws IOException {
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(UtilsGlobal.class.getResource("/nicoruben/views/" + fxmlFile + ".fxml")), 640, 480);
        stage.setScene(scene);
    }

    /**
     * Test - Conexion a la BBDD
     */
    public static void testConexionBBDD() {
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion != null && !conexion.isClosed()) {
                System.out.println("✅ Conexión exitosa a la base de datos!");
            } else {
                System.out.println("❌ No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
        }
    }

    /**
     * Test - Query a BBDD
     */
    public static void testQueryBBDD() {
        try (Connection conexion = ConexionBD.conectar();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nombre, apellido1, apellido2 FROM Z_testDB")) {

            System.out.println("Lista de personas:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");

                System.out.println(id + ": " + nombre + " " + apellido1 + " " + apellido2);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en la consulta: " + e.getMessage());
        }
    }


}
