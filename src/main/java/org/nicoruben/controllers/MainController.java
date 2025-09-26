package org.nicoruben.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private StackPane centerPane;

    @FXML
    private VBox leftMenu;

    @FXML
    private Button btnActividades;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnInstructores;

    @FXML
    private Button btnReservas;

    @FXML
    private Button btnPlanificacion;


    @FXML
    public void handleTopButton(javafx.event.ActionEvent event) {
        Button clicked = (Button) event.getSource();
        leftMenu.getChildren().clear();

        switch (clicked.getText()) {
            case "Clientes" -> {
                Button listarClientes = new Button("Listar Clientes");
                listarClientes.setMaxWidth(Double.MAX_VALUE);
                listarClientes.setOnAction(e -> showInCenter("Listar Clientes"));

                Button nuevoCliente = new Button("Nuevo Cliente");
                nuevoCliente.setMaxWidth(Double.MAX_VALUE);
                nuevoCliente.setOnAction(e -> showInCenter("Nuevo Cliente"));

                leftMenu.getChildren().addAll(listarClientes, nuevoCliente);
            }
            case "Actividades" -> {
                Button listarAct = new Button("Listar Actividades");
                listarAct.setMaxWidth(Double.MAX_VALUE);
                listarAct.setOnAction(e -> showInCenter("Listar Actividades"));

                Button nuevaAct = new Button("Nueva Actividad");
                nuevaAct.setMaxWidth(Double.MAX_VALUE);
                nuevaAct.setOnAction(e -> showInCenter("Nueva Actividad"));

                leftMenu.getChildren().addAll(listarAct, nuevaAct);
            }

            case "Instructores" -> {
                Button listaInstruc = new Button("Listar Intructores/as");
                listaInstruc.setMaxWidth(Double.MAX_VALUE);
                listaInstruc.setOnAction(e -> showInCenter("Listar Intructores/as"));

                Button nuevoInstruc = new Button("Nuevo Intructor/a");
                nuevoInstruc.setMaxWidth(Double.MAX_VALUE);
                nuevoInstruc.setOnAction(e -> showInCenter("Nuevo Intructor/a"));

                leftMenu.getChildren().addAll(listaInstruc, nuevoInstruc);
            }

            case "Reservas" -> {
                Button listaReserva = new Button("Listar Reservas");
                listaReserva.setMaxWidth(Double.MAX_VALUE);
                listaReserva.setOnAction(e -> showInCenter("Listar Reservas"));

                Button nuevaReserva = new Button("Nuevo Reservas");
                nuevaReserva.setMaxWidth(Double.MAX_VALUE);
                nuevaReserva.setOnAction(e -> showInCenter("Nueva Reserva"));

                leftMenu.getChildren().addAll(listaReserva, nuevaReserva);
            }

            case "Planificación" -> {
                Button listaPlanificacion = new Button("Listar Planificaciones");
                listaPlanificacion.setMaxWidth(Double.MAX_VALUE);
                listaPlanificacion.setOnAction(e -> showInCenter("Listar Planificaciones"));

                Button nuevaPlanificacion = new Button("Nueva Planificación");
                nuevaPlanificacion.setMaxWidth(Double.MAX_VALUE);
                nuevaPlanificacion.setOnAction(e -> showInCenter("Nueva Planificación"));

                leftMenu.getChildren().addAll(listaPlanificacion, nuevaPlanificacion);
            }

        }
    }

    private void showInCenter(String text) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(new Label(text));
    }
}
