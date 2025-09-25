package org.nicoruben;

import atlantafx.base.theme.NordLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        final int APP_WIDTH = 1600;
        final int APP_HEIGHT = 900;

        // 🔹 Aplica el tema Nord Light antes de crear la Scene
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        // Carga el FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/nicoruben/views/main.fxml"));
        Parent root = fxmlLoader.load();

        // Crea la Stage
        // La App tendra solo un stage con muchas scenes. El tamaño sera 1600px x 900px
        stage.setResizable(false);
        stage.setWidth(APP_WIDTH);
        stage.setHeight(APP_HEIGHT);

        // Crea la Scene
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        stage.setTitle("GYM APP");
        stage.setScene(scene);
        stage.show();


        /* TEST CONEXION Y QUERY A BBDD */
        // UtilsGlobal.testConexionBBDD();
        // UtilsGlobal.testQueryBBDD();
    }


    public static void main(String[] args) {
        launch();
    }
}