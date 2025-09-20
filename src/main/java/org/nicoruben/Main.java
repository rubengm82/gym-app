package org.nicoruben;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Parent;

// Importa Atlantafx Nord Light
import atlantafx.base.theme.NordLight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @FXML
    private Label label;

    @Override
    public void start(Stage stage) throws Exception {
        // 🔹 Aplica el tema Nord Light antes de crear la Scene
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        // Carga tu FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/nicoruben/main.fxml"));
        Parent root = fxmlLoader.load();

        // Crea la Scene
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Gym APP");
        stage.setScene(scene);
        stage.show();

        // Prueba de conexion a BBDD
        testConexionBBDD();

        // Prueba Query a BBDD
        testQueryBBDD();
    }

    @FXML
    protected void onButtonClick() {
        label.setText("¡Botón pulsado desde FXML!");
    }

    private static void testQueryBBDD() {
        try (Connection conexion = ConexionBD.conectar();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nombre, apellido1, apellido2 FROM personas")) {

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

    private static void testConexionBBDD() {
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


    public static void main(String[] args) {
        launch();
    }
}
