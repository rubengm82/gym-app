package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;

import org.nicoruben.models.Instructores;


import java.util.ArrayList;
import java.util.List;

public class ListarInstructores {

    @FXML
    private Button buttonBajaAlta;

    @FXML
    private Button buttonEditar;

    @FXML
    private ToggleButton buttonToggleVerBajas;

    @FXML
    private TableColumn<Instructores, Integer> campoID;
    @FXML
    private TableColumn<Instructores, String> campoNombre;
    @FXML
    private TableColumn<Instructores, String> campoApellido1;
    @FXML
    private TableColumn<Instructores, String> campoApellido2;
    @FXML
    private TableColumn<Instructores, String> campoDNI;
    @FXML
    private TableColumn<Instructores, Integer> campoTelefono;

    @FXML
    private Label labelTituloInstructores;

    @FXML
    private TableView<Instructores> tablaInstructores;


    private List<Instructores> listaInstructores;


    /* AUTO-LOAD al cargar la vista */
    public void initialize() {
        campoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        campoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        campoApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        campoApellido2.setCellValueFactory(new PropertyValueFactory<>("apellidos2"));
        campoDNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        campoTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        // Cargar datos en la tabla de clintes
        listaInstructores = Instructores.obtenerInstructores();

        // Mostrar solo activos con (estado = 1)  (1 - ACTIVO || 0 - BAJA)
        mostrarInstructoresPorEstado(1);

    }

    private void mostrarInstructoresPorEstado(int estado) {
        List<Instructores> filtrados_bajas = new ArrayList<>();
        for (Instructores instructor : listaInstructores) {
            if (instructor.getEstado() == estado) {
                filtrados_bajas.add(instructor);
            }
        }
        tablaInstructores.setItems(FXCollections.observableArrayList(filtrados_bajas));
    }




    @FXML
    void onClickBajaAlta(ActionEvent event) {

    }

    @FXML
    void onClickEditar(ActionEvent event) {

    }

    @FXML
    void onClickToggleBajas(ActionEvent event) {

    }

}
