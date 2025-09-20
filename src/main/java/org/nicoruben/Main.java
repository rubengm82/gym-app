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
    }

    @FXML
    protected void onButtonClick() {
        label.setText("¡Botón pulsado desde FXML!");
    }

    public static void main(String[] args) {
        launch();
    }
}
