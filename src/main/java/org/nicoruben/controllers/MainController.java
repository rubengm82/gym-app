package org.nicoruben.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

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

        // /////////////////////////////////////////
        // AQUI ESTARAN LOS BOTONES DE TOP Y LEFT //
        // /////////////////////////////////////////
        switch (clicked.getText()) {

            // BOTON TOP CLIENTES
            case "Clientes" -> {
                // BOTONES LEFT
                Button listarClientes = new Button("Listar Clientes");
                listarClientes.setMaxWidth(Double.MAX_VALUE);
                listarClientes.setOnAction(e -> showInCenter("ListarClientes"));

                Button nuevoCliente = new Button("Nuevo Cliente");
                nuevoCliente.setMaxWidth(Double.MAX_VALUE);
                nuevoCliente.setOnAction(e -> showInCenter("NuevoCliente"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarClientes, nuevoCliente);
            }

            // BOTON TOP ACTIVIDADES
            case "Actividades" -> {
                // BOTONES LEFT
                Button listarAct = new Button("Listar Actividades");
                listarAct.setMaxWidth(Double.MAX_VALUE);
                listarAct.setOnAction(e -> showInCenter("ListarActividades"));

                Button nuevaAct = new Button("Nueva Actividad");
                nuevaAct.setMaxWidth(Double.MAX_VALUE);
                nuevaAct.setOnAction(e -> showInCenter("NuevaActividad"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarAct, nuevaAct);
            }


        }
    }

    // METODOS DE MainController
    private void showInCenter(String fxmlFile) {
        try {
            // Limpia lo que haya en el centro
            centerPane.getChildren().clear();

            // Carga el FXML desde la carpeta resources/fxml (ajusta la ruta según tu estructura)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/centerPane/" + fxmlFile + ".fxml"));
            Node content = loader.load();

            // Hace que el nodo cargado se expanda en el StackPane
            StackPane.setAlignment(content, Pos.CENTER);

            // Añade el contenido al centerPane
            centerPane.getChildren().add(content);

        } catch (IOException e) {
            e.printStackTrace();
            centerPane.getChildren().add(new Label("Error cargando vista: " + fxmlFile));
        }
    }
}
