package org.nicoruben.controllers.centerPane;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.models.Clases;
import org.nicoruben.models.Planificaciones;

import java.util.Date;

public class EditarPlanificacionLunes {

    @FXML
    private ComboBox<?> combo_act;

    @FXML
    private ComboBox<?> combo_instructor;

    @FXML
    private Spinner<?> input_fin_h;

    @FXML
    private Spinner<?> input_fin_m;

    @FXML
    private Spinner<?> input_ini_h;

    @FXML
    private Spinner<?> input_ini_m;

    @FXML
    private Label labelTituloClientes;

    @FXML
    private Label labelTituloClientes1;

    @FXML
    private TableColumn<Planificaciones, String> table_clase;

    @FXML
    private TableColumn<Planificaciones, Date> table_h_fin;

    @FXML
    private TableColumn<Planificaciones, Date> table_h_ini;


    public void initialize(){

        campoID.setCellValueFactory(new PropertyValueFactory<>("id_clase"));

        table_h_ini.setCellValueFactory(new PropertyValueFactory<>(""));

    }
}

