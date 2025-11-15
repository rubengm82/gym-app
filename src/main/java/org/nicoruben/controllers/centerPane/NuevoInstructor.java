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

        // Validar DNI o NIE
        if (dni.isEmpty()) {
            errores += "·El DNI/NIE es obligatorio\n";
        } else if (!dni.matches("([XYZ]\\d{7}[A-Z])|(\\d{8}[A-Z])")) {
            errores += "·Formato de DNI/NIE no válido\n";
        } else {
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            String numeroStr = dni.substring(0, dni.length() - 1);
            char letra = dni.charAt(dni.length() - 1);

            // Convertir NIE → número
            if (numeroStr.charAt(0) == 'X') numeroStr = "0" + numeroStr.substring(1);
            else if (numeroStr.charAt(0) == 'Y') numeroStr = "1" + numeroStr.substring(1);
            else if (numeroStr.charAt(0) == 'Z') numeroStr = "2" + numeroStr.substring(1);

            int numero = Integer.parseInt(numeroStr);
            char letraCorrecta = letras.charAt(numero % 23);

            if (letra != letraCorrecta) {
                errores += "·La letra del DNI/NIE no es correcta\n";
            }
        }

        // Comprobar si el DNI/NIE ya existe
        if (Instructores.existeDNI(dni)) {
            errores += "·Ya existe un instructor con este DNI/NIE\n";
        }

        // Mostrar errores si hay alguno
        if (!errores.isEmpty()) {
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            // Solo se inserta si no hay errores
            Instructores.insertarInstructor(nombre, apellido1, apellido2, dni, telefono, estado);

            input_error.setText("");
            input_error.getStyleClass().removeAll("danger");

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

        input_error.setText("");
        input_error.getStyleClass().removeAll("danger");
    }

}
