package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.nicoruben.controllers.MainController;
import org.nicoruben.models.Clientes;
import org.nicoruben.models.Instructores;

public class EditarCliente {

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_cancelar;

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

    private Clientes clienteActual;

    @FXML
    void onClickActualizar(ActionEvent event) {
        clienteActual.setNombre(input_nombre.getText().trim());
        clienteActual.setApellido1(input_apellido1.getText().trim());
        clienteActual.setApellido2(input_apellido2.getText().trim());
        clienteActual.setIBAN(input_IBAN.getText().trim());
        clienteActual.setMail(input_mail.getText().trim());
        clienteActual.setTelefono(input_telefono.getText().trim());
        clienteActual.setEstado(1); // Mantiene su estado activo porque solo edita sus campos

        boolean exito = Clientes.actualizarCliente(clienteActual);

        if (exito) {
            input_error.setText("Cliente/a actualizado/a correctamente");
            input_error.getStyleClass().removeAll("danger");
            if (!input_error.getStyleClass().contains("success")) input_error.getStyleClass().add("success");
        } else {
            input_error.setText("Error al actualizar el cliente/a");
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) input_error.getStyleClass().add("danger");
        }
    }

    @FXML
    void onClickCancelar(ActionEvent event) {
        MainController.showInCenter("listarClientes");
    }



    // /////////////////////////////////////////
    // /////////// METODOS PROPIOS /////////////
    // /////////////////////////////////////////

    // Metodo encargado de rellenar los campos del input con el objeto recibido
    public void recogerObjeto(Clientes cliente) {
        if (cliente != null) {
            // Guardamos el cliente para usarlo al actualizar
            clienteActual = cliente;

            // Rellenamos los campos del formulario
            input_nombre.setText(cliente.getNombre());
            input_apellido1.setText(cliente.getApellido1());
            input_apellido2.setText(cliente.getApellido2());
            input_IBAN.setText(cliente.getIBAN());
            input_mail.setText(cliente.getMail());
            input_telefono.setText(cliente.getTelefono());
        }
    }

    public static class EditarInstructor {

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

        private Instructores instructorSeleccionado;

        // Método que recibe el instructor desde el controlador anterior
        public void setData(Instructores instructor) {
            this.instructorSeleccionado = instructor;
            input_nombre.setText(instructor.getNombre());
            input_apellido1.setText(instructor.getApellido1());
            input_apellido2.setText(instructor.getApellido2());
            input_dni.setText(instructor.getDNI());
            input_telefono.setText(instructor.getTelefono());
        }

        @FXML
        void onClickActualizar(ActionEvent event) {
            // Actualizar los datos en el objeto
            instructorSeleccionado.setNombre(input_nombre.getText());
            instructorSeleccionado.setApellido1(input_apellido1.getText());
            instructorSeleccionado.setApellido2(input_apellido2.getText());
            instructorSeleccionado.setDNI(input_dni.getText());
            instructorSeleccionado.setTelefono(input_telefono.getText());

            boolean exito = Instructores.actualizarInstructor(instructorSeleccionado);

            if (exito) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setHeaderText(null);
                alerta.setContentText("Instructor actualizado correctamente.");
                alerta.showAndWait();
                MainController.showInCenter("listarInstructores");
            } else {
                input_error.setText("Error al actualizar el instructor.");
            }
        }

        @FXML
        void onClickCancelar(ActionEvent event) {
            MainController.showInCenter("listarInstructores");
        }
    }
}
