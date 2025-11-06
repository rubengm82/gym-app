package org.nicoruben.controllers.centerPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import org.nicoruben.models.Planificaciones;
import org.nicoruben.models.Reservas;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combo_dia.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"
        ));
        combo_dia.getSelectionModel().selectFirst();
        actualizarGrafico(combo_dia.getValue());
        combo_dia.setOnAction(event -> setDayCombo());
    }

    @FXML
    private void setDayCombo() {
        String diaSeleccionado = combo_dia.getValue();
        if (diaSeleccionado != null && !diaSeleccionado.isEmpty()) {
            actualizarGrafico(diaSeleccionado);
        }
    }

    private void actualizarGrafico(String dia) {
        List<Planificaciones> planificaciones = Planificaciones.obtenerPlanificacionesPorDia(dia);
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Reservas del " + dia);

        for (Planificaciones p : planificaciones) {
            int reservas = Reservas.contarReservasPorPlanificacion(p.getId_planificacion());
            String nombreClase = (p.getClase() != null && p.getClase().getNombre() != null)
                    ? p.getClase().getNombre()
                    : "Clase sin nombre";
            String etiqueta = nombreClase + " (" + p.getHora_inicio() + ")";
            serie.getData().add(new XYChart.Data<>(etiqueta, reservas));
        }

        Platform.runLater(() -> {
            graficoOcupacion.getData().clear();
            graficoOcupacion.getData().add(serie);
            graficoOcupacion.setTitle("Reservas del " + dia);
            xAxis.setLabel("Planificación (Clase - Hora)");
            yAxis.setLabel("Número de Reservas");
        });
    }
}
