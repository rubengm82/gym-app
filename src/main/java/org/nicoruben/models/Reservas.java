package org.nicoruben.models;

import org.nicoruben.services.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reservas {

    private int id;
    private Planificaciones planificacion;
    private Clientes cliente;
    private LocalDate fechaReserva;
    private int estado;

    // ESTADOS DE LAS RESERVAS EN EL CAMPO ESTADO DE LA BBDD
    // -1 CADUCADOS
    // 0 RESERVA ANULADA
    // 1 ACTIVOS DEL DIA
    // 2 RESERVA VERIFICADA

    // CONSTRUCTORES
    public Reservas(int id, Planificaciones planificacion, Clientes cliente, LocalDate fechaReserva, int estado) {
        this.id = id;
        this.planificacion = planificacion;
        this.cliente = cliente;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }

    public Reservas() {}


    // SETTER & GETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Planificaciones getPlanificacion() { return planificacion; }
    public void setPlanificacion(Planificaciones planificacion) { this.planificacion = planificacion; }

    public Clientes getCliente() { return cliente; }
    public void setCliente(Clientes cliente) { this.cliente = cliente; }

    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    // Métodos auxiliares
    public String getNombreCliente() {
        return cliente != null ? cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2() : "";
    }

    public String getNombreClase() {
        return planificacion != null && planificacion.getClase() != null
                ? planificacion.getClase().getNombre()
                : "Sin clase asignada";
    }

    public LocalTime getHoraInicio() {
        return (planificacion != null && planificacion.getHora_inicio() != null)
                ? LocalTime.parse(planificacion.getHora_inicio())
                : null;
    }

    public LocalTime getHoraFin() {
        return (planificacion != null && planificacion.getHora_fin() != null)
                ? LocalTime.parse(planificacion.getHora_fin())
                : null;
    }

    public String getDia() {
        return (planificacion != null) ? planificacion.getDia() : "";
    }

    // Obtener todas las reservas
    public static List<Reservas> obtenerTodasReservas() {
        List<Reservas> reservas = new ArrayList<>();
        String sql = "SELECT r.id AS reserva_id, r.fecha_reserva, r.estado, " +
                "c.id AS cliente_id, c.nombre, c.apellido1, c.apellido2, c.IBAN, c.mail, c.telefono, c.estado AS estado_cliente, " +
                "p.id AS plan_id, p.dia, p.hora_inicio, p.hora_fin, p.estado AS estado_plan, " +
                "cl.id AS clase_id, cl.nombre AS nombre_clase, " +
                "i.id AS inst_id, i.nombre AS nombre_instructor " +
                "FROM Reservas r " +
                "JOIN Clientes c ON r.fk_id_cliente = c.id " +
                "JOIN Planificaciones p ON r.fk_id_planificacion = p.id " +
                "JOIN Clases cl ON p.fk_id_clase = cl.id " +
                "JOIN Instructores i ON p.fk_id_inst = i.id";

        try (Connection con = ConexionBD.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Cliente
                Clientes cliente = new Clientes(
                        rs.getInt("cliente_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("IBAN"),
                        rs.getString("mail"),
                        rs.getString("telefono"),
                        rs.getInt("estado_cliente")
                );

                // Clase
                Clases clase = new Clases();
                clase.setId(rs.getInt("clase_id"));
                clase.setNombre(rs.getString("nombre_clase"));

                // Instructor
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt("inst_id"));
                instructor.setNombre(rs.getString("nombre_instructor"));

                // Planificación
                Planificaciones planificacion = new Planificaciones(
                        rs.getInt("plan_id"),
                        rs.getString("dia"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fin"),
                        clase,
                        instructor,
                        rs.getInt("estado_plan")
                );

                // Reserva
                Reservas reserva = new Reservas(
                        rs.getInt("reserva_id"),
                        planificacion,
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

    // Cancelar reserva
    public static boolean cancelarReserva(int id) {
        boolean exito = false;
        String sql = "UPDATE Reservas SET estado = 0 WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
              PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            exito = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al anular reserva: " + e.getMessage());
        }

        return exito;
    }

    // Insertar reserva en la base de datos
    public static boolean insertarReserva(Clientes cliente, Planificaciones planificacion) {
        boolean exito = false;
        String sql = "INSERT INTO Reservas (fk_id_cliente, fk_id_planificacion, fecha_reserva, estado) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
              PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, cliente.getId());
            ps.setInt(2, planificacion.getId());
            ps.setDate(3, Date.valueOf(LocalDate.now())); // fecha_reserva = hoy
            ps.setInt(4, 1); // estado por defecto = 1

            int filas = ps.executeUpdate();
            if (filas > 0) {
                exito = true;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar reserva: " + e.getMessage());
        }
        return exito;
    }

    public static int contarReservasPorPlanificacionParaGrafica(int idPlanificacion, int estado) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Reservas WHERE fk_id_planificacion = ? AND estado = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPlanificacion);
            ps.setInt(2, estado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar reservas: " + e.getMessage());
        }

        return total;
    }

    // funcion para graficos
    public static int contarReservasPorPlanificacion(int idPlanificacion) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Reservas WHERE fk_id_planificacion = ? AND estado = 1";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPlanificacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar reservas: " + e.getMessage());
        }

        return total;
    }

    public static boolean existeReservaClienteEnPlanificacion(int idCliente, int idPlanificacion) {
        boolean existe = false;
        String sql = "SELECT 1 FROM Reservas WHERE fk_id_cliente = ? AND fk_id_planificacion = ? AND estado = 1";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idPlanificacion);
            ResultSet rs = ps.executeQuery();
            existe = rs.next(); // true si ya existe
        } catch (SQLException e) {
            System.err.println("Error al verificar reserva existente: " + e.getMessage());
        }

        return existe;
    }

}
