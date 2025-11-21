package org.nicoruben.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.nicoruben.services.UtilsGlobal;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

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
    private Button btnSalir;


    @FXML
    public void initialize() {
        staticCenterPane = centerPane; // guarda el pane al iniciar
    }

    @FXML
    public void handleTopButton(ActionEvent event) {
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
                listarClientes.setOnAction(e -> showInCenter("listarClientes", null));

                Button nuevoCliente = new Button("Nuevo/a Cliente/a");
                nuevoCliente.setMaxWidth(Double.MAX_VALUE);
                nuevoCliente.setOnAction(e -> showInCenter("nuevoCliente", null));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarClientes, nuevoCliente);

                showInCenter("listarClientes", null);
            }

            // BOTON TOP CLASES
            case "Clases" -> {
                // BOTONES LEFT
                Button listarClases = new Button("Listar Clases");
                listarClases.setMaxWidth(Double.MAX_VALUE);
                listarClases.setOnAction(e -> showInCenter("listarClases", null));

                Button nuevaClase = new Button("Nueva Clase");
                nuevaClase.setMaxWidth(Double.MAX_VALUE);
                nuevaClase.setOnAction(e -> showInCenter("nuevaClase", null));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarClases, nuevaClase);

                showInCenter("listarClases", null);
            }

            // BOTON TOP INFORMES
            case "Gráficas" -> {


                showInCenter("grafico", null);
            }

            // BOTON TOP INSTRUCTORES
            case "Instructores" -> {
                // BOTONES LEFT
                Button listarActividades = new Button("Listar Instructores");
                listarActividades.setMaxWidth(Double.MAX_VALUE);
                listarActividades.setOnAction(e -> showInCenter("listarInstructores", null));

                Button nuevaActividad = new Button("Nuevo Instructor/a");
                nuevaActividad.setMaxWidth(Double.MAX_VALUE);
                nuevaActividad.setOnAction(e -> showInCenter("nuevoInstructor", null));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarActividades, nuevaActividad);

                showInCenter("listarInstructores", null);
            }

            // BOTON TOP PLANIFICACIONES
            case "Planificaciones" -> {
                showInCenter("editarPlanficaciones", null);
            }

            // BOTON TOP RESERVAS
            case "Reservas" -> {
                // BOTONES LEFT
                Button listarReservas = new Button("Listar Reservas");
                listarReservas.setMaxWidth(Double.MAX_VALUE);
                listarReservas.setOnAction(e -> showInCenter("listarReservas", null));

                Button nuevaReserva = new Button("Nueva Reserva");
                nuevaReserva.setMaxWidth(Double.MAX_VALUE);
                nuevaReserva.setOnAction(e -> showInCenter("nuevaReserva", null));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarReservas, nuevaReserva);

                showInCenter("listarReservas", null);
            }

        }
    }

    // Metodo para salir de la sesion.
    @FXML
    void onClickSalir(ActionEvent event) {
        // Crear el cuadro de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación de salida");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas salir de la aplicación?");

        // Personalizar los botones
        ButtonType btnAceptar = new ButtonType("Salir");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btnAceptar, btnCancelar);

        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // Si el usuario confirma, ejecutar la acción de salir
        if (resultado.isPresent() && resultado.get() == btnAceptar) {
            try {
                UtilsGlobal.goToSceneWithButton(btnSalir, "/views/login/login.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////

    public static void showInCenter(String fxmlFile, Object data) {
        try {
            staticCenterPane.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/views/centerPane/" + fxmlFile + ".fxml"));
            Node content = loader.load();

            if (data != null) {
                Object controller = loader.getController();
                boolean encontroMetodo = false;

                for (Method method : controller.getClass().getMethods()) {
                    if (method.getName().equals("recogerObjeto") &&
                            method.getParameterCount() == 1 &&
                            method.getParameters()[0].getType().isAssignableFrom(data.getClass())) {
                        try {
                            method.invoke(controller, data);
                            encontroMetodo = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error invocando recogerObjeto en " + controller.getClass().getSimpleName());
                        }
                    }
                }

                if (!encontroMetodo) {
                    System.out.println("El controlador " + controller.getClass().getSimpleName() +
                            " no tiene un método recogerObjeto compatible con: " + data.getClass().getSimpleName());
                }
            }

            StackPane.setAlignment(content, Pos.CENTER);
            staticCenterPane.getChildren().add(content);

        } catch (Exception e) {
            e.printStackTrace();
            staticCenterPane.getChildren().add(new Label("Error cargando vista: " + fxmlFile));
        }
    }

}
