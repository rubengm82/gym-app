package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nicoruben.models.*;
import org.nicoruben.services.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NuevaReserva {

    @FXML private ComboBox<Clientes> comboClientes;
    @FXML private ComboBox<String> comboDia;
    @FXML private ComboBox<Planificaciones> comboPlanificaciones;
    @FXML private Button buttonGuardar;

    private List<Planificaciones> todasPlanificaciones;

    @FXML
    public void initialize() {
        // Inicialmente desactivar comboPlanificaciones
        comboPlanificaciones.setDisable(true);

        // Cargar clientes
        List<Clientes> clientes = Clientes.obtenerTodosClientes();

        clientes.sort((c1, c2) ->
                c1.getApellido1().compareToIgnoreCase(c2.getApellido1()) != 0
                        ? c1.getApellido1().compareToIgnoreCase(c2.getApellido1())
                        : c1.getApellido2().compareToIgnoreCase(c2.getApellido2())
        );

        comboClientes.setItems(FXCollections.observableArrayList(clientes));

        // Mostrar nombre + apellidos + email
        comboClientes.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Clientes item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(String.format("%s %s %s (%s)",
                            item.getNombre(),
                            item.getApellido1(),
                            item.getApellido2(),
                            item.getMail() != null ? item.getMail() : ""));
                }
            }
        });
        comboClientes.setButtonCell(comboClientes.getCellFactory().call(null));

        // Días
        comboDia.setItems(FXCollections.observableArrayList("Lunes","Martes","Miércoles","Jueves","Viernes"));

        // Cargar todas las planificaciones activas
        todasPlanificaciones = Planificaciones.obtenerTodasPlanificacionesActivas();

        // Filtrar planificaciones al seleccionar un día
        comboDia.setOnAction(e -> filtrarPlanificacionesPorDia());

        // Mostrar info de la planificación
        comboPlanificaciones.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Planificaciones p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setText("");
                } else {
                    String horaInicio = p.getHora_inicio().substring(0,5);
                    String horaFin = p.getHora_fin().substring(0,5);
                    setText(String.format("%s - %s a %s en Clase de %s",
                            p.getDia(),
                            horaInicio,
                            horaFin,
                            p.getClase() != null ? p.getClase().getNombre() : "Sin clase"));
                }
            }
        });
        comboPlanificaciones.setButtonCell(comboPlanificaciones.getCellFactory().call(null));
    }

    private void filtrarPlanificacionesPorDia() {
        String diaSeleccionado = comboDia.getValue();
        if (diaSeleccionado != null) {
            List<Planificaciones> filtradas = todasPlanificaciones.stream()
                    .filter(p -> p.getDia().equalsIgnoreCase(diaSeleccionado))
                    .toList();
            comboPlanificaciones.setItems(FXCollections.observableArrayList(filtradas));
            comboPlanificaciones.setDisable(filtradas.isEmpty());
        } else {
            comboPlanificaciones.getItems().clear();
            comboPlanificaciones.setDisable(true);
        }
    }

    @FXML
    void onClickGuardar(ActionEvent event) {
        Clientes cliente = comboClientes.getValue();
        Planificaciones planificacion = comboPlanificaciones.getValue();

        String mensajeError = null;
        boolean exito = false;

        // 1. Validar selección
        if (cliente == null || planificacion == null) {
            mensajeError = "Debes seleccionar cliente y planificación.";
        }

        // 2. Validar si ya tiene reserva
        if (mensajeError == null) {
            boolean yaReservado = Reservas.existeReservaClienteEnPlanificacion(
                    cliente.getId(),
                    planificacion.getId()
            );
            if (yaReservado) {
                mensajeError = "Este cliente ya tiene una reserva en esta planificación.";
            }
        }

        // 3. Validar aforo
        if (mensajeError == null) {
            int idPlan = planificacion.getId();
            int reservasActuales = Reservas.contarReservasPorPlanificacion(idPlan);
            int aforoMaximo = Clases.obtenerAforoPorId(planificacion.getClase().getId());

            if (reservasActuales >= aforoMaximo) {
                mensajeError = "No se pudo crear la reserva: aforo completo.";
            }
        }

        // 4. Si hubo algún error que lo muestre
        if (mensajeError != null) {
            new Alert(Alert.AlertType.WARNING, mensajeError, ButtonType.OK).showAndWait();
        } else {
            // Crear reserva
            exito = Reservas.insertarReserva(cliente, planificacion);

            if (exito) {
                new Alert(Alert.AlertType.INFORMATION, "Reserva creada correctamente", ButtonType.OK).showAndWait();
                comboClientes.getSelectionModel().clearSelection();
                comboDia.getSelectionModel().clearSelection();
                comboPlanificaciones.getItems().clear();
                comboPlanificaciones.setDisable(true);
            } else {
                new Alert(Alert.AlertType.ERROR, "No se pudo crear la reserva por error interno", ButtonType.OK).showAndWait();
            }
        }
    }

}
