package org.nicoruben;

import atlantafx.base.theme.NordLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nicoruben.models.Administradores;
import org.nicoruben.services.UtilsGlobal;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // CONSTANTES
        final int APP_WIDTH = 1600;
        final int APP_HEIGHT = 900;

        // 🔹 Aplica el tema Nord Light antes de crear la Scene
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        // Carga el FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login/login.fxml"));
        Parent root = fxmlLoader.load();

        // La App tendra solo un stage con muchas scenes. El tamaño sera 1600px x 900px
        // Crea la Stage en el centro de la pantalla
        stage.setResizable(false);
        stage.setWidth(APP_WIDTH);
        stage.setHeight(APP_HEIGHT);
        stage.centerOnScreen();

        // Crea la Scene
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        stage.setTitle("GYM APP");
        stage.setScene(scene);
        stage.show();

        // Comprobación de conexion con la BBDD
        UtilsGlobal.testConexionBBDD();

        // Purgado de reservas del dia anterior al actual
         UtilsGlobal.actualizarReservasExpiradas();
    }

    public static void main(String[] args) {
        launch();
    }

}