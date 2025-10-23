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
        String IBAN = input_IBAN.getText().trim().replaceAll("\\s+", "").toUpperCase();
        String mail = input_mail.getText().trim();
        String telefono = input_telefono.getText().trim();
        int estado = 1;

        // Variable para almacenar errores
        String errores = "";

        if (nombre.isEmpty() || apellido1.isEmpty()) {
            errores += "Debe ingresar al menos nombre y primer apellido\n";
        }

        if (mail.isEmpty()) {
            errores += "El correo es obligatorio\n";
        } else if (!mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            errores += "El correo no es válido\n";
        } else if (Clientes.existeMail(mail)) {
            errores += "El correo ya está registrado\n";
        }

        if (!IBAN.isEmpty() && !IBAN.matches("[a-zA-Z]{2}[0-9]{2}[\\s*]{0,}[0-9]{4}[\\s*]{0,}[0-9]{4}[\\s*]{0,}[0-9]{4}[\\s*]{0,}[0-9]{4}[\\s*]{0,}[0-9]{4}[\\s*]{0,}")) {
            errores += "El IBAN no es válido\n";
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
            IBAN = IBAN.replaceAll("\\s+", "").toUpperCase();
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
        input_nombre.clear();
        input_apellido1.clear();
        input_apellido2.clear();
        input_IBAN.clear();
        input_mail.clear();
        input_telefono.clear();
    }

}
