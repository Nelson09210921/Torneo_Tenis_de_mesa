package com.example.torneo_tenis_de_mesa.UIAdministrador;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torneo_tenis_de_mesa.Administrador;
import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.example.torneo_tenis_de_mesa.R;
import com.example.torneo_tenis_de_mesa.Registro;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class ParticipantesFragment extends Fragment {
    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Adapter_users adapterUsers;
    ArrayList<Persona> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =inflater.inflate(R.layout.fragment_participantes, container, false);
        recyclerView = vista.findViewById(R.id.lista_de_usuarios);
        list = new ArrayList<>();
        cargarlista();
        mostrarlista();
        // Inflate the layout for this fragment
        return vista;
    }

    private void mostrarlista() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterUsers = new Adapter_users(list, getContext());
        recyclerView.setAdapter(adapterUsers);
        Toast.makeText(getContext(), "se mostro ", Toast.LENGTH_LONG).show();
    }

    private void cargarlista() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Persona");
        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Persona p = dataSnapshot.getValue(Persona.class);
                    list.add(p);
                    //Toast.makeText(getContext(), p.getNombre_usuario(), Toast.LENGTH_LONG).show();

                }
                adapterUsers.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

