package org.nicoruben.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.nicoruben.services.UtilsGlobal;

import java.io.IOException;

public class MainController {

    @FXML
    private Button buttonTheme;

    @FXML
    private void onClickGoToThemeScene(ActionEvent event) throws IOException {
        UtilsGlobal.goToSceneWithButton(buttonTheme, "theme_test");
    }
}
