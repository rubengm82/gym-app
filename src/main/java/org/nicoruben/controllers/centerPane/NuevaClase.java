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
        spinner_aforo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 10));
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
            errores += "·Debe ingresar al menos el nombre de la clase\n";
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Clase añadida correctamente!",
                    new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
            alert.setHeaderText(null);
            alert.showAndWait();
            btn_reset.fire();  // Borra todos los campos, hace click virtual en el boton Reset
        }
    }

    @FXML
    void onClickReset(ActionEvent event) {
        spinner_aforo.getValueFactory().setValue(25);;
        input_nombre.clear();
        textArea_descripcion.clear();
    }
}
