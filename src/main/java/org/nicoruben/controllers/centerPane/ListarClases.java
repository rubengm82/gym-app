package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.models.Clases;
import org.nicoruben.models.Clientes;
import org.nicoruben.models.Instructores;

import java.time.LocalTime;
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
    private TableColumn<Clases, LocalTime> campoDuracion;

    @FXML
    private TableColumn<Clases, String> campoDia;

    @FXML
    private TableColumn<Clases, Integer> campoAforo;

    @FXML
    private TableColumn<Clases, Integer> campoIntructor;

    @FXML
    private TableColumn<Clases, String> campoDescripcion;

    @FXML
    private Label labelTituloClientes;





    /* Atributos de la class */
    private List<Clases> todasClases;
    private List<Instructores> todosInstructores = Instructores.obtenerInstructores();



    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////

    @FXML
    void onClickBajaAlta(ActionEvent event) {

    }

    @FXML
    void onClickEditar(ActionEvent event) {

    }

    @FXML
    void onClickToggleBajas(ActionEvent event) {

    }

    /* AUTO-LOAD al cargar la vista */
    public void initialize() {
        // Configurar columnas
        campoID.setCellValueFactory(new PropertyValueFactory<>("id_clase"));
        campoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        campoDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        campoDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        campoAforo.setCellValueFactory(new PropertyValueFactory<>("aforo"));
        campoDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // NUEVO: columna del instructor mostrando nombre completo
        campoIntructor.setCellValueFactory(new PropertyValueFactory<>("nombreInstructorCompleto"));

        // Cargar todos los instructores y clases
        todasClases = Clases.obtenerTodasClases();
        todosInstructores = Instructores.obtenerInstructores();

        // Rellenar nombreInstructor en cada clase
        for (Clases clase : todasClases) {
            Instructores encontrado = null;

            for (Instructores instructor : todosInstructores) {
                if (instructor.getId() == clase.getFkIdInst()) {
                    encontrado = instructor;
                }
            }
            if (encontrado != null) {
                clase.setNombreInstructorCompleto (encontrado.getNombre() + " " + encontrado.getApellido() + " " + encontrado.getApellidos2());
            } else {
                clase.setNombreInstructorCompleto("Desconocido");
            }
        }

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
