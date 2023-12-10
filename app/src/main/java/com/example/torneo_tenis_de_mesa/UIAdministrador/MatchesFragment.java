package com.example.torneo_tenis_de_mesa.UIAdministrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedMap.PersistentOrderedMap;
import androidx.datastore.core.Final;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torneo_tenis_de_mesa.Polimorfismo.Matches;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Torneo;
import com.example.torneo_tenis_de_mesa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;


public class MatchesFragment extends Fragment {



    public Persona[] inscritos = new Persona[8];
     private Matches[] encuentros_cuartos = new Matches[4];

    private Matches[] encuentros_semifinal = new Matches[2];

    private Matches encuentro_final;

    RecyclerView recyclerView_encuentros;

    ArrayList<Matches> lista_matches;


    ArrayList<Matches> lista_finales;

    ArrayList<Matches> listafinal;
    ArrayList<Persona> listapersona;

    MatchesAdapter matchesAdapterc, matchesAdapters, matchesAdapterf;

    RecyclerView reciclerView_semifinal;

    RecyclerView recyclerView_final;

    TextView texto1, texto2, texto3;

    DatabaseReference databaseReference_jugadores, databaseReference_torneo, referenceeval;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_matches, container, false);


        RelativeLayout relativegenerar = (RelativeLayout) vista.findViewById(R.id.relativegenerar);
        recyclerView_encuentros = vista.findViewById(R.id.lista_de_encuentros);
        reciclerView_semifinal = vista.findViewById(R.id.lista_de_finales);
        recyclerView_final = vista.findViewById(R.id.lista_final);
        texto1 = vista.findViewById(R.id.cuartos_text);
        texto2 = vista.findViewById(R.id.semifinales_text);
        texto3 = vista.findViewById(R.id.final_text);

        listapersona = new ArrayList<>();
        Persona estandar = new Persona();
        estandar.setId(UUID.randomUUID().toString());
        estandar.setNombre("jugador");
        estandar.setNombre_usuario("usuario_jugador");
        estandar.setCorreo("correo_jugador");
        estandar.setContrasena("contrasena");
        estandar.setTipo_usuario(2);



        /*
        HASTA AQUI SE VA A MODIFICARRRR

        */
        referenceeval = FirebaseDatabase.getInstance().getReference("Torneo").child("Torneo1");
        referenceeval.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    relativegenerar.removeAllViews();


                    //Se hacen visibles los encabezados
                    texto1.setVisibility(vista.VISIBLE);
                    texto2.setVisibility(vista.VISIBLE);
                    texto3.setVisibility(vista.VISIBLE);



                    lista_matches = new ArrayList<>();
                    lista_finales = new ArrayList<>();
                    listafinal = new ArrayList<>();
                    cargarlista();
                    mostrarVista();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        relativegenerar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                //Se llenan los encuentros
                databaseReference_jugadores = FirebaseDatabase.getInstance().getReference("Persona");
                Query consil = databaseReference_jugadores.orderByChild("tipo_usuario").equalTo(2);

                consil.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        obtenerdatos(snapshot);
                        int j=0;
                        for(int h=0; h< encuentros_cuartos.length; h++){
                            encuentros_cuartos[h] = new Matches(listapersona.get(j), listapersona.get(j+1), "jueves", "lugar x", UUID.randomUUID().toString(), "cuartos");
                            j+=2;
                        }

                        for(int i=0; i< encuentros_semifinal.length; i++){
                            encuentros_semifinal[i] = new Matches(estandar, estandar, "viernes", "lugary", UUID.randomUUID().toString(), "semifinal"+i);
                        }

                        encuentro_final = new Matches(estandar,estandar, "Sabado", "Lugares", UUID.randomUUID().toString(), "final");

                        databaseReference_torneo = FirebaseDatabase.getInstance().getReference("Torneo").child("Torneo1");

                        Torneo torneo = new Torneo(encuentros_cuartos[0], encuentros_cuartos[1], encuentros_cuartos[2], encuentros_cuartos[3],
                                encuentros_semifinal[0], encuentros_semifinal[1], encuentro_final);

                        databaseReference_torneo.setValue(torneo);
                        lista_matches = new ArrayList<>();
                        lista_finales = new ArrayList<>();
                        listafinal = new ArrayList<>();
                        cargarlista();
                        mostrarVista();
                        //Toast.makeText(getContext(), listapersona.get(0).getNombre().toString(), Toast.LENGTH_LONG).show();
                        relativegenerar.removeAllViews();



                        //Se hacen visibles los encabezados
                        texto1.setVisibility(vista.VISIBLE);
                        texto2.setVisibility(vista.VISIBLE);
                        texto3.setVisibility(vista.VISIBLE);





                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });


        // Inflate the layout for this fragment
        return vista;
    }

    private void obtenerdatos(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot ds : snapshot.getChildren()){
            Persona person = ds.getValue(Persona.class);
            if(person.getTipo_usuario() == 2){
                listapersona.add(person);
            }
        }
    }

    private void cargarlista() {
        databaseReference_torneo = FirebaseDatabase.getInstance().getReference("Torneo/Torneo1");
        Query consulta = databaseReference_torneo.orderByChild("tipo_objeto").equalTo("match");

        consulta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    Matches eval = ds.getValue(Matches.class);
                    if(eval.getTipo().toString().equals("cuartos")){
                        lista_matches.add(eval);
                    }
                    if(eval.getTipo().toString().equals("semifinal0") || eval.getTipo().toString().equals("semifinal1")){
                        lista_finales.add(eval);
                    }
                    if(eval.getTipo().toString().equals("final")){
                        listafinal.add(eval);
                    }

                }matchesAdapterc.notifyDataSetChanged();
                matchesAdapters.notifyDataSetChanged();
                matchesAdapterf.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //
    }

    private void mostrarVista() {

        //Se muestran los encuentros
        recyclerView_encuentros.setHasFixedSize(true);
        recyclerView_encuentros.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapterc = new MatchesAdapter(lista_matches, getContext(), new MatchesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(Matches match) {
                moveToChange(match);
            }
        });
        recyclerView_encuentros.setAdapter(matchesAdapterc);

        //Se muestran las finales
        reciclerView_semifinal.setHasFixedSize(true);
        reciclerView_semifinal.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapters = new MatchesAdapter(lista_finales, getContext(), new MatchesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(Matches match) {
                moveToChange(match);
            }
        });
        reciclerView_semifinal.setAdapter(matchesAdapters);

        //se muestra la final

        recyclerView_final.setHasFixedSize(true);
        recyclerView_final.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapterf = new MatchesAdapter(listafinal, getContext(), new MatchesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(Matches match) {
                moveToChange(match);
            }
        });
        recyclerView_final.setAdapter(matchesAdapterf);


    }

    private void moveToChange(Matches matches) {

        Intent intent = new Intent(getContext(), CambiosMatchesActivity.class);

        intent.putExtra("Matches", matches );
        startActivity(intent);
    }


}