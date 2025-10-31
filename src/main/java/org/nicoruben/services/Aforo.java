package org.nicoruben.services;

import javafx.scene.control.ProgressBar;

public class Aforo {
    String nombre;
    int inscritos;
    int aforoMax;
    ProgressBar barra;

    // CONSTRUCTORES
    public Aforo(String nombre, int inscritos, int aforoMax, ProgressBar barra) {
        this.nombre = nombre;
        this.inscritos = inscritos;
        this.aforoMax = aforoMax;
        this.barra = barra;
    }


    // SETTERS AND GETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public int getAforoMax() {
        return aforoMax;
    }

    public void setAforoMax(int aforoMax) {
        this.aforoMax = aforoMax;
    }

    public ProgressBar getBarra() {
        return barra;
    }

    public void setBarra(ProgressBar barra) {
        this.barra = barra;
    }


    // METODOS
    public void sumarInscrito() {
        if (inscritos < aforoMax) {
            inscritos++;
            barra.setProgress((double) inscritos / aforoMax);
        }
    }
}
