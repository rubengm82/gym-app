package org.nicoruben.controllers.centerPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nicoruben.controllers.MainController;
import org.nicoruben.models.Clientes;

import java.util.ArrayList;
import java.util.List;

public class ListarClientes {

    @FXML
    private Button buttonBajaAlta;

    @FXML
    private Button buttonEditar;

    @FXML
    private ToggleButton buttonToggleVerBajas;

    @FXML
    private Label labelTituloClientes;

    @FXML
    private TableView<Clientes> tablaClientes;

    @FXML
    private TableColumn<Clientes, Integer> campoID;

    @FXML
    private TableColumn<Clientes, String> campoNombre;
    @FXML
    private TableColumn<Clientes, String> campoApellido1;
    @FXML
    private TableColumn<Clientes, String> campoApellido2;
    @FXML
    private TableColumn<Clientes, String> campoIBAN;
    @FXML
    private TableColumn<Clientes, String> campoMail;
    @FXML
    private TableColumn<Clientes, String> campoTelefono;


    /* Atributos de la class */
    private List<Clientes> todosClientes;


    /* AUTO-LOAD al cargar la vista */
    public void initialize() {
        campoID.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));
        campoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        campoApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        campoApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        campoIBAN.setCellValueFactory(new PropertyValueFactory<>("IBAN"));
        campoMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        campoTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        // Cargar datos en la tabla de clintes
        todosClientes = Clientes.obtenerTodosClientes();

        // Mostrar solo activos con (estado = 1)  (1 - ACTIVO || 0 - BAJA)
        mostrarClientesPorEstado(1);
    }


    /* METODOS */
    @FXML
    void onClickToggleBajas(ActionEvent event) {
        if (buttonToggleVerBajas.isSelected()) {
            mostrarClientesPorEstado(0); // mostrar bajas
            buttonBajaAlta.setText("ALTA");
            labelTituloClientes.setText("Listado Clientes de Baja");
            buttonToggleVerBajas.setText("VER CLIENTES DE ALTA");
        } else {
            mostrarClientesPorEstado(1); // mostrar activos
            buttonBajaAlta.setText("BAJA");
            labelTituloClientes.setText("Listado Clientes Activos");
            buttonToggleVerBajas.setText("VER CLIENTES DE BAJA");
        }
    }

    @FXML
    void onClickBajaAlta(ActionEvent event) {
        // Obtener el cliente seleccionado en la tabla
        Clientes clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado != null) {
            // Crear un Alert de confirmación
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText(null);
            String accion = (clienteSeleccionado.getEstado() == 1) ? "dar de baja" : "dar de alta";
            confirmacion.setContentText("¿Seguro que deseas " + accion + " al cliente seleccionado?");

            // Personalizar botones: Aceptar y Cancelar
            ButtonType btnAceptar = new ButtonType("Aceptar");
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmacion.getButtonTypes().setAll(btnAceptar, btnCancelar);

            // Esperar respuesta
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == btnAceptar) {
                    // Cambiar el estado del cliente
                    int nuevoEstado = (clienteSeleccionado.getEstado() == 1) ? 0 : 1;
                    clienteSeleccionado.setEstado(nuevoEstado);

                    // Actualizar en la base de datos
                    Clientes.actualizarEstado(clienteSeleccionado.getId_cliente(), nuevoEstado);

                    // Refrescar la tabla según el toggle
                    if (buttonToggleVerBajas.isSelected()) {
                        mostrarClientesPorEstado(0);
                    } else {
                        mostrarClientesPorEstado(1);
                    }
                }
            });
        } else {
            // Si no hay cliente seleccionado, mostrar alerta de aviso
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, seleccione un cliente primero");
            alerta.showAndWait();
        }
    }

    private void mostrarClientesPorEstado(int estado) {
        List<Clientes> filtrados_bajas = new ArrayList<>();
        for (Clientes clientes : todosClientes) {
            if (clientes.getEstado() == estado) {
                filtrados_bajas.add(clientes);
            }
        }
        tablaClientes.setItems(FXCollections.observableArrayList(filtrados_bajas));
    }

    @FXML
    void onClickEditar(ActionEvent event) {
        Clientes clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado != null) {
            // Llamamos al MainController para cargar la vista y pasar el cliente
            MainController.showInCenter("editarCliente", clienteSeleccionado);
        } else {
            // Alert en caso de no tener seleccionado algo de la lista
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un cliente antes de editar.");
            alert.showAndWait();
        }
    }

}
