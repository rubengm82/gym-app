package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
        // Recoger datos
        String nombre = input_nombre.getText().trim();
        String apellido1 = input_apellido1.getText().trim();
        String apellido2 = input_apellido2.getText().trim();
        String IBANInput = input_IBAN.getText().trim();
        String IBAN = IBANInput.replaceAll("\\s+", "").toUpperCase();
        String mail = input_mail.getText().trim();
        String telefono = input_telefono.getText().trim();
        int estado = 1;

        // Variable para almacenar errores
        String errores = "";

        // Validar nombre y primer apellido
        if (nombre.isEmpty() || apellido1.isEmpty()) {
            errores += "·Debe ingresar al menos nombre y primer apellido\n";
        }

        // Validar correo
        if (mail.isEmpty()) {
            errores += "·Debe ingresar al menos un correo\n";
        } else if (!mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            errores += "·El correo no es válido\n";
        } else if (Clientes.existeMail(mail)) {
            errores += "·El correo ya está registrado\n";
        }

        // Validar IBAN si se ingresó
        if (!IBAN.isEmpty() && !IBAN.matches("[A-Z]{2}[0-9]{22}")) {
            errores += "·El IBAN no es válido\n";
        }

        // Mostrar errores si hay alguno
        if (!errores.isEmpty()) {
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            // No hay errores, insertar cliente
            Clientes.insertarCliente(nombre, apellido1, apellido2, IBAN, mail, telefono, estado);

            // Limpiar errores y estilos
            input_error.setText("");
            input_error.getStyleClass().removeAll("danger");
            if (!input_error.getStyleClass().contains("success")) {
                input_error.getStyleClass().add("success");
            }

            // Mostrar alerta de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Cliente/a añadido/a correctamente!",
                    new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
            alert.setHeaderText(null);
            alert.showAndWait();

            // Limpiar formulario
            btn_reset.fire();
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
