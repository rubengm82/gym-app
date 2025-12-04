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

import java.text.Normalizer;
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

    @FXML
    private TableColumn<Reservas, String> campoConfirmacion;

    @FXML
    private TextField searchBar;

    public void initialize() {
        // Columnas de la tabla
        campoID.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        campoCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        campoClase.setCellValueFactory(new PropertyValueFactory<>("nombreClase"));
        campoDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        campoFechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        campoHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        campoHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));

        campoConfirmacion.setCellValueFactory(cellData -> {
            Reservas reserva = cellData.getValue();
            String confirmacion = (reserva.getEstado() == 1) ? "NO" : (reserva.getEstado() == 2 ? "SI" : "");
            return new javafx.beans.property.SimpleStringProperty(confirmacion);
        });

        // Cargar todas las reservas ACTIVAS
        List<Reservas> reservas = Reservas.obtenerTodasReservas();
        List<Reservas> activas = new ArrayList<>();

        for (Reservas r : reservas) {
            if (r.getEstado() == 1 || r.getEstado() == 2) {
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

        // Filtra search bar
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });
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

        boolean seleccionValida = (seleccionada != null);

        if (!seleccionValida) {
            new Alert(Alert.AlertType.WARNING, "Debes seleccionar una reserva primero.", ButtonType.OK)
                    .showAndWait();
        } else {
            Alert confirmacion = new Alert(
                    Alert.AlertType.NONE,
                    "  ¿Seguro que quieres cancelar esta reserva?",
                    ButtonType.OK,
                    ButtonType.CANCEL
            );

            boolean confirmo = (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK);

            if (confirmo) {
                boolean exito = Reservas.cancelarReserva(seleccionada.getIdReserva());

                if (exito) {
                    todasReservas.remove(seleccionada);
                    tablaReservas.getItems().remove(seleccionada);
                } else {
                    new Alert(Alert.AlertType.ERROR, "No se pudo cancelar la reserva.", ButtonType.OK)
                            .showAndWait();
                }
            }
        }
    }

    @FXML
    void onclickComboboxDia(ActionEvent event) {
        filtrarPorDia(combobox_dia.getValue());
    }

    // Filtrado Tabla searchBar
    private void filtrarTabla(String filtro) {
        if (filtro == null) filtro = "";

        // Normalizar filtro: quitar acentos y pasar a minúsculas
        String filtroNormalizado = Normalizer.normalize(filtro, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();

        List<Reservas> filtrados = new ArrayList<>();

        for (Reservas reserva : todasReservas) {

            boolean coincide = false;

            // Si el filtro está vacío, añadir directamente
            if (filtro.isEmpty()) {
                coincide = true;
            } else {
                for (TableColumn<Reservas, ?> col : tablaReservas.getColumns()) {

                    Object cellValue = col.getCellObservableValue(reserva).getValue();
                    if (cellValue != null) {

                        String valorNormalizado = Normalizer.normalize(cellValue.toString(), Normalizer.Form.NFD)
                                .replaceAll("\\p{M}", "")
                                .toLowerCase();

                        // Marcar coincidencia si contiene el filtro
                        coincide = coincide || valorNormalizado.contains(filtroNormalizado);
                    }
                }
            }

            // Añadir a la lista filtrada si coincide
            if (coincide) {
                filtrados.add(reserva);
            }
        }

        tablaReservas.setItems(FXCollections.observableArrayList(filtrados));
    }
}
