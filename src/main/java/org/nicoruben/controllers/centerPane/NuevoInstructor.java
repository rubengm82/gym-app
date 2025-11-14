package org.nicoruben.controllers.centerPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nicoruben.models.Clientes;
import org.nicoruben.models.Instructores;
public class NuevoInstructor {

    @FXML
    private Button btn_aceptar;

    @FXML
    private Button btn_reset;

    @FXML
    private TextField input_apellido1;

    @FXML
    private TextField input_apellido2;

    @FXML
    private Label input_error;

    @FXML
    private TextField input_dni;

    @FXML
    private TextField input_nombre;

    @FXML
    private TextField input_telefono;


    @FXML
    void onClickAceptar(ActionEvent event) {

        String nombre = input_nombre.getText().trim();
        String apellido1 = input_apellido1.getText().trim();
        String apellido2 = input_apellido2.getText().trim();
        String telefono = input_telefono.getText().trim();
        String dni = input_dni.getText().trim().toUpperCase();
        int estado = 1;

        String errores = "";

        if (nombre.isEmpty() || apellido1.isEmpty()) {
            errores += "·Debe ingresar un Nombre\n";
            errores += "·Debe ingresar un Apellido\n";
        }

        // Validar DNI
        if (dni.isEmpty()) {
            errores += "·El DNI es obligatorio\n";
        } else if (!dni.matches("\\d{8}[A-Z]")) {
            errores += "·El DNI no tiene un formato válido (8 dígitos y letra)\n";
        } else {
            // Comprobar letra
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int numero = Integer.parseInt(dni.substring(0, 8));
            char letraCorrecta = letras.charAt(numero % 23);
            if (dni.charAt(8) != letraCorrecta) {
                errores += "· La letra del DNI no es correcta\n";
            }
        }

        // Comprobar si el DNI ya existe
        if (Instructores.existeDNI(dni) && !dni.isEmpty()) {
            errores += "·Ya existe un instructor con este DNI\n";
        }

        if (!errores.isEmpty()) {
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            Instructores.insertarInstructor(nombre, apellido1, apellido2, dni, telefono, estado);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Instructor/a añadido/a correctamente!",
                    new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
            alert.setHeaderText(null);
            alert.showAndWait();
            btn_reset.fire();

        }
    }

    @FXML
    void onClickReset(ActionEvent event) {

        input_nombre.clear();
        input_apellido1.clear();
        input_apellido2.clear();
        input_telefono.clear();
        input_dni.clear();
    }

}
