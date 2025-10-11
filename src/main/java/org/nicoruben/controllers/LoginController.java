package org.nicoruben.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.nicoruben.services.UtilsGlobal;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import org.nicoruben.models.*;


import java.io.IOException;

public class LoginController {

    @FXML
    private Button buttonEntrar;

    @FXML
    private PasswordField input_password;

    @FXML
    private TextField input_mail;

    @FXML
    private Label error_label;


    @FXML
    void onClickEntrar(ActionEvent event) {
        String mail_input = input_mail.getText();
        String password_input = input_password.getText();

        // System.out.println(mail_input);
        // System.out.println(password_input);

        try {
            int res = Administradores.verificarSession(mail_input,password_input);
            // System.out.println(res);

            if(res != 1){
                input_mail.clear();
                input_password.clear();

                //label rojo
                error_label.setText("Usuario o contraseña incorrectos");
            }else{
                UtilsGlobal.goToSceneWithButton(buttonEntrar, "/views/main.fxml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
