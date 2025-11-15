package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

        String nombre = input_nombre.getText().trim();
        String apellido1 = input_apellido1.getText().trim();
        String apellido2 = input_apellido2.getText().trim();
        String telefono = input_telefono.getText().trim();
        String dni = input_dni.getText().trim().toUpperCase();

        String errores = "";

        // Validar nombre y apellidos
        if (nombre.isEmpty() || apellido1.isEmpty()) {
            errores += "·Debe ingresar un Nombre\n";
            errores += "·Debe ingresar un Apellido\n";
        }

        // Validar DNI/NIE
        if (dni.isEmpty()) {
            errores += "·El DNI/NIE es obligatorio\n";
        } else if (!dni.matches("([XYZ]\\d{7}[A-Z])|(\\d{8}[A-Z])")) {
            errores += "·Formato de DNI/NIE no válido\n";
        } else {
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            String numeroStr = dni.substring(0, dni.length() - 1);
            char letra = dni.charAt(dni.length() - 1);

            if (numeroStr.charAt(0) == 'X') numeroStr = "0" + numeroStr.substring(1);
            else if (numeroStr.charAt(0) == 'Y') numeroStr = "1" + numeroStr.substring(1);
            else if (numeroStr.charAt(0) == 'Z') numeroStr = "2" + numeroStr.substring(1);

            int numero = Integer.parseInt(numeroStr);
            char letraCorrecta = letras.charAt(numero % 23);

            if (letra != letraCorrecta) {
                errores += "·La letra del DNI/NIE no es correcta\n";
            }
        }

        // Comprobar si el DNI/NIE existe en otro instructor
        if (!dni.equals(instructorSeleccionado.getDNI()) && Instructores.existeDNI(dni)) {
            errores += "·Ya existe un instructor con este DNI/NIE\n";
        }

        // Mostrar errores si los hay
        if (!errores.isEmpty()) {
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) input_error.getStyleClass().add("danger");
        } else {
            // Actualizar instructor solo si no hay errores
            instructorSeleccionado.setNombre(nombre);
            instructorSeleccionado.setApellido1(apellido1);
            instructorSeleccionado.setApellido2(apellido2);
            instructorSeleccionado.setDNI(dni);
            instructorSeleccionado.setTelefono(telefono);

            boolean exito = Instructores.actualizarInstructor(instructorSeleccionado);

            if (exito) {
                input_error.setText(""); // Limpiar errores
                input_error.getStyleClass().removeAll("danger");
                if (!input_error.getStyleClass().contains("success")) input_error.getStyleClass().add("success");

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Instructor/a actualizado/a correctamente",
                        new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Error al actualizar el instructor/a",
                        new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onClickCancelar(ActionEvent event) {
        MainController.showInCenter("listarInstructores");
    }

}
