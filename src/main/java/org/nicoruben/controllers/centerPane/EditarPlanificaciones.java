package org.nicoruben.controllers.centerPane;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.models.Clases;
import org.nicoruben.models.Instructores;
import org.nicoruben.models.Planificaciones;

import javafx.event.ActionEvent;
import java.time.LocalTime;
import java.util.List;

public class EditarPlanificaciones {

    @FXML private Label error_message;
    @FXML private ComboBox<Clases> combo_act;
    @FXML private ComboBox<Instructores> combo_instructor;
    @FXML private ComboBox<String> combo_dia;
    @FXML private Spinner<Integer> input_fin_h, input_fin_m, input_ini_h, input_ini_m;
    @FXML private Label labelTituloClientes, labelTituloClientes1;
    @FXML private TableView<Planificaciones> table_planificaciones;
    @FXML private TableColumn<Planificaciones, String> table_h_ini, table_h_fin, table_clase, table_instructor;
    @FXML private Button btnAgregar, btnBorrar;
    @FXML private Button delete;


    @FXML
    private Label success_message;

    @FXML
    public void initialize() {
        input_ini_h.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 20, 8));
        input_fin_h.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 21, 9));
        input_ini_m.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0, 15));
        input_fin_m.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0, 15));

        table_h_ini.setCellValueFactory(new PropertyValueFactory<>("hora_inicio"));
        table_h_fin.setCellValueFactory(new PropertyValueFactory<>("hora_fin"));
        table_clase.setCellValueFactory(data -> {
            Planificaciones p = data.getValue();
            String nombre = (p.getClase() != null) ? p.getClase().getNombre() : "";
            return new SimpleStringProperty(nombre);
        });
        table_instructor.setCellValueFactory(data -> {
            Planificaciones ins = data.getValue();
            String nombre = (ins.getInstructor() != null) ? ins.getInstructor().getNombre() + " " +  ins.getInstructor().getApellido1(): "";
            return new SimpleStringProperty(nombre);
        });

        combo_act.getItems().setAll(Clases.obtenerTodasClases());
        combo_instructor.getItems().setAll(Instructores.obtenerInstructores());
        combo_dia.setItems(FXCollections.observableArrayList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes"));

        combo_dia.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarPlanificacionesEnTabla(newVal);
            }
        });

        combo_dia.getSelectionModel().selectFirst();


    }

    private void cargarPlanificacionesEnTabla(String dia) {
        List<Planificaciones> lista = Planificaciones.obtenerPlanificacionesPorDia(dia);
        if (lista != null) {
            table_planificaciones.getItems().setAll(lista);
        } else {
            showAlertError("Error", "No se pudieron cargar las planificaciones.");
        }
    }

    @FXML
    void crearPlanificacion(ActionEvent event) {
        if (input_ini_h.getValue() == null || input_fin_h.getValue() == null || input_ini_m.getValue() == null || input_fin_m.getValue() == null) {
            error_message.setText("Debes ingresar todas las horas y minutos.");
            return;
        }
        if (combo_instructor.getSelectionModel().isEmpty()) {
            error_message.setText("Debes ingresar un instructor");
            return;
        }
        if (combo_act.getSelectionModel().isEmpty()) {
            error_message.setText("Debes ingresar una actividad.");
            return;
        }

        int hora_inicio = input_ini_h.getValue();
        int hora_fin = input_fin_h.getValue();
        int min_inicio = input_ini_m.getValue();
        int min_fin = input_fin_m.getValue();
        int instructor = combo_instructor.getSelectionModel().getSelectedItem().getId();
        int classe = combo_act.getSelectionModel().getSelectedItem().getId_clase();
        String dia = combo_dia.getSelectionModel().getSelectedItem();

        LocalTime inicio = LocalTime.of(hora_inicio, min_inicio);
        LocalTime fin = LocalTime.of(hora_fin, min_fin);

        if (Planificaciones.verificarPlanificacion(dia, inicio, fin) > 0) {
            error_message.setText("Horario ocupado");
        } else {
            Planificaciones.insertPlanificaciones(dia, inicio, fin, classe, instructor);
            cargarPlanificacionesEnTabla(dia);
            error_message.setText("");
            success_message.setText("Planificacion creada correctamente");
        }
    }

    @FXML
    void deleteActivity(ActionEvent event) {
        Planificaciones planificacionDel = table_planificaciones.getSelectionModel().getSelectedItem();
        if (planificacionDel != null) {
            Planificaciones.delPlanificacion(planificacionDel.getId_planificacion());
            cargarPlanificacionesEnTabla(combo_dia.getSelectionModel().getSelectedItem());
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
