package org.nicoruben;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SceneUtils {

    /**
     * Cambia la escena del Stage actual.
     *
     * @param sourceNode Nodo que disparó el evento (ej: Button)
     * @param fxmlFile   Nombre del archivo FXML a cargar, ejemplo "ventanados.fxml"
     * @throws IOException Si no se encuentra el FXML
     */
    public static void cambiarEscena(Node sourceNode, String fxmlFile) throws IOException {
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(SceneUtils.class.getResource("/nicoruben/" + fxmlFile)), 640, 480);
        stage.setScene(scene);
    }

}
