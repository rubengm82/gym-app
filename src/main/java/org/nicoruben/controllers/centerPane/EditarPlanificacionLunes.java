package org.nicoruben.controllers.centerPane;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.models.Clases;
import org.nicoruben.models.Instructores;
import org.nicoruben.models.Planificaciones;

import java.util.List;

public class EditarPlanificacionLunes {

    @FXML private ComboBox<Clases> combo_act;
    @FXML private ComboBox<Instructores> combo_instructor;
    @FXML private Spinner<Integer> input_fin_h, input_fin_m, input_ini_h, input_ini_m;
    @FXML private Label labelTituloClientes, labelTituloClientes1;
    @FXML private TableView<Planificaciones> table_planificaciones;
    @FXML private TableColumn<Planificaciones, String> table_h_ini, table_h_fin, table_clase;
    @FXML private Button btnAgregar, btnBorrar;

    @FXML
    public void initialize() {
        // Configurar spinners (horas y minutos)
        input_ini_h.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 20, 8));
        input_fin_h.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 21, 9));
        input_ini_m.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0, 15));
        input_fin_m.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0, 15));

        // Configurar columnas de la tabla
        table_h_ini.setCellValueFactory(new PropertyValueFactory<>("hora_inicio"));
        table_h_fin.setCellValueFactory(new PropertyValueFactory<>("hora_fin"));
        table_clase.setCellValueFactory(data -> {
            Planificaciones p = data.getValue();
            String nombre = (p.getClase() != null) ? p.getClase().getNombre() : "";
            return new SimpleStringProperty(nombre);
        });


        combo_act.getItems().setAll(Clases.obtenerTodasClases());
        combo_instructor.getItems().setAll(Instructores.obtenerInstructores());
        // Cargar datos
        cargarPlanificacionesEnTabla();
    }

    private void cargarPlanificacionesEnTabla() {
        List<Planificaciones> lista = Planificaciones.obtenerPlanificaciones();
        if (lista != null) {
            table_planificaciones.getItems().setAll(lista);
        } else {
            showAlertError("Error", "No se pudieron cargar las planificaciones.");
        }
    }



    private void showAlertError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlertInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
