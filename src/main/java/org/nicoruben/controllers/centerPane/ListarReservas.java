package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListarReservas {

    @FXML
    private Button buttonBajaAlta;

    @FXML
    private TableColumn<?, ?> campoAforo;

    @FXML
    private TableColumn<?, ?> campoDescripcion;

    @FXML
    private TableColumn<?, ?> campoDia;

    @FXML
    private TableColumn<?, ?> campoDuracion;

    @FXML
    private TableColumn<?, ?> campoID;

    @FXML
    private TableColumn<?, ?> campoIntructor;

    @FXML
    private TableColumn<?, ?> campoNombre;

    @FXML
    private Label labelTituloClientes;

    @FXML
    private TableView<?> tablaClases;

    @FXML
    void onClickBajaAlta(ActionEvent event) {

    }

}
