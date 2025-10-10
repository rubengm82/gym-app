package org.nicoruben.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.nicoruben.models.Clientes;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane centerPane;
    private static StackPane staticCenterPane;

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
    private Button btnInformes;


    @FXML
    public void initialize() {
        staticCenterPane = centerPane; // guarda el pane al iniciar
    }

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
                listarClientes.setOnAction(e -> showInCenter("listarClientes"));

                Button nuevoCliente = new Button("Nuevo Cliente");
                nuevoCliente.setMaxWidth(Double.MAX_VALUE);
                nuevoCliente.setOnAction(e -> showInCenter("nuevoCliente"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarClientes, nuevoCliente);
            }

            // BOTON TOP CLASES
            case "Clases" -> {
                // BOTONES LEFT
                Button listarClases = new Button("Listar Clases");
                listarClases.setMaxWidth(Double.MAX_VALUE);
                listarClases.setOnAction(e -> showInCenter("listarClases"));

                Button nuevaClase = new Button("Nueva Clase");
                nuevaClase.setMaxWidth(Double.MAX_VALUE);
                nuevaClase.setOnAction(e -> showInCenter("nuevaClase"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarClases, nuevaClase);
            }

            // BOTON TOP INFORMES
            case "Informes" -> {
                // BOTONES LEFT
                Button graficaOne = new Button("Gráfica 01");
                graficaOne.setMaxWidth(Double.MAX_VALUE);
                graficaOne.setOnAction(e -> showInCenter("graficaOne"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(graficaOne);
            }
        }
    }



    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////

    // Metodo para mostrar el centerPane sin enviar objeto
    public static void showInCenter(String fxmlFile) {
        try {
            // Limpia lo que haya en el centro
            staticCenterPane.getChildren().clear();

            // Carga el FXML desde la carpeta resources/fxml (ajusta la ruta según tu estructura)
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/views/centerPane/" + fxmlFile + ".fxml"));
            Node content = loader.load();

            // Hace que el nodo cargado se expanda en el StackPane
            StackPane.setAlignment(content, Pos.CENTER);

            // Añade el contenido al centerPane
            staticCenterPane.getChildren().add(content);

        } catch (IOException e) {
            e.printStackTrace();
            staticCenterPane.getChildren().add(new Label("Error cargando vista: " + fxmlFile));
        }
    }

    // Metodo para mostrar el centerPane pero pudiendo enviar un Objeto del tipo que le digamos
    public static void showInCenterWithData(String fxmlFile, Clientes cliente) {
        try {
            staticCenterPane.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/views/centerPane/" + fxmlFile + ".fxml"));
            Node content = loader.load();

            // Obtenemos el controlador del FXML cargado para poder llamar sus métodos
            // (como recogerObjeto) y pasarle datos que actualicen la vista.
            Object controller = loader.getController();

            try {
                controller.getClass().getMethod("recogerObjeto", Clientes.class).invoke(controller, cliente);
            } catch (NoSuchMethodException ignored) {
                System.out.println("El controlador " + controller.getClass().getSimpleName() + " no tiene método recogerObjeto(Clientes).");
            } catch (Exception e) {
                e.printStackTrace();
            }

            StackPane.setAlignment(content, Pos.CENTER);
            staticCenterPane.getChildren().add(content);

        } catch (Exception e) {
            e.printStackTrace();
            staticCenterPane.getChildren().add(new Label("Error cargando vista: " + fxmlFile));
        }
    }

}
