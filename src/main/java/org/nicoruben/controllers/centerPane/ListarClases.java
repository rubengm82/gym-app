package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.controllers.MainController;
import org.nicoruben.models.Clases;
import org.nicoruben.models.Clientes;

import java.util.ArrayList;
import java.util.List;

public class ListarClases {

    @FXML
    private Button buttonBajaAlta;

    @FXML
    private Button buttonEditar;

    @FXML
    private ToggleButton buttonToggleVerBajas;

    @FXML
    private TableView<Clases> tablaClases;

    @FXML
    private TableColumn<Clases, Integer> campoID;

    @FXML
    private TableColumn<Clases, String> campoNombre;

    @FXML
    private TableColumn<Clases, Integer> campoAforo;

    @FXML
    private TableColumn<Clases, String> campoDescripcion;

    @FXML
    private Label labelTituloClientes;


    /* Atributos de la class */
    private List<Clases> todasClases;


    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////
    @FXML
    void onClickBajaAlta(ActionEvent event) {
        // Obtener la clase seleccionada en la tabla
        Clases claseSeleccionada = tablaClases.getSelectionModel().getSelectedItem();

        if (claseSeleccionada != null) {
            // Crear un Alert de confirmación
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText(null);
            String accion = (claseSeleccionada.getEstado() == 1) ? "dar de baja" : "dar de alta";
            confirmacion.setContentText("¿Seguro que deseas " + accion + " a la clase seleccionada?");

            // Personalizar botones: Aceptar y Cancelar
            ButtonType btnAceptar = new ButtonType("Aceptar");
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmacion.getButtonTypes().setAll(btnAceptar, btnCancelar);

            // Esperar respuesta
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == btnAceptar) {
                    // Cambiar el estado del cliente
                    int nuevoEstado = (claseSeleccionada.getEstado() == 1) ? 0 : 1;
                    claseSeleccionada.setEstado(nuevoEstado);

                    // Actualizar en la base de datos
                    Clases.actualizarEstado(claseSeleccionada.getId_clase(), nuevoEstado);

                    // Refrescar la tabla según el toggle
                    if (buttonToggleVerBajas.isSelected()) {
                        mostrarClasesPorEstado(0);
                    } else {
                        mostrarClasesPorEstado(1);
                    }
                }
            });
        } else {
            // Si no hay cliente seleccionado, mostrar alerta de aviso
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, seleccione una clase primero");
            alerta.showAndWait();
        }
    }

    @FXML
    void onClickEditar(ActionEvent event) {
        Clases claseSeleccionada = tablaClases.getSelectionModel().getSelectedItem();

        if (claseSeleccionada != null) {
            // Llamamos al MainController para cargar la vista y pasar la clase del gym
            MainController.showInCenterWithData("editarClase", claseSeleccionada);
        } else {
            // Alert en caso de no tener seleccionado algo de la lista
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecciona una clase antes de editar.");
            alert.showAndWait();
        }
    }

    @FXML
    void onClickToggleBajas(ActionEvent event) {
        if (buttonToggleVerBajas.isSelected()) {
            mostrarClasesPorEstado(0); // mostrar bajas
            buttonBajaAlta.setText("ALTA");
            labelTituloClientes.setText("Listado Clases de Baja");
            buttonToggleVerBajas.setText("VER CLASES DE ALTA");
        } else {
            mostrarClasesPorEstado(1); // mostrar activos
            buttonBajaAlta.setText("BAJA");
            labelTituloClientes.setText("Listado Clases Activas");
            buttonToggleVerBajas.setText("VER CLASES DE BAJA");
        }
    }

    /* AUTO-LOAD al cargar la vista */
    public void initialize() {

        // Cargar todos los instructores y clases
        todasClases = Clases.obtenerTodasClases();

        // Configurar columnas
        campoID.setCellValueFactory(new PropertyValueFactory<>("id_clase"));
        campoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        campoAforo.setCellValueFactory(new PropertyValueFactory<>("aforo"));
        campoDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Mostrar solo activos
        mostrarClasesPorEstado(1);

    }

    private void mostrarClasesPorEstado(int estado) {
        List<Clases> filtrados_bajas = new ArrayList<>();
        for (Clases clases : todasClases) {
            if (clases.getEstado() == estado) {
                filtrados_bajas.add(clases);
            }
        }
        tablaClases.setItems(FXCollections.observableArrayList(filtrados_bajas));
    }
}
