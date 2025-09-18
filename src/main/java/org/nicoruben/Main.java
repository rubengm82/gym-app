package org.nicoruben; // El package puede seguir siendo org.example si quieres

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {

    @FXML
    private Label label;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/nicoruben/main.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 400, 200);
        stage.setTitle("App JavaFX con FXML");
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
