package com.example.torneo_tenis_de_mesa.UI_Participante;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.torneo_tenis_de_mesa.Polimorfismo.Matches;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.example.torneo_tenis_de_mesa.R;
import com.example.torneo_tenis_de_mesa.UIAdministrador.MatchesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FirebaseUser current_User;

    RecyclerView recyclerView_proxEncuentros;

    ArrayList<Matches> lista_proxencuentros;

    DatabaseReference databaseReference_jugador;
    String email_current_user;

    ArrayList<Matches> lista_de_participaciones;

    MatchesAdapter matchesAdapter;


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View vista = inflater.inflate(R.layout.fragment_home, container, false);
       recyclerView_proxEncuentros = vista.findViewById(R.id.lista_de_prox_encuentros);



       lista_proxencuentros = new ArrayList<>();
       cargar_lista();
       mostrar_lista();


        return vista;

    }


    private void cargar_lista() {

        current_User = FirebaseAuth.getInstance().getCurrentUser();
        String email_current_user = current_User.getEmail();
        databaseReference_jugador = FirebaseDatabase.getInstance().getReference("Torneo").child("Torneo1");

        Query consultajugador_uno = databaseReference_jugador.orderByChild("tipo_objeto").equalTo("match");

        consultajugador_uno.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Matches p = dataSnapshot.getValue(Matches.class);

                    if(p.getJugador1().getCorreo().equals(email_current_user) || p.getJugador2().getCorreo().equals(email_current_user)){
                        lista_proxencuentros.add(p);
                        Toast.makeText(getContext(), p.getJugador1().getNombre(), Toast.LENGTH_SHORT).show();
                    }
                }
                //Error comun colocar esto en el lugar equivocado
                matchesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        /*
        consultajugador_uno.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(snapshot.exists()){
                    Toast.makeText(getContext(), "Aca esta llegandp biern", Toast.LENGTH_SHORT).show();

                    DatabaseReference parent_ref = snapshot.getRef().getParent();

                    parent_ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Matches matches_jugador = snapshot.getValue(Matches.class);
                            lista_de_participaciones.add(matches_jugador);
                            Toast.makeText(getContext(), matches_jugador.getFecha().toString(), Toast.LENGTH_SHORT).show();

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
        consultajudaro_dos.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    DatabaseReference parent_ref = snapshot.getRef().getParent();

                    parent_ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Matches matches_jugador = snapshot.getValue(Matches.class);
                            lista_de_participaciones.add(matches_jugador);
                            Toast.makeText(getContext(), matches_jugador.getFecha().toString(), Toast.LENGTH_SHORT).show();
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
        });*/

    }
    private void mostrar_lista() {
       recyclerView_proxEncuentros.setHasFixedSize(true);
       recyclerView_proxEncuentros.setLayoutManager(new LinearLayoutManager(getContext()));
       matchesAdapter = new MatchesAdapter(lista_proxencuentros, getContext(), new MatchesAdapter.OnItemClickListener() {
           @Override
           public void OnItemClickListener(Matches match) {}
       });
       recyclerView_proxEncuentros.setAdapter(matchesAdapter);
    }

}