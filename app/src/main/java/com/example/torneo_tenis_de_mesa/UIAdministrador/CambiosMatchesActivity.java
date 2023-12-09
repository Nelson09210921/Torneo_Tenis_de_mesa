package com.example.torneo_tenis_de_mesa.UIAdministrador;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torneo_tenis_de_mesa.Administrador;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Matches;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Torneo;
import com.example.torneo_tenis_de_mesa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.MoreObjects;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CambiosMatchesActivity extends AppCompatActivity {

    EditText lugar, fecha;
    TextView jugador1, jugador2;

    SwitchCompat switchCompat;

    Button boton_guardar;

    boolean auxiliar = false;

    CheckBox finalizado_state;

    FirebaseFirestore firebaseDatabase;

    DatabaseReference reference;



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
        finalizado_state = findViewById(R.id.finalizadoo);


        lugar.setText(match.getLugar());
        fecha.setText(match.getFecha());
        jugador1.setText(match.getJugador1().getNombre());
        jugador2.setText(match.getJugador2().getNombre());



        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    auxiliar = true;
                }
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference().child("Torneo");
        firebaseDatabase = FirebaseFirestore.getInstance();


        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Consultas en la db
                Query query = reference.child("Torneo1").orderByChild("id").equalTo(match.getId().toString());

                Query consulta_semi_una_vacia = reference.child("Torneo1").orderByChild("tipo").equalTo("semifinal0");
                Query consulta_semi_dos_vacia = reference.child("Torneo1").orderByChild("tipo").equalTo("semifinal1");

                Query consulta_final_vacia = reference.child("Torneo1").orderByChild("tipo").equalTo("final");
                final String[] match_actual = new String[1];
                //Importante los estados son la forma de ubicar al jugador dentro del torneo y van de la siguiente manera
                // "e" (Estado espera): Estado inicial del jugador, quiere decir que ha jugado el encuentro.
                // "g" (Estado ganador)
                // "p" (Estado perdedor)
                //Primero cambia el estado del usuario en el torneo
                //Se verifica el avance en el que va el torneo y si hay espacios ocupados, si no lo esta se asigna y se cambia el estado del usuario de nuevo
                //Si Jugador 1 esta ocupado pasa al jugador 2 y lo llena

                query.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //Toast.makeText(getApplicationContext(), snapshot.getKey() + "gedfgedg", Toast.LENGTH_SHORT).show();
                        reference.child("Torneo1").child(snapshot.getKey()).child("lugar").setValue(lugar.getText().toString());
                        reference.child("Torneo1").child(snapshot.getKey()).child("fecha").setValue(fecha.getText().toString());
                        match_actual[0] = snapshot.getKey().toString();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                /*
                //Mas consultas
                Query consulta_jugador_uno = reference.child("Torneo1").child(match_actual[0]).child("jugador1")
                        .orderByChild("id").equalTo(match.getJugador1().getId());
                Query consulta_jugador_dos = reference.child("Torneo1").child(match_actual[0]).child("jugador2")
                        .orderByChild("id").equalTo(match.getJugador2().getId());*/


                if(finalizado_state.isChecked()){
                    final String[] avance_torneo = {"00"};
                    consulta_semi_una_vacia.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                
                                Matches match_evaluado = snapshot.getValue(Matches.class);
                                Toast.makeText(getApplicationContext(), match_evaluado.getId().toString(), Toast.LENGTH_LONG).show();

                                if(!match_evaluado.getJugador1().getNombre().equals("jugador") && avance_torneo[0] == "00" ){
                                    avance_torneo[0] = "10";
                                }
                                if(!match_evaluado.getJugador2().getNombre().equals("jugador") && avance_torneo[0] == "10" ){
                                    avance_torneo[0] = "11";
                                }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    consulta_semi_dos_vacia.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Matches match_evaluado = snapshot.getValue(Matches.class);
                            if(!match_evaluado.getJugador1().getNombre().equals("jugador") && avance_torneo[0] == "11" ){

                                avance_torneo[0] = "20";
                            }
                            if(!match_evaluado.getJugador2().getNombre().equals("jugador") && avance_torneo[0] == "20" ){
                                avance_torneo[0] = "22";
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    consulta_final_vacia.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            Matches match_evaluadoo = snapshot.getValue(Matches.class);

                            if(!match_evaluadoo.getJugador1().getNombre().equals("jugador") && avance_torneo[0] == "22"){
                                avance_torneo[0] = "30";
                            }
                            if (!match_evaluadoo.getJugador2().getNombre().equals("jugador") && avance_torneo[0] == "30"){
                                avance_torneo[0] = "33";
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    //Si algun jugador es ganador
                    query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                Matches match_jugador_ganador = snapshot.getValue(Matches.class);

                                if(auxiliar == false){
                                    //Jugador 1 GANADOR
                                    reference.child("Torneo1").child(snapshot.getKey()).child("jugador1").child("estado_torneo").setValue("g");
                                    reference.child("Torneo1").child(snapshot.getKey()).child("jugador2").child("estado_torneo").setValue("p");
                                    Toast.makeText(getApplicationContext(), avance_torneo[0], Toast.LENGTH_LONG).show();

                                    switch (avance_torneo[0]){
                                        case "00":
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador1").setValue(match_jugador_ganador.getJugador1());
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador1").child("estado_torneo").setValue("e");
                                            break;
                                        case "10":
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador2").setValue(match_jugador_ganador.getJugador1());
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador2").child("estado_torneo").setValue("e");
                                            break;
                                        case "11":
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador1").setValue(match_jugador_ganador.getJugador1());
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador1").child("estado_torneo").setValue("e");
                                            break;
                                        case "20":
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador2").setValue(match_jugador_ganador.getJugador1());
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador2").child("estado_torneo").setValue("e");
                                            break;
                                        case "22":
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador1").setValue(match_jugador_ganador.getJugador1());
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador1").child("estado_torneo").setValue("e");
                                            break;
                                        case "30":
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador2").setValue(match_jugador_ganador.getJugador1());
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador2").child("estado_torneo").setValue("e");
                                            break;
                                    }

                                }else {
                                    reference.child("Torneo1").child(snapshot.getKey()).child("jugador1").child("estado_torneo").setValue("p");
                                    reference.child("Torneo1").child(snapshot.getKey()).child("jugador2").child("estado_torneo").setValue("g");
                                    Toast.makeText(getApplicationContext(), avance_torneo[0], Toast.LENGTH_LONG).show();

                                    switch (avance_torneo[0]){
                                        case "00":
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador1").setValue(match_jugador_ganador.getJugador2());
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador1").child("estado_torneo").setValue("e");
                                            break;
                                        case "10":
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador2").setValue(match_jugador_ganador.getJugador2());
                                            reference.child("Torneo1").child("encuentro_semi_uno").child("jugador2").child("estado_torneo").setValue("e");
                                            break;
                                        case "11":
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador1").setValue(match_jugador_ganador.getJugador2());
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador1").child("estado_torneo").setValue("e");
                                            break;
                                        case "20":
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador2").setValue(match_jugador_ganador.getJugador2());
                                            reference.child("Torneo1").child("encuentro_semi_dos").child("jugador2").child("estado_torneo").setValue("e");
                                            break;
                                        case "22":
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador1").setValue(match_jugador_ganador.getJugador2());
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador1").child("estado_torneo").setValue("e");
                                            break;
                                        case "30":
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador2").setValue(match_jugador_ganador.getJugador2());
                                            reference.child("Torneo1").child("encuentroFinal").child("jugador2").child("estado_torneo").setValue("e");
                                            break;
                                    }
                                }

                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                }

                Intent toadministrador = new Intent(getApplicationContext(), Administrador.class);
                startActivity(toadministrador);



            }
        });



    }



}