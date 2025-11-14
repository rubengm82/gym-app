package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nicoruben.controllers.MainController;
import org.nicoruben.models.Clases;
import org.nicoruben.models.Clientes;
import org.nicoruben.models.Instructores;

public class EditarClase {

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_cancelar;

    @FXML
    private Label input_error;

    @FXML
    private TextField input_nombre;

    @FXML
    private Spinner<Integer> spinner_aforo;

    @FXML
    private TextArea textArea_descripcion;

    private Clases claseActual;


    /* AUTO-LOAD al cargar la vista */
    public void initialize() {
        // Creamos el ValueFactory con un valor inicial cualquiera (temporal)
        spinner_aforo.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0)
        );
    }


    @FXML
    void onClickActualizar(ActionEvent event) {
        claseActual.setNombre(input_nombre.getText().trim());
        claseActual.setAforo(spinner_aforo.getValue());
        claseActual.setDescripcion(textArea_descripcion.getText().trim());


        boolean exito = Clases.actualizarClase(claseActual);

        if (exito) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Clase actualizada correctamente",
                    new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
            alert.setHeaderText(null);
            alert.showAndWait();
            input_error.getStyleClass().removeAll("danger");
            if (!input_error.getStyleClass().contains("success")) input_error.getStyleClass().add("success");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Error al actualizar la clase",
                    new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
            alert.setHeaderText(null);
            alert.showAndWait();
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) input_error.getStyleClass().add("danger");
        }
    }

    @FXML
    void onClickCancelar(ActionEvent event) {
        MainController.showInCenter("listarClases");
    }



    // /////////////////////////////////////////
    // /////////// METODOS PROPIOS /////////////
    // /////////////////////////////////////////

    // Metodo encargado de rellenar los campos del input con el objeto recibido
    public void recogerObjeto(Clases clase) {
        // Guardamos el cliente para usarlo al actualizar
        claseActual = clase;

        input_nombre.setText(clase.getNombre());
        spinner_aforo.getValueFactory().setValue(clase.getAforo());
        textArea_descripcion.setText(clase.getDescripcion());
    }



}
