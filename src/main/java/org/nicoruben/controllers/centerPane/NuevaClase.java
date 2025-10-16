package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nicoruben.models.Clases;

public class NuevaClase {

    @FXML
    private Button btn_aceptar;

    @FXML
    private Button btn_reset;

    @FXML
    private Spinner<Integer> spinner_aforo;

    @FXML
    private TextArea textArea_descripcion;

    @FXML
    private Label input_error;

    @FXML
    private TextField input_nombre;



    /* AUTO-LOAD al cargar la vista */
    public void initialize() {
        /* SPINNERS */
        // Spinner de personas en el aforo -- AFORO
        spinner_aforo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 25));
    }


    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////
    @FXML
    void onClickAceptar(ActionEvent event) {
        String nombre = input_nombre.getText();
        int aforo = spinner_aforo.getValue();
        String descripcion = textArea_descripcion.getText();
        int estado = 1;

        // Variable para almacenar errores
        String errores = "";

        if (nombre.isEmpty()) {
            errores += "Debe ingresar al menos nombre\n";
        }

        if (!errores.isEmpty()) {
            // Mostrar errores en error_label
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            // Comprobacion correcta, insertar clase!
            Clases.insertarClase(nombre, aforo, descripcion, estado);
            input_error.setText("Clase añadida correctamente!");
            btn_reset.fire();  // Borra todos los campos, hace click virtual en el boton Reset
            input_error.getStyleClass().removeAll("danger");
            if (!input_error.getStyleClass().contains("success")) {
                input_error.getStyleClass().add("success");
            }
        }
    }

    @FXML
    void onClickReset(ActionEvent event) {
        spinner_aforo.getValueFactory().setValue(25);;
        input_nombre.clear();
        textArea_descripcion.clear();
    }
}
