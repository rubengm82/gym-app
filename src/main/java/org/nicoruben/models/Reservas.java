package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reservas {

    private int idReserva;
    //private Planificaciones planificacion; // fk_id_planificacion
    private Clientes cliente;             // fk_id_cliente
    private LocalDate fechaReserva;
    private int estado;

    // Constructor completo
    public Reservas(int idReserva, Clientes cliente, LocalDate fechaReserva, int estado) {
        this.idReserva = idReserva;
        //this.planificacion = planificacion;
        this.cliente = cliente;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    /*public Planificaciones getPlanificacion() {
        return planificacion;
    }*/

    /*public void setPlanificacion(Planificacion planificacion) {
        this.planificacion = planificacion;
    }*/

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    // Métodos auxiliares para mostrar nombre de clase o cliente en la TableView
    public String getNombreCliente() {
        return cliente != null
                ? cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2()
                : "";
    }

    /*public String getNombreClase() {
        return planificacion != null && planificacion.getClase() != null ? planificacion.getClase().getNombre() : "";
    }*/

    /*public LocalTime getHoraInicio() {
        return planificacion != null ? planificacion.getHoraInicio() : null;
    }*/



    /* ***********************************************************/
    /* ESTO HAY QUE HACERLO BIEN CON EL MODELO DE PLANFICACIONES */
    /* ***********************************************************/
    public LocalTime getHoraInicio() {
        return LocalTime.of(19, 0); // LocalTime fijo, temporal hasta que Planificaciones esté listo
    }
    public String getNombreClase() {
        return "Sin clase - Hacerlo bien con Model Planificaciones"; // o null, o cualquier valor temporal
    }
    /* ***********************************************************/
    /* ***********************************************************/
    /* ***********************************************************/



    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////
    public static List<Reservas> obtenerTodasReservas() {
        List<Reservas> reservas = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.fecha_reserva, r.estado, " +
                "c.id_cliente, c.nombre, c.apellido1, c.apellido2, c.IBAN, c.mail, c.telefono, c.estado AS estado_cliente " +
                "FROM Reservas r " +
                "JOIN Clientes c ON r.fk_id_cliente = c.id_cliente";

        try (Connection con = ConexionBD.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Clientes cliente = new Clientes(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("IBAN"),
                        rs.getString("mail"),
                        rs.getString("telefono"),
                        rs.getInt("estado_cliente")
                );

                Reservas reserva = new Reservas(
                        rs.getInt("id_reserva"),
                        cliente,
                        rs.getDate("fecha_reserva").toLocalDate(),
                        rs.getInt("estado")
                );

                reservas.add(reserva);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
        }
        return reservas;
    }

    // BORRAR PERMANENTEMENTE de la BBDD una Reserva
    public static boolean borrarReserva(int idReserva) {
        String sql = "DELETE FROM Reservas WHERE id_reserva = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            return ps.executeUpdate() > 0; // true si se borró
        } catch (SQLException e) {
            System.err.println("Error al borrar reserva: " + e.getMessage());
            return false;
        }
    }

}