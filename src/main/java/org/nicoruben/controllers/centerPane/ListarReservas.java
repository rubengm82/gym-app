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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private TableColumn<Reservas, String> campoDia;

    @FXML
    private TableColumn<Reservas, LocalTime> campoHoraInicio;

    @FXML
    private TableColumn<Reservas, LocalTime> campoHoraFin;

    @FXML
    private TableColumn<Reservas, Integer> campoID;

    @FXML
    private Label labelTituloClientes;

    @FXML
    private TableView<Reservas> tablaReservas;

    private ObservableList<Reservas> todasReservas;

    @FXML
    private ComboBox<String> combobox_dia;

    public void initialize() {
        // Columnas de la tabla
        campoID.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        campoCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        campoClase.setCellValueFactory(new PropertyValueFactory<>("nombreClase"));
        campoDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        campoFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        campoHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        campoHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));

        // Cargar todas las reservas ACTIVAS
        List<Reservas> reservas = Reservas.obtenerTodasReservas();
        List<Reservas> activas = new ArrayList<>();

        for (Reservas r : reservas) {
            if (r.getEstado() == 1) {
                activas.add(r);
            }
        }

        activas.sort((r1, r2) -> Integer.compare(r2.getIdReserva(), r1.getIdReserva()));
        todasReservas = FXCollections.observableArrayList(activas);
        tablaReservas.setItems(todasReservas);


        // Inicializar ComboBox
        ObservableList<String> dias = FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Todos"
        );
        combobox_dia.setItems(dias);

        // Seleccionar automáticamente el día actual
        String diaHoy = obtenerDiaSemanaActual();
        if (dias.contains(diaHoy)) {
            combobox_dia.setValue(diaHoy);
        } else {
            combobox_dia.setValue("Todos");
        }

        filtrarPorDia(diaHoy);
    }

    private void filtrarPorDia(String diaSeleccionado) {
        if (diaSeleccionado == null || diaSeleccionado.equals("Todos")) {
            tablaReservas.setItems(todasReservas);
        } else {
            ObservableList<Reservas> filtradas = todasReservas.filtered(
                    r -> r.getDia().equalsIgnoreCase(diaSeleccionado)
            );
            tablaReservas.setItems(filtradas);
        }
    }

    private String obtenerDiaSemanaActual() {
        DayOfWeek dia = LocalDate.now().getDayOfWeek();
        switch (dia) {
            case MONDAY:
                return "Lunes";
            case TUESDAY:
                return "Martes";
            case WEDNESDAY:
                return "Miércoles";
            case THURSDAY:
                return "Jueves";
            case FRIDAY:
                return "Viernes";
            default:
                return "Todos"; // Por si es sábado o domingo
        }
    }

    @FXML
    void onClickBajaAlta(ActionEvent event) {
        Reservas seleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            new Alert(Alert.AlertType.WARNING, "Debes seleccionar una reserva primero.", ButtonType.OK).showAndWait();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.NONE, "¿Seguro que quieres cancelar esta reserva?", ButtonType.OK, ButtonType.CANCEL);
        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            boolean exito = Reservas.cancelarReserva(seleccionada.getIdReserva());

            if (exito) {
                todasReservas.remove(seleccionada);
                tablaReservas.getItems().remove(seleccionada);
            } else {
                new Alert(Alert.AlertType.ERROR, "No se pudo cancelar la reserva.", ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML
    void onclickComboboxDia(ActionEvent event) {
        filtrarPorDia(combobox_dia.getValue());
    }
}
