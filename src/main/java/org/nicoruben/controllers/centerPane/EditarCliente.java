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

    @FXML
    private TextField input_password;

    private Clientes clienteActual;

    @FXML
    void onClickActualizar(ActionEvent event) {

        String nombre = input_nombre.getText().trim();
        String apellido1 = input_apellido1.getText().trim();
        String apellido2 = input_apellido2.getText().trim();
        String IBANInput = input_IBAN.getText().trim();
        String IBAN = IBANInput.replaceAll("\\s+", "").toUpperCase();
        String mail = input_mail.getText().trim();
        String telefono = input_telefono.getText().trim();
        String password = input_password.getText().trim();
        int estado = 1;

        StringBuilder errores = new StringBuilder();
        boolean puedeActualizar = true;

        // Validaciones
        if (nombre.isEmpty() || apellido1.isEmpty()) {
            errores.append("·Debe ingresar al menos nombre y primer apellido\n");
            puedeActualizar = false;
        }

        if (mail.isEmpty()) {
            errores.append("·Debe ingresar al menos un correo\n");
            puedeActualizar = false;
        } else if (!mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            errores.append("·El correo no es válido\n");
            puedeActualizar = false;
        } else if (!mail.equals(clienteActual.getMail()) && Clientes.existeMail(mail)) {
            errores.append("·El correo ya está registrado\n");
            puedeActualizar = false;
        }

        if (!IBAN.isEmpty() && !IBAN.matches("[A-Z]{2}[0-9]{22}")) {
            errores.append("·El IBAN no es válido\n");
            puedeActualizar = false;
        }

        // Si NO se puede actualizar → mostrar errores
        if (!puedeActualizar) {
            input_error.setText(errores.toString().trim());
            input_error.getStyleClass().removeAll("success");
            if (!input_error.getStyleClass().contains("danger")) {
                input_error.getStyleClass().add("danger");
            }
        } else {
            // Actualizar datos
            clienteActual.setNombre(nombre);
            clienteActual.setApellido1(apellido1);
            clienteActual.setApellido2(apellido2);
            clienteActual.setIBAN(IBAN);
            clienteActual.setMail(mail);
            clienteActual.setTelefono(telefono);
            clienteActual.setEstado(estado);

            boolean exito;

            // Si hay nueva contraseña
            if (!password.isEmpty()) {
                String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password,
                        org.mindrot.jbcrypt.BCrypt.gensalt(12));

                if (hashedPassword.startsWith("$2a$")) {
                    hashedPassword = "$2y$" + hashedPassword.substring(4);
                }

                exito = Clientes.actualizarClienteConPassword(clienteActual, hashedPassword);
            } else {
                exito = Clientes.actualizarCliente(clienteActual);
            }

            if (exito) {
                input_error.setText("");
                input_error.getStyleClass().removeAll("danger");
                if (!input_error.getStyleClass().contains("success")) {
                    input_error.getStyleClass().add("success");
                }

                new Alert(Alert.AlertType.INFORMATION,
                        "Cliente/a actualizado/a correctamente!",
                        new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE)).showAndWait();
            } else {
                input_error.getStyleClass().removeAll("success");
                if (!input_error.getStyleClass().contains("danger")) {
                    input_error.getStyleClass().add("danger");
                }

                new Alert(Alert.AlertType.ERROR,
                        "Error al actualizar el cliente/a",
                        new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE)).showAndWait();
            }
        }
    }


    @FXML
    void onClickCancelar(ActionEvent event) {
        MainController.showInCenter("listarClientes", null);
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