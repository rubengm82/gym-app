package org.nicoruben.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.nicoruben.services.UtilsGlobal;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button buttonEntrar;

    @FXML
    void onClickEntrar(ActionEvent event) {
        try {
            UtilsGlobal.goToSceneWithButton(buttonEntrar, "/views/main.fxml");
        } catch (IOException e) {
            e.printStackTrace(); // or show an alert to the user
        }
    }

}
