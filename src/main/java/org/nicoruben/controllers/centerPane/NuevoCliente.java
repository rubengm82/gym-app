package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.nicoruben.models.Clientes;

public class NuevoCliente {

    @FXML
    private Button btn_aceptar;

    @FXML
    private Button btn_reset;

    @FXML
    private TextField input_Email;

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
    void onClickAceptar(ActionEvent event) {
        String nombre;
        String apellido1;
        String apellido2;
        String IBAN;
        String mail;
        String telefono;
        int estado = 1;

        nombre = input_nombre.getText();
        apellido1 = input_apellido1.getText();
        apellido2 = input_apellido2.getText();
        IBAN = input_IBAN.getText();
        mail = input_IBAN.getText();
        telefono = input_telefono.getText();

        // System.out.println(nombre+apellido1+apellido2+IBAN+mail+telefono+Integer.toString(estado));

        Clientes.insertarCliente(nombre, apellido1, apellido2, IBAN, mail, telefono, estado);
    }

}
