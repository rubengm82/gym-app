package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nicoruben.controllers.MainController;
import org.nicoruben.models.Clientes;

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
        // Recoger datos
        String nombre = input_nombre.getText().trim();
        String apellido1 = input_apellido1.getText().trim();
        String apellido2 = input_apellido2.getText().trim();
        String IBANInput = input_IBAN.getText().trim();
        String IBAN = IBANInput.replaceAll("\\s+", "").toUpperCase();
        String mail = input_mail.getText().trim();
        String telefono = input_telefono.getText().trim();
        int estado = 1; // Mantener activo

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
        } else if (!mail.equals(clienteActual.getMail()) && Clientes.existeMail(mail)) {
            errores += "·El correo ya está registrado\n";
        }

        // Validar IBAN si se ingresó
        if (!IBAN.isEmpty() && !IBAN.matches("[A-Z]{2}[0-9]{22}")) {
            errores += "·El IBAN no es válido\n";
        }

        // Mostrar errores si los hay
        if (!errores.isEmpty()) {
            input_error.setText(errores.trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            // Solo actualizamos si no hay errores
            clienteActual.setNombre(nombre);
            clienteActual.setApellido1(apellido1);
            clienteActual.setApellido2(apellido2);
            clienteActual.setIBAN(IBAN);
            clienteActual.setMail(mail);
            clienteActual.setTelefono(telefono);
            clienteActual.setEstado(estado);

            boolean exito = Clientes.actualizarCliente(clienteActual);

            if (exito) {
                // Limpiar errores y aplicar estilo success
                input_error.setText("");
                input_error.getStyleClass().removeAll("danger");
                if (!input_error.getStyleClass().contains("success")) {
                    input_error.getStyleClass().add("success");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Cliente/a actualizado/a correctamente!",
                        new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                // Mostrar error de actualización
                input_error.getStyleClass().removeAll("success");
                if (!input_error.getStyleClass().contains("danger")) {
                    input_error.getStyleClass().add("danger");
                }

                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Error al actualizar el cliente/a",
                        new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
                alert.setHeaderText(null);
                alert.showAndWait();
            }
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

}