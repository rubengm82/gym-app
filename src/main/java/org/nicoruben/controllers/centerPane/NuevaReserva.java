package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.nicoruben.models.*;

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

        clientes.sort((c1, c2) -> {
            int cmp = c1.getApellido1().compareToIgnoreCase(c2.getApellido1());
            if (cmp != 0) return cmp;
            return c1.getApellido2().compareToIgnoreCase(c2.getApellido2());
        });

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

        if (cliente == null || planificacion == null) {
            new Alert(Alert.AlertType.NONE, "  Debes seleccionar cliente y planificación", ButtonType.OK).showAndWait();
            return;
        }

        boolean exito = Reservas.insertarReserva(cliente, planificacion);

        if (exito) {
            new Alert(Alert.AlertType.NONE, "  Reserva creada correctamente", ButtonType.OK).showAndWait();
            comboClientes.getSelectionModel().clearSelection();
            comboDia.getSelectionModel().clearSelection();
            comboPlanificaciones.getItems().clear();
            comboPlanificaciones.setDisable(true);
        } else {
            new Alert(Alert.AlertType.NONE, "  No se pudo crear la reserva", ButtonType.OK).showAndWait();
        }
    }
}
