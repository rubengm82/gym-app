package org.nicoruben.controllers.centerPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
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

    @FXML
    private ComboBox<Planificaciones> combo_planificacion;


    @FXML
    private PieChart asistenciaChart;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combo_dia.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"
        ));
        combo_dia.getSelectionModel().selectFirst();
        actualizarGrafico(combo_dia.getValue());
        combo_dia.setOnAction(event -> setDayCombo());

        List<Planificaciones> planificaciones = Planificaciones.obtenerTodasPlanificacionesActivas();


        ObservableList<Planificaciones> listaObservable = FXCollections.observableArrayList(planificaciones);

        combo_planificacion.setItems(listaObservable);


        combo_planificacion.setConverter(new javafx.util.StringConverter<Planificaciones>() {
            @Override
            public String toString(Planificaciones p) {
                if (p == null) return "";
                return p.getClase().getNombre() + " - " + p.getDia();
            }

            @Override
            public Planificaciones fromString(String string) {
                return null; // no se usa
            }
        });


        combo_planificacion.setCellFactory(lv -> new javafx.scene.control.ListCell<Planificaciones>() {
            @Override
            protected void updateItem(Planificaciones p, boolean empty) {
                super.updateItem(p, empty);

                if (empty || p == null || p.getClase() == null) {
                    setText(null);
                } else {
                    setText(p.getClase().getNombre() + " - " + p.getDia());
                }
            }
        });


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


    public void asistenciaChartAc(ActionEvent actionEvent) {
        Planificaciones selectedPlanificacion = combo_planificacion.getValue();
        System.out.println(selectedPlanificacion.getId_planificacion());

        int usadas = Reservas.contarReservasPorPlanificacionGrafi(selectedPlanificacion.getId_planificacion());
        int nousdas = Reservas.contarReservasPorPlanificacion(selectedPlanificacion.getId_planificacion());

        System.out.println(usadas);



    }
}
