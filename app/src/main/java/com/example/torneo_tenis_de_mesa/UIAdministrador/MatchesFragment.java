package com.example.torneo_tenis_de_mesa.UIAdministrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.torneo_tenis_de_mesa.R;

import java.util.ArrayList;
import java.util.UUID;


public class MatchesFragment extends Fragment {



    private Persona[] inscritos = new Persona[8];
     private Matches[] encuentros_cuartos = new Matches[4];

    private Matches[] encuentros_semifinal = new Matches[2];

    private Matches encuentro_final;

    RecyclerView recyclerView_encuentros;

    ArrayList<Matches> lista_matches;

    ArrayList<Matches> lista_finales;

    ArrayList<Matches> listafinal;

    MatchesAdapter matchesAdapter;

    RecyclerView reciclerView_semifinal;

    RecyclerView recyclerView_final;

    TextView texto1, texto2, texto3;

    Button btnIngresar, btnmostrar, btnregistrar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_matches, container, false);

        btnIngresar = vista.findViewById(R.id.bottoningresar);
        btnmostrar = vista.findViewById(R.id.bottonMostrar);
        btnregistrar = vista.findViewById(R.id.bottonregistrar);

        RelativeLayout relativegenerar = (RelativeLayout) vista.findViewById(R.id.relativegenerar);


        /*@Param jugadores inscritos 
        *******SOLO PARA PRUEBA SE MODIFICARA CON UNA VALIDACION DE LA CANTIDAD DE 
        JUGADORES INSCRITOS DENTRO DE LA BASE DE DATOS YA ESTABLECIDA*************
        */
        Persona jugador = new Persona();
        jugador.setId("x");
        jugador.setNombre("Jugador");
        jugador.setNombre_usuario("nombre_ususariojugador");
        jugador.setCorreo("correojugador");
        jugador.setContrasena("contrasena");
        jugador.setTipo_usuario(2);

        Persona p1 = new Persona();
        p1.setId("1");
        p1.setNombre("Nombre1");
        p1.setNombre_usuario("nombre_ususario1");
        p1.setCorreo("correo");
        p1.setContrasena("contrasena");
        p1.setTipo_usuario(2);

        Persona p2 = new Persona();
        p2.setId("2");
        p2.setNombre("Nombre2");
        p2.setNombre_usuario("nombre_ususario2");
        p2.setCorreo("correo");
        p2.setContrasena("contrasena");
        p2.setTipo_usuario(2);

        Persona p3 = new Persona();
        p3.setId("3");
        p3.setNombre("Nombre3");
        p3.setNombre_usuario("nombre_ususario3");
        p3.setCorreo("correo");
        p3.setContrasena("contrasena");
        p3.setTipo_usuario(2);

        Persona p4 = new Persona();
        p4.setId("4");
        p4.setNombre("Nombre4");
        p4.setNombre_usuario("nombre_ususario4");
        p4.setCorreo("correo");
        p4.setContrasena("contrasena");
        p4.setTipo_usuario(2);

        Persona p5 = new Persona();
        p5.setId("5");
        p5.setNombre("Nombre5");
        p5.setNombre_usuario("nombre_ususario5");
        p5.setCorreo("correo");
        p5.setContrasena("contrasena");
        p5.setTipo_usuario(2);

        Persona p6 = new Persona();
        p6.setId("6");
        p6.setNombre("Nombre6");
        p6.setNombre_usuario("nombre_ususario6");
        p6.setCorreo("correo");
        p6.setContrasena("contrasena");
        p6.setTipo_usuario(2);

        Persona p7 = new Persona();
        p7.setId("7");
        p7.setNombre("Nombre7");
        p7.setNombre_usuario("nombre_ususario7");
        p7.setCorreo("correo");
        p7.setContrasena("contrasena");
        p7.setTipo_usuario(2);

        Persona p8 = new Persona();
        p8.setId("8");
        p8.setNombre("Nombre8");
        p8.setNombre_usuario("nombre_ususario8");
        p8.setCorreo("correo");
        p8.setContrasena("contrasena");
        p8.setTipo_usuario(2);


        //PRUEBAAAAAA
        ///MODIFICARRRRRRRRRRRRRRRRRRRRRRRR
        btnmostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inscritos[0] == null){
                    Toast.makeText(getContext(), "No hay usuarios inscritos", Toast.LENGTH_LONG).show();
                }else {

                    recyclerView_encuentros = vista.findViewById(R.id.lista_de_encuentros);
                    reciclerView_semifinal = vista.findViewById(R.id.lista_de_finales);
                    recyclerView_final = vista.findViewById(R.id.lista_final);
                    texto1 = vista.findViewById(R.id.cuartos_text);
                    texto2 = vista.findViewById(R.id.semifinales_text);
                    texto3 = vista.findViewById(R.id.final_text);
                    texto1.setVisibility(v.VISIBLE);
                    texto2.setVisibility(v.VISIBLE);
                    texto3.setVisibility(v.VISIBLE);



                    lista_matches = new ArrayList<>();
                    lista_finales = new ArrayList<>();
                    listafinal = new ArrayList<>();
                    cargarlista();
                    mostrarVista();
                }
            }
        });
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se llenan los encuentros
                inscritos[0] = p1;
                inscritos[1] = p2;
                inscritos[2] = p3;
                inscritos[3] = p4;
                inscritos[4] = p5;
                inscritos[5] = p6;
                inscritos[6] = p7;
                inscritos[7] = p8;
                Toast.makeText(getContext(), "Se ha completado El registro", Toast.LENGTH_LONG).show();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int j = 0;

                for(int i = 0; i < inscritos.length; i+=2){
                    encuentros_cuartos[j] = new Matches(inscritos[i], inscritos[i+1], "Jueves", "mesa x");
                    //Toast.makeText(getContext(), inscritos[i].getNombre().toString(), Toast.LENGTH_SHORT).show();
                    j++;
                }

                int h = 0;
                for(int i =0; i< encuentros_semifinal.length; i++){
                    encuentros_semifinal[i] = new Matches(jugador, jugador, "Viernes", "mesa x");
                    h++;
                }

                encuentro_final = new Matches(jugador, jugador, "Sabado", "mesa y");
                Toast.makeText(getContext(), "Se ha completado la inscripcion", Toast.LENGTH_LONG).show();
            }
        });





        /*
        HASTA AQUI SE VA A MODIFICARRRR
        HASTA AQUI VIENEN LOS METODOS DE PRUEBA
        */


        relativegenerar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Se llenan los encuentros
                int j = 0;

                for(int i = 0; i < inscritos.length; i+=2){
                    encuentros_cuartos[j] = new Matches(inscritos[i], inscritos[i+1], "Jueves", "mesa x");
                    //Toast.makeText(getContext(), inscritos[i].getNombre().toString(), Toast.LENGTH_SHORT).show();
                    j++;
                }

                int h = 0;
                for(int i =0; i< encuentros_semifinal.length; i++){
                    encuentros_semifinal[i] = new Matches(jugador, jugador, "Viernes", "mesa x");
                    h++;
                }

                encuentro_final = new Matches(jugador, jugador, "Sabado", "mesa y");


                //se va el boton
                relativegenerar.removeAllViews();
                recyclerView_encuentros = vista.findViewById(R.id.lista_de_encuentros);
                reciclerView_semifinal = vista.findViewById(R.id.lista_de_finales);
                recyclerView_final = vista.findViewById(R.id.lista_final);
                texto1 = vista.findViewById(R.id.cuartos_text);
                texto2 = vista.findViewById(R.id.semifinales_text);
                texto3 = vista.findViewById(R.id.final_text);


                //Se hacen visibles los encabezados
                texto1.setVisibility(v.VISIBLE);
                texto2.setVisibility(v.VISIBLE);
                texto3.setVisibility(v.VISIBLE);



                lista_matches = new ArrayList<>();
                lista_finales = new ArrayList<>();
                listafinal = new ArrayList<>();

                for(int i = 0; i < encuentros_cuartos.length; i++ ){
                    //Si llega a ser ganador alguno de los 2
                    if(encuentros_cuartos[i].jugador1.estado_torneo == "g"){
                        Toast.makeText(getContext(), "jugador 1 ganador", Toast.LENGTH_LONG).show();
                    }
                    //si llega a ser perdedor alguno
                    else if (encuentros_cuartos[i].jugador1.estado_torneo == "p" ) {
                        Toast.makeText(getContext(), "jugador 2 ganador", Toast.LENGTH_LONG).show();

                    }
                }

                cargarlista();
                //Toast.makeText(getContext(), "Cargó vista", Toast.LENGTH_LONG).show();

                mostrarVista();
                //Toast.makeText(getContext(), "Mostro vista", Toast.LENGTH_LONG).show();


            }
        });


        // Inflate the layout for this fragment
        return vista;
    }

    private void cargarlista() {


        for (int i = 0 ; i < encuentros_cuartos.length; i++){
            lista_matches.add(encuentros_cuartos[i]);
        }

        for(int i = 0; i < encuentros_semifinal.length; i++){
            lista_finales.add(encuentros_semifinal[i]);

        }
        listafinal.add(encuentro_final);

        //matchesAdapter.notifyDataSetChanged();
        //Toast.makeText(getContext(), "Se notifica", Toast.LENGTH_LONG).show();
    }

    private void mostrarVista() {

        //Se muestran los encuentros
        recyclerView_encuentros.setHasFixedSize(true);
        recyclerView_encuentros.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapter = new MatchesAdapter(lista_matches, getContext(), new MatchesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(Matches match) {
                moveToChange(match);
            }
        });
        recyclerView_encuentros.setAdapter(matchesAdapter);

        //Se muestran las finales
        reciclerView_semifinal.setHasFixedSize(true);
        reciclerView_semifinal.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapter = new MatchesAdapter(lista_finales, getContext(), new MatchesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(Matches match) {
                moveToChange(match);
            }
        });
        reciclerView_semifinal.setAdapter(matchesAdapter);

        //se muestra la final

        recyclerView_final.setHasFixedSize(true);
        recyclerView_final.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesAdapter = new MatchesAdapter(listafinal, getContext(), new MatchesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(Matches match) {
                moveToChange(match);
            }
        });
        recyclerView_final.setAdapter(matchesAdapter);


    }

    private void moveToChange(Matches matches) {

        Intent intent = new Intent(getContext(), CambiosMatchesActivity.class);

        intent.putExtra("Matches", matches );
        startActivity(intent);
    }


}
