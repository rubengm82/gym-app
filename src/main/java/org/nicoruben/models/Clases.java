package org.nicoruben.models;

import java.time.LocalTime;

public class Clases {
    private int idClase;
    private String nombre;
    private LocalTime duracion;
    private String dia;
    private int aforo;
    private int fkIdInst;
    private String descripcion;
    private int estado;

    // CONSTRUCTORES
    public Clases(int idClase, String nombre, LocalTime duracion, String dia, int aforo, int fkIdInst, String descripcion) {
        this.idClase = idClase;
        this.nombre = nombre;
        this.duracion = duracion;
        this.dia = dia;
        this.aforo = aforo;
        this.fkIdInst = fkIdInst;
        this.descripcion = descripcion;
        this.estado = 1;
    }

    // SETTERS & GETTERS
    public int getIdClase() { return idClase; }
    public void setIdClase(int idClase) { this.idClase = idClase; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalTime getDuracion() { return duracion; }
    public void setDuracion(LocalTime duracion) { this.duracion = duracion; }
    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }
    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }
    public int getFkIdInst() { return fkIdInst; }
    public void setFkIdInst(int fkIdInst) { this.fkIdInst = fkIdInst; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }



    // ///////////////////
    // METODOS PROPIOS ///
    // ///////////////////


}
