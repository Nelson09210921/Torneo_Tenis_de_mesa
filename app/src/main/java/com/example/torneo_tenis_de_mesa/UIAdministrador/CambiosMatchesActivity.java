package com.example.torneo_tenis_de_mesa.UIAdministrador;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torneo_tenis_de_mesa.Administrador;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Matches;
import com.example.torneo_tenis_de_mesa.R;

public class CambiosMatchesActivity extends AppCompatActivity {

    EditText lugar, fecha;
    TextView jugador1, jugador2;

    SwitchCompat switchCompat;

    Button boton_guardar, btn_obtener;

    boolean auxiliar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiosmarchesactivity);

        Matches match = (Matches) getIntent().getSerializableExtra("Matches");
        lugar = findViewById(R.id.editTextLugar);
        fecha = findViewById(R.id.editTextfecha);
        jugador1 = findViewById(R.id.textviewJugador1);
        jugador2 = findViewById(R.id.textviewJugador2);
        boton_guardar = findViewById(R.id.button_guardar);
        switchCompat = findViewById(R.id.switch1);



        /*EDITARRRRRRRRRRRRRRRRR*/
        btn_obtener = findViewById(R.id.bottonobtener);
        btn_obtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "obteniendo datos", Toast.LENGTH_LONG).show();
                lugar.setText(match.getLugar());
                fecha.setText(match.getFecha());
                jugador1.setText(match.getJugador1().getNombre());
                jugador2.setText(match.getJugador2().getNombre());
            }
        });
        /*Hasta aquiiii*/


        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    auxiliar = true;
                }
            }
        });

        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.setLugar(lugar.getText().toString());
                match.setFecha(fecha.getText().toString());

                if(auxiliar){
                    match.getJugador1().setEstado_torneo("p");
                    match.getJugador2().setEstado_torneo("g");
                }else {
                    match.getJugador1().setEstado_torneo("g");
                    match.getJugador2().setEstado_torneo("p");
                }
                Intent toadministrador = new Intent(getApplicationContext(), Administrador.class);
                Toast.makeText(getApplicationContext(), "Se ha guardado correctamente", Toast.LENGTH_LONG).show();
                startActivity(toadministrador);

            }
        });



    }



}