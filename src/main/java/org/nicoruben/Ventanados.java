package org.nicoruben;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Ventanados {

    @FXML
    private Button buttonGoMain;

    @FXML
    void onClickGoVentanaMain() throws IOException {
        SceneUtils.cambiarEscena(buttonGoMain, "main.fxml");
    }
}
