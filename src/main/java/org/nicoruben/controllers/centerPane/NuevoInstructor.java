package org.nicoruben.controllers.centerPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

        String nombre = input_nombre.getText();
        String apellido1 = input_apellido1.getText();
        String apellido2 = input_apellido2.getText();
        String telefono = input_telefono.getText();
        String dni = input_dni.getText();
        int estado = 1;

        // Variable para almacenar errores
        String errores = "";

        if (nombre.isEmpty() || apellido1.isEmpty()) {
            errores += "Debe ingresar al menos nombre y primer apellido\n";
        }

        if (!errores.isEmpty()) {
            // Mostrar errores en error_label
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            // Comprobacion correcta, insertar Instructor!
            Instructores.insertarInstructor(nombre, apellido1, apellido2, dni,telefono, estado);
            input_error.setText("Instructor añadido correctamente!");
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
        input_telefono.clear();
        input_dni.clear();
    }

}
