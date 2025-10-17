package org.nicoruben.controllers.centerPane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.ArrayList;
import java.util.List;

public class EditarPlanificacionLunes {

    @FXML
    private ComboBox<?> combo_act;

    @FXML
    private ComboBox<?> combo_instructor;

    @FXML
    private Spinner<Integer> input_fin_h;

    @FXML
    private Spinner<Integer> input_fin_m;

    @FXML
    private Spinner<Integer> input_ini_h;

    @FXML
    private Spinner<Integer> input_ini_m;

    @FXML
    private Label labelTituloClientes;

    @FXML
    private Label labelTituloClientes1;

    @FXML
    private Button submit_button;



    public void initialize()
    {
            input_ini_h.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(9, 21, 9, 1)
            );

            input_fin_h.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(9, 21, 9, 1)
            );

            input_ini_m.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45, 0, 15)
            );

            input_fin_m.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45, 0, 15)
            );
    }

}
