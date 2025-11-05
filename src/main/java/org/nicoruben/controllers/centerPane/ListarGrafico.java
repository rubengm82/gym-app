package org.nicoruben.controllers.centerPane;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.nicoruben.models.Planificaciones;
import org.nicoruben.models.Reservas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListarGrafico implements Initializable {

    @FXML
    private BarChart<String, Number> graficoOcupacion;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarGrafico();
    }

    private void inicializarGrafico() {


        // Obtener todas las planificaciones activas
        List<Planificaciones> planificaciones = Planificaciones.obtenerTodasPlanificacionesActivas();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Reservas por Planificación");


        for (Planificaciones p : planificaciones) {
            try {
                int reservas = Reservas.contarReservasPorPlanificacion(p.getId_planificacion());
                String nombreClase = (p.getClase() != null && p.getClase().getNombre() != null)
                        ? p.getClase().getNombre()
                        : "Clase sin nombre";

                String etiqueta = nombreClase + " (" + p.getDia() + ")";


                serie.getData().add(new XYChart.Data<>(etiqueta, reservas));
            } catch (Exception e) {
                System.err.println("Error procesando planificacion ID " + p.getId_planificacion() + ": " + e.getMessage());
            }
        }


        Platform.runLater(() -> {
            graficoOcupacion.getData().clear();
            graficoOcupacion.getData().add(serie);
            graficoOcupacion.setTitle("Reservas por Planificacin");
            xAxis.setLabel("Planificacion");
            yAxis.setLabel("Nº de Reservas");

        });
    }
}
