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
                listarClientes.setOnAction(e -> showInCenter("listarClientes"));

                Button nuevoCliente = new Button("Nuevo Cliente");
                nuevoCliente.setMaxWidth(Double.MAX_VALUE);
                nuevoCliente.setOnAction(e -> showInCenter("nuevoCliente"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarClientes, nuevoCliente);

                showInCenter("listarClientes");
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

                showInCenter("listarClases");
            }

            // BOTON TOP INFORMES
            case "Informes" -> {
                // BOTONES LEFT
                Button graficaOne = new Button("Gráfico");
                graficaOne.setMaxWidth(Double.MAX_VALUE);
                graficaOne.setOnAction(e -> showInCenter("grafico"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(graficaOne);

                showInCenter("grafico");
            }

            // BOTON TOP INSTRUCTORES
            case "Instructores" -> {
                // BOTONES LEFT
                Button listarActividades = new Button("Listar Instructores");
                listarActividades.setMaxWidth(Double.MAX_VALUE);
                listarActividades.setOnAction(e -> showInCenter("listarInstructores"));

                Button nuevaActividad = new Button("Nuevo Instructor");
                nuevaActividad.setMaxWidth(Double.MAX_VALUE);
                nuevaActividad.setOnAction(e -> showInCenter("nuevoInstructor"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarActividades, nuevaActividad);

                showInCenter("listarInstructores");
            }

            // BOTON TOP PLANIFICACIONES
            case "Planificaciones" -> {
                // BOTONES LEFT
                Button lunes = new Button("Lunes");
                lunes.setMaxWidth(Double.MAX_VALUE);
                lunes.setOnAction(e -> showInCenter("editarPlanficacionLunes"));

                Button martes = new Button("Martes");
                martes.setMaxWidth(Double.MAX_VALUE);

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(lunes, martes);

                showInCenter("editarPlanficacionLunes");
            }

            // BOTON TOP RESERVAS
            case "Reservas" -> {
                // BOTONES LEFT
                Button listarReservas = new Button("Listar Reservas");
                listarReservas.setMaxWidth(Double.MAX_VALUE);
                listarReservas.setOnAction(e -> showInCenter("listarReservas"));

                Button nuevaReserva = new Button("Nueva Reserva");
                nuevaReserva.setMaxWidth(Double.MAX_VALUE);
                nuevaReserva.setOnAction(e -> showInCenter("nuevaReserva"));

                // Añade los botones al leftMenu
                leftMenu.getChildren().addAll(listarReservas, nuevaReserva);

                showInCenter("listarReservas");
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
    public static void showInCenterWithData(String fxmlFile, Object data) {
        try {
            staticCenterPane.getChildren().clear();

            // Crea un FXMLLoader apuntando al archivo FXML dentro de /views/centerPane/
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/views/centerPane/" + fxmlFile + ".fxml"));
            Node content = loader.load(); // Carga la vista FXML y devuelve el nodo raíz

            // Obtiene el controlador asociado a ese FXML
            Object controller = loader.getController();

            try {
                // Intentamos obtener el metodo "recogerObjeto" que acepte exactamente el tipo de 'data' que estamos pasando
                Method method = controller.getClass().getMethod("recogerObjeto", data.getClass());
                method.invoke(controller, data);
            } catch (NoSuchMethodException e) {
                System.out.println("El controlador no tiene un método recogerObjeto que reciba: " + data.getClass().getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Alinea el contenido en el centro y lo agrega al StackPane
            StackPane.setAlignment(content, Pos.CENTER);
            staticCenterPane.getChildren().add(content);

        } catch (Exception e) {
            e.printStackTrace();
            staticCenterPane.getChildren().add(new Label("Error cargando vista: " + fxmlFile));
        }
    }

}
