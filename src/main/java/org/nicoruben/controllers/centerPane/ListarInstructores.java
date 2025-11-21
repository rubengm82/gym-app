package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.models.Instructores;
import org.nicoruben.controllers.MainController;

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
    private TableColumn<Instructores, String> campoTelefono;

    @FXML
    private Label labelTituloInstructores;

    @FXML
    private TableView<Instructores> tablaInstructores;

    private List<Instructores> listaInstructores;


    /* AUTO-LOAD al cargar la vista */
    public void initialize() {
        campoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        campoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        campoApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        campoApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        campoDNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        campoTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));


        listaInstructores = Instructores.obtenerInstructores();

        // Mostrar solo activos (estado = 1)
        mostrarInstructoresPorEstado(1);
    }

    private void mostrarInstructoresPorEstado(int estado) {
        List<Instructores> filtrados = new ArrayList<>();
        for (Instructores instructor : listaInstructores) {
            if (instructor.getEstado() == estado) {
                filtrados.add(instructor);
            }
        }
        tablaInstructores.setItems(FXCollections.observableArrayList(filtrados));
    }

    // Toggle Activos / Bajas
    @FXML
    void onClickToggleBajas(ActionEvent event) {
        if (buttonToggleVerBajas.isSelected()) {
            mostrarInstructoresPorEstado(0);
            buttonBajaAlta.setText("ALTA");
            labelTituloInstructores.setText("Listado Instructores de Baja");
            buttonToggleVerBajas.setText("VER INSTRUCTORES DE ALTA");
        } else {
            mostrarInstructoresPorEstado(1);
            buttonBajaAlta.setText("BAJA");
            labelTituloInstructores.setText("Listado Instructores Activos");
            buttonToggleVerBajas.setText("VER INSTRUCTORES DE BAJA");
        }
    }

    // Cambiar estado BAJA / ALTA
    @FXML
    void onClickBajaAlta(ActionEvent event) {
        Instructores instructorSeleccionado = tablaInstructores.getSelectionModel().getSelectedItem();

        if (instructorSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText(null);

            String accion = (instructorSeleccionado.getEstado() == 1) ? "dar de baja" : "dar de alta";
            confirmacion.setContentText("¿Seguro que deseas " + accion + " al instructor seleccionado?");

            ButtonType btnAceptar = new ButtonType("Aceptar");
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmacion.getButtonTypes().setAll(btnAceptar, btnCancelar);

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == btnAceptar) {
                    int nuevoEstado = (instructorSeleccionado.getEstado() == 1) ? 0 : 1;
                    instructorSeleccionado.setEstado(nuevoEstado);


                    Instructores.actualizarEstado(instructorSeleccionado.getId(), nuevoEstado);


                    if (buttonToggleVerBajas.isSelected()) {
                        mostrarInstructoresPorEstado(0);
                    } else {
                        mostrarInstructoresPorEstado(1);
                    }
                }
            });
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, seleccione un instructor primero.");
            alerta.showAndWait();
        }
    }

    // Editar instructor
    @FXML
    void onClickEditar(ActionEvent event) {
        Instructores instructorSeleccionado = tablaInstructores.getSelectionModel().getSelectedItem();

        if (instructorSeleccionado != null) {
            MainController.showInCenter("editarInstructor", instructorSeleccionado);
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setHeaderText(null);
            alerta.setContentText("Selecciona un instructor antes de editar.");
            alerta.showAndWait();
        }
    }
}
