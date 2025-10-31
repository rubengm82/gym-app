package org.nicoruben.controllers.centerPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import org.nicoruben.services.Aforo;

import java.util.List;

public class ListarGrafico {

    @FXML
    private ProgressBar progressYoga, progressCrossfit, progressPilates;

    private List<Aforo> clases;

    public void initialize() {
        clases = List.of(
                new Aforo("Yoga", 12, 20, progressYoga),
                new Aforo("Crossfit", 10, 15, progressCrossfit),
                new Aforo("Pilates", 10, 18, progressPilates) // 18 → 10 para poder sumar
        );
    }

    public void sumarInscrito(String nombre) {
        for (Aforo clase : clases) {
            if (clase.getNombre().equals(nombre)) {
                clase.sumarInscrito();
            }
        }
    }

    @FXML
    void onClickSumarYoga(ActionEvent event) {
        sumarInscrito("Yoga");
    }

    @FXML
    void onClickSumarCrossfit(ActionEvent event) {
        sumarInscrito("Crossfit");
    }

    @FXML
    void onClickSumarPilates(ActionEvent event) {
        sumarInscrito("Pilates");
    }

}
