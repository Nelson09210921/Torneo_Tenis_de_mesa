package com.example.torneo_tenis_de_mesa.Polimorfismo;

public class Persona {
    public String  id;
    public String nombre;
    public String nombre_usuario;
    public  String correo;
    public String contrasena;
    public int tipo_usuario;


    public Persona() {
        this.id = "";
        this.nombre = "";
        this.nombre_usuario = "";
        this.correo = "";
        this.contrasena = "";
        this.tipo_usuario = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
