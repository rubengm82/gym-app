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
    private Button buttonHoy;

    @FXML
    private Button buttonTodasReservas;

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
        campoID.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        campoCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        campoClase.setCellValueFactory(new PropertyValueFactory<>("nombreClase"));
        campoFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        campoHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));

        // Cargar todas las reservas
        todasReservas = Reservas.obtenerTodasReservas();
        tablaReservas.setItems(FXCollections.observableArrayList(todasReservas));

        // Listener para filtrar por fecha
        id_datepicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate == null) {
                // Si no hay fecha seleccionada, mostrar todas
                tablaReservas.setItems(FXCollections.observableArrayList(todasReservas));
            } else {
                // Filtrar solo las reservas que coincidan con la fecha
                List<Reservas> filtradas = todasReservas.stream()
                        .filter(r -> r.getFechaReserva().isEqual(newDate))
                        .toList();
                tablaReservas.setItems(FXCollections.observableArrayList(filtradas));
            }
        });
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

    @FXML
    void onClickHoy(ActionEvent event) {
        LocalDate hoy = LocalDate.now();
        id_datepicker.setValue(hoy); // Esto también actualizará la tabla por el listener que ya tienes
    }

    @FXML
    void onClickTodasReservas(ActionEvent event) {
        id_datepicker.setValue(null); // Esto hará que el listener muestre todas las reservas
    }

}
