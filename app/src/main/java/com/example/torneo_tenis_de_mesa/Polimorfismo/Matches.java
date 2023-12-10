package com.example.torneo_tenis_de_mesa.Polimorfismo;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Matches implements Serializable{

    public String id;
    public Persona jugador1;
    public Persona jugador2;
    public String fecha;
    public String lugar;

    public String tipo;

    public String tipo_objeto;

    public Matches() {
    }

    public Matches(Persona jugador1, Persona jugador2, String fecha, String lugar, String id, String tipo) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.fecha = fecha;
        this.lugar = lugar;
        this.id = id;
        this.tipo = tipo;
        this.tipo_objeto = "match";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo_objeto() {
        return tipo_objeto;
    }

    public void setTipo_objeto(String tipo_objeto) {
        this.tipo_objeto = tipo_objeto;
    }
}
