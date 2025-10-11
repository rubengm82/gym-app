package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.nicoruben.models.Clientes;

public class NuevoCliente {

    @FXML
    private Button btn_aceptar;

    @FXML
    private Button btn_reset;

    @FXML
    private TextField input_mail;

    @FXML
    private TextField input_IBAN;

    @FXML
    private TextField input_apellido1;

    @FXML
    private TextField input_apellido2;

    @FXML
    private TextField input_nombre;

    @FXML
    private TextField input_telefono;

    @FXML
    private Label input_error;


    @FXML
    void onClickAceptar(ActionEvent event) {
        String nombre = input_nombre.getText().trim();
        String apellido1 = input_apellido1.getText().trim();
        String apellido2 = input_apellido2.getText().trim();
        String IBAN = input_IBAN.getText().trim();
        String mail = input_mail.getText().trim();
        String telefono = input_telefono.getText().trim();
        int estado = 1;

        // Variable para almacenar errores
        String errores = "";

        if (nombre.isEmpty() || apellido1.isEmpty()) {
            errores += "Debe ingresar al menos nombre y primer apellido\n";
        }

        if (!mail.isEmpty() && (!mail.contains("@") || mail.startsWith("@") || mail.endsWith("@"))) {
            errores += "El correo no es válido\n";
        }

        if (Clientes.existeMail(mail)) {
            errores += "El correo ya está registrado\n";
        }

        if (!errores.isEmpty()) {
            // Mostrar errores en error_label
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            // Comprobacion correcta, insertar cliente!
            Clientes.insertarCliente(nombre, apellido1, apellido2, IBAN, mail, telefono, estado);
            input_error.setText("Cliente añadido correctamente!");
            btn_reset.fire();  // Borra todos los campos, hace click virtual en el boton Reset
            input_error.getStyleClass().removeAll("danger");
            if (!input_error.getStyleClass().contains("success")) {
                input_error.getStyleClass().add("success");
            }
        }
    }

    @FXML
    void onClickReset(ActionEvent event) {
        String nombre = input_nombre.getText();
        String apellido1 = input_apellido1.getText();
        String apellido2 = input_apellido2.getText();
        String IBAN = input_IBAN.getText();
        String mail = input_mail.getText();
        String telefono = input_telefono.getText();

        input_nombre.clear();
        input_apellido1.clear();
        input_apellido2.clear();
        input_IBAN.clear();
        input_mail.clear();
        input_telefono.clear();
    }

}
