package org.nicoruben.controllers.centerPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import org.nicoruben.models.Clientes;
import org.nicoruben.models.Planificaciones;
import org.nicoruben.models.Reservas;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ListarGrafico implements Initializable {

    @FXML
    private BarChart<String, Number> graficoOcupacion;
    @FXML
    private ComboBox<String> combo_dia;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ComboBox<Planificaciones> combo_planificacion;
    @FXML
    private ComboBox<String> combo_dia_asi;
    @FXML
    private PieChart asistenciaChart;
    @FXML
    private Label infoPieChart;
    @FXML
    private Label titleAsis;
    @FXML
    private Label titleDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combo_dia.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"
        ));
        combo_dia.getSelectionModel().selectFirst();

        combo_dia_asi.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"
        ));

        combo_dia_asi.getSelectionModel().selectFirst();
        combo_dia_asi.setOnAction(e -> setDayCombo2());

        String diaHoy = obtenerDiaSemanaActual();
        titleDay.setText("Número de reservas por planificación del " + diaHoy);
        titleAsis.setText("Relación de clientes activos / no activos");

        combo_dia.setValue(diaHoy);
        combo_dia_asi.setValue(diaHoy);

        setDayCombo2();
        actualizarGrafico(combo_dia.getValue());
        asistenciaChartAc();
    }

    @FXML
    private void setDayCombo() {
        String diaSeleccionado = combo_dia.getValue();
        if (diaSeleccionado != null && !diaSeleccionado.isEmpty()) {
            actualizarGrafico(diaSeleccionado);
        }
    }

    @FXML
    void setDayCombo2() {
        String diaSeleccionado = combo_dia_asi.getValue();

        if (diaSeleccionado != null && !diaSeleccionado.isEmpty()) {

            List<Planificaciones> planificaciones =
                    Planificaciones.obtenerPlanificacionesPorDia(diaSeleccionado);

            ObservableList<Planificaciones> listaObservable =
                    FXCollections.observableArrayList(planificaciones);

            combo_planificacion.setItems(listaObservable);

            combo_planificacion.setConverter(new StringConverter<>() {
                @Override
                public String toString(Planificaciones p) {
                    if (p == null) return "";
                    return p.getClase().getNombre() + " - " + p.getHora_inicio();
                }

                @Override
                public Planificaciones fromString(String string) {
                    return null;
                }
            });

            combo_planificacion.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(Planificaciones p, boolean empty) {
                    super.updateItem(p, empty);
                    if (empty || p == null) {
                        setText(null);
                    } else {
                        setText(p.getClase().getNombre() + " - " + p.getHora_inicio());
                    }
                }
            });
        }
    }

    private void actualizarGrafico(String dia) {
        boolean mostrarDatos = true;

        if (dia.equals("Fin de semana")) {
            mostrarDatos = false;
        }

        if (mostrarDatos) {
            List<Planificaciones> planificaciones = Planificaciones.obtenerPlanificacionesPorDia(dia);
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName("Reservas del " + dia);

            for (Planificaciones p : planificaciones) {
                int reservas = Reservas.contarReservasPorPlanificacionParaGrafica(p.getId_planificacion(), 1);
                String nombreClase = (p.getClase() != null && p.getClase().getNombre() != null) ? p.getClase().getNombre() : "Clase sin nombre";
                String etiqueta = nombreClase + " (" + p.getHora_inicio() + ")";
                serie.getData().add(new XYChart.Data<>(etiqueta, reservas));
            }

            Platform.runLater(() -> {
                graficoOcupacion.getData().clear();
                graficoOcupacion.getData().add(serie);
                xAxis.setLabel("Planificación (Clase - Hora)");
                yAxis.setLabel("Número de Reservas");
                titleDay.setText("Número de reservas por planificación del " + dia);
            });

        } else {
            // Fin de semana Texto en Label
            Platform.runLater(() -> {
                graficoOcupacion.getData().clear();
                xAxis.setLabel("");
                yAxis.setLabel("");
                titleDay.setText("No hay planificaciones fines de semana");
            });
        }
    }

    @FXML
    public void asistenciaChartAc() {
        infoPieChart.setText("");

        int activos = Clientes.contarClientesPorEstado(1);
        int inactivos = Clientes.contarClientesPorEstado(0);

        float total = activos + inactivos;

        asistenciaChart.getData().clear();

        if (total == 0) {
            infoPieChart.setText("No hay clientes registrados.");
        } else {
            PieChart.Data activosData =
                    new PieChart.Data("Activos (" + activos + ")", activos);

            PieChart.Data inactivosData =
                    new PieChart.Data("Inactivos (" + inactivos + ")", inactivos);

            asistenciaChart.getData().addAll(activosData, inactivosData);
        }
    }

    private String obtenerDiaSemanaActual() {
        DayOfWeek dia = LocalDate.now().getDayOfWeek();

        return switch (dia) {
            case MONDAY -> "Lunes";
            case TUESDAY -> "Martes";
            case WEDNESDAY -> "Miércoles";
            case THURSDAY -> "Jueves";
            case FRIDAY -> "Viernes";
            default -> "Fin de semana";
        };
    }
}
