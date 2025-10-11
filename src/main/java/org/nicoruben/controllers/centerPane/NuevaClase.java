package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nicoruben.models.Instructores;

import java.util.ArrayList;
import java.util.List;

public class NuevaClase {

    @FXML
    private Button btn_aceptar;

    @FXML
    private Button btn_reset;

    @FXML
    private Spinner<Integer> spinner_aforo;

    @FXML
    private TextField input_descripcion;

    @FXML
    private Label input_error;

    @FXML
    private TextField input_instructor;

    @FXML
    private TextField input_nombre;

    @FXML
    private ComboBox<String> combo_dia;

    @FXML
    private ComboBox<Instructores> combo_instructor;

    @FXML
    private Spinner<Integer> spinner_hora;

    @FXML
    private Spinner<Integer> spinner_minutos;


    /* AUTO-LOAD al cargar la vista */
    public void initialize() {

        /* SELECTS */
        // DIAS
        // Incializacion del ComboBox de los días
        combo_dia.setItems(FXCollections.observableArrayList("LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"));


        // INSTRUCTORES
        List<Instructores> todosInstructores = Instructores.obtenerInstructores();
        List<Instructores> instructoresActivos = new ArrayList<>();

        // Solo seleccionmos los Instructores ACTIVOS
        for (Instructores i : todosInstructores) {
            if (i.getEstado() == 1) {
                instructoresActivos.add(i);
            }
        }
        combo_instructor.setItems(FXCollections.observableArrayList(instructoresActivos));

        // TODO - TUTORIAL como funcionan los combobox, para obtener su id o poner value en su campo
        // setCellFactory define cómo se muestran los items en la lista desplegable.
        // setButtonCell define cómo se muestra el item seleccionado en el combo.
        // Instructor seleccionado = combo_instructor.getValue();
        // int id_instructor = seleccionado.getId(); <-- De aqui sacare la id para subir a BBDD y poner su FK

        // Mostrar nombre completo en el ComboBox
        combo_instructor.setCellFactory(c -> new ListCell<Instructores>() {
            @Override
            protected void updateItem(Instructores item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " " + item.getApellido() + " " + item.getApellidos2());
                }
            }
        });

        // También para la selección actual
        combo_instructor.setButtonCell(new ListCell<Instructores>() {
            @Override
            protected void updateItem(Instructores item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " " + item.getApellido() + " " + item.getApellidos2());
                }
            }
        });


        /* SPINNERS */
        // Spinner de horas: de 0 a 5 -- HORAS
        spinner_hora.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,3,0));
        // Spinner de minutos: de 0 a 59 -- MINUTOS
        spinner_minutos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0, 15));
        // Spinner de personas en el aforo -- AFORO
        spinner_aforo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0));

        // TODO: De esta manera capturare las horas y minutos y generare un LocalTime para la BBDD
        // LocalTime duracion = LocalTime.of(horas, minutos);

    }



    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////
    @FXML
    void onClickAceptar(ActionEvent event) {

    }

    @FXML
    void onClickReset(ActionEvent event) {

    }
}
