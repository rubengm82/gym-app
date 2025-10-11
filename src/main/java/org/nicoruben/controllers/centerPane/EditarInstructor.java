package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.nicoruben.controllers.MainController;
import org.nicoruben.models.Instructores;

public class EditarInstructor {

    @FXML
    private TextField input_nombre;

    @FXML
    private TextField input_apellido1;

    @FXML
    private TextField input_apellido2;

    @FXML
    private TextField input_dni;

    @FXML
    private TextField input_telefono;

    @FXML
    private Label input_error;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_cancelar;

    private Instructores instructorSeleccionado;


    // Recibe el instructor desde ListarInstructores
    public void recogerObjeto(Instructores instructor) {
        this.instructorSeleccionado = instructor;
        input_nombre.setText(instructor.getNombre());
        input_apellido1.setText(instructor.getApellido1());
        input_apellido2.setText(instructor.getApellido2());
        input_dni.setText(instructor.getDNI());
        input_telefono.setText(instructor.getTelefono());
    }

    @FXML
    void onClickActualizar(ActionEvent event) {

        instructorSeleccionado.setNombre(input_nombre.getText());
        instructorSeleccionado.setApellido1(input_apellido1.getText());
        instructorSeleccionado.setApellido2(input_apellido2.getText());
        instructorSeleccionado.setDNI(input_dni.getText());
        instructorSeleccionado.setTelefono(input_telefono.getText());

        boolean exito = Instructores.actualizarInstructor(instructorSeleccionado);

        if (exito) {
            input_error.setText("Instructor/a actualizado/a correctamente");
            input_error.getStyleClass().removeAll("danger");
            if (!input_error.getStyleClass().contains("success")) input_error.getStyleClass().add("success");
        } else {
            input_error.setText("Error al actualizar el instructor/a");
        }
    }

    @FXML
    void onClickCancelar(ActionEvent event) {
        MainController.showInCenter("listarInstructores");
    }

}
