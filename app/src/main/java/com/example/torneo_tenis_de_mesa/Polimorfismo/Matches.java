package com.example.torneo_tenis_de_mesa.Polimorfismo;

import java.io.Serializable;

public class Matches implements Serializable {
    public Persona jugador1;
    public Persona jugador2;
    public String fecha;
    public String lugar;

    public Matches(Persona jugador1, Persona jugador2, String fecha, String lugar) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.fecha = fecha;
        this.lugar = lugar;
    }

    public Persona getJugador1() {
        return jugador1;
    }

    public void setJugador1(Persona jugador1) {
        this.jugador1 = jugador1;
    }

    public Persona getJugador2() {
        return jugador2;
    }

    public void setJugador2(Persona jugador2) {
        this.jugador2 = jugador2;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
