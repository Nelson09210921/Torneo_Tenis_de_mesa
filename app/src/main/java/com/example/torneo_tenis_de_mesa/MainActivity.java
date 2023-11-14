package com.example.torneo_tenis_de_mesa;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/*
@Author Nelson Barboza && Valentina Galenao
@Version Pre-alpha 1.0.0

Plataforma integral diseñada para gestionar eficientemente torneos de tenis de mesa que deseen ser realizados en la Universidad. 
Su objetivo principal es brindar a los usuarios una experiencia organizada y fluida al planificar y dar seguimiento al torneo. 

La interfaz de usuario será intuitiva y fácil de navegar, asegurando que todas las funciones estén disponibles de manera clara y accesible.

Existen dos tipos de usuario que pueden registrarse en la aplicación, el administrador, 
que es la persona quién lidera y organiza el evento y los participantes (jugadores) que pueden ingresar a visualizar o inscribirse al torneo de tenis de mesa.

*/

public class MainActivity extends AppCompatActivity {

    //Creación de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //A ingreso_Activity al presionar el boton
    public void ToIngreso(View view){
        Intent toingreso = new Intent(this, Ingreso.class);
        startActivity(toingreso);
    }

    //A Registro_Activity al presionar el boton
    public void ToRegistro(View view){
        Intent toregistro = new Intent(this, Registro.class);
        startActivity(toregistro);
    }
}
