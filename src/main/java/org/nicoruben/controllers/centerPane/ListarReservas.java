package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableColumn<Reservas, String> campoDia;

    @FXML
    private TableColumn<Reservas, LocalTime> campoHoraInicio;

    @FXML
    private TableColumn<Reservas, LocalTime> campoHoraFin;

    @FXML
    private TableColumn<Reservas, Integer> campoID;

    @FXML
    private DatePicker id_datepicker;

    @FXML
    private Label labelTituloClientes;

    @FXML
    private TableView<Reservas> tablaReservas;

    // Usaremos ObservableList para que los cambios se reflejen automáticamente
    private ObservableList<Reservas> todasReservas;

    public void initialize() {
        // Columnas de la tablaReservas
        campoID.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        campoCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        campoClase.setCellValueFactory(new PropertyValueFactory<>("nombreClase"));
        campoDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        campoFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        campoHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        campoHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));

        // Cargar todas las reservas
        List<Reservas> reservas = Reservas.obtenerTodasReservas();
        reservas.sort((r1, r2) -> Integer.compare(r2.getIdReserva(), r1.getIdReserva()));
        todasReservas = FXCollections.observableArrayList(reservas);
        tablaReservas.setItems(todasReservas);

        // Listener para filtrar por fecha
        id_datepicker.valueProperty().addListener((obs, oldDate, newDate) -> filtrarPorFecha(newDate));
    }

    private void filtrarPorFecha(LocalDate fecha) {
        if (fecha == null) {
            tablaReservas.setItems(todasReservas);
        } else {
            ObservableList<Reservas> filtradas = todasReservas.filtered(r -> r.getFechaReserva().isEqual(fecha));
            tablaReservas.setItems(filtradas);
        }
    }

    @FXML
    void onClickBajaAlta(ActionEvent event) {
        Reservas seleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            new Alert(Alert.AlertType.WARNING, "Debes seleccionar una reserva primero.", ButtonType.OK).showAndWait();
            return;
        }

        // Confirmación
        Alert confirmacion = new Alert(Alert.AlertType.NONE, "  ¿Seguro que quieres borrar esta reserva?", ButtonType.OK, ButtonType.CANCEL);
        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            boolean exito = Reservas.borrarReserva(seleccionada.getIdReserva());

            if (exito) {
                // Quitar de la lista principal ObservableList
                todasReservas.remove(seleccionada);
                // Quitar de la tabla si está filtrada
                tablaReservas.getItems().remove(seleccionada);
            } else {
                new Alert(Alert.AlertType.ERROR, "No se pudo borrar la reserva.", ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML
    void onClickHoy(ActionEvent event) {
        LocalDate hoy = LocalDate.now();
        id_datepicker.setValue(hoy);
    }

    @FXML
    void onClickTodasReservas(ActionEvent event) {
        id_datepicker.setValue(null);
    }
}
