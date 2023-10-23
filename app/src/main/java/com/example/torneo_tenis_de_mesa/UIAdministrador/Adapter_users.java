package com.example.torneo_tenis_de_mesa.UIAdministrador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.example.torneo_tenis_de_mesa.R;

import java.util.ArrayList;

public class Adapter_users extends RecyclerView.Adapter<Adapter_users.MyViewHolder> {

    Context context;
    LayoutInflater nInflater;
    ArrayList<Persona> list;

    public Adapter_users(ArrayList<Persona> list, Context context) {
        this.list = list;
        this.context = context;
        this.nInflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Adapter_users.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = nInflater.inflate(R.layout.card_participante, null);

        return new Adapter_users.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Adapter_users.MyViewHolder holder, final int position) {
        //Persona persona = list.get(position);
        holder.binData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre, nombreusuario, correo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_card_participantes);
            nombreusuario = itemView.findViewById(R.id.nombreusuario_card_participantes);
            correo = itemView.findViewById(R.id.correo_card_participantes);

        }

        public void binData(final Persona persona) {

            nombre.setText(persona.getNombre());
            nombreusuario.setText(persona.getNombre_usuario());
            correo.setText(persona.getCorreo());
        }
    }
}
