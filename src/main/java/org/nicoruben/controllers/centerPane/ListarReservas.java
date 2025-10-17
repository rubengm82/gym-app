package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.models.Clases;
import org.nicoruben.models.Clientes;
import org.nicoruben.models.Reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ListarReservas {

    @FXML
    private Button buttonBorrar;

    @FXML
    private TableColumn<Clases, String> campoClase;

    @FXML
    private TableColumn<Clientes, String> campoCliente;

    @FXML
    private TableColumn<Reservas, Integer> campoEstado;

    @FXML
    private TableColumn<Reservas, LocalDate> campoFechaReserva;

    @FXML
    private TableColumn<?, LocalTime> campoHoraInicio;

    @FXML
    private TableColumn<Reservas, Integer> campoID;

    @FXML
    private DatePicker id_datepicker;

    @FXML
    private Label labelTituloClientes;

    @FXML
    private TableView<Reservas> tablaReservas;


    /* Atributos de la class */
    private List<Reservas> todasReservas;


    /* AUTO-LOAD al cargar la vista */
    public void initialize() {
        // Columnas de la tablaReservas
        campoID.setCellValueFactory(new PropertyValueFactory<>("idReserva")); // id_reserva
        campoCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente")); // nombre + apellido1 + apellido2
        campoClase.setCellValueFactory(new PropertyValueFactory<>("nombreClase")); // nombre de la clase
        campoFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva")); // LocalDate
        campoHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio")); // LocalTime

        // Cargar datos en la tablaReservas
        todasReservas = Reservas.obtenerTodasReservas(); // Metodo que consulta la BBDD y rellena objetos Reserva con cliente y planificacion
        tablaReservas.setItems(FXCollections.observableArrayList(todasReservas));
    }

    @FXML
    void onClickBajaAlta(ActionEvent event) {
        // Obtenemos la reserva seleccionada
        Reservas seleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar una reserva primero.");
            alert.showAndWait();
            return;
        }

        // Confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que quieres borrar esta reserva?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        // Borrar de la base de datos
        boolean exito = Reservas.borrarReserva(seleccionada.getIdReserva());

        if (exito) {
            // Quitar de la tabla
            tablaReservas.getItems().remove(seleccionada);
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("No se pudo borrar la reserva.");
            error.showAndWait();
        }
    }


}
