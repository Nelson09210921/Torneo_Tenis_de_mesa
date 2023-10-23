package com.example.torneo_tenis_de_mesa;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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