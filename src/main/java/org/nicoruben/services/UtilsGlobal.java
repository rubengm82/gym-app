package org.nicoruben.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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
        Scene scene = new Scene(FXMLLoader.load(UtilsGlobal.class.getResource(fxmlFile)), 640, 480);
        stage.setScene(scene);
    }

    /**
     * Test - Conexion a la BBDD
     */
    public static void testConexionBBDD() {
        try (Connection connection = ConexionBD.conectar()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("* Conexión exitosa a la base de datos!");
            } else {
                System.out.println("* No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("* Error al conectar: " + e.getMessage());
        }
    }

    /**
     * Purga los dias anteriores de las reservas del día actual al iniciar la App
     */
    public static void actualizarReservasExpiradas() {
        String sql = "UPDATE Reservas SET estado = -1 WHERE fecha_reserva < CURDATE() AND (estado = 1 OR estado = 2)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int actualizadas = ps.executeUpdate();
            System.out.println("Reservas caducadas actualizadas a estado -1: " + actualizadas);

        } catch (SQLException e) {
            System.err.println("Error al actualizar reservas caducadas: " + e.getMessage());
        }
    }

}
