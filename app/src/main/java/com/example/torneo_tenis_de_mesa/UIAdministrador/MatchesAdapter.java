package com.example.torneo_tenis_de_mesa.UIAdministrador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torneo_tenis_de_mesa.Polimorfismo.Matches;
import com.example.torneo_tenis_de_mesa.R;

import java.util.ArrayList;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder> {
    Context context;
    LayoutInflater nInflater;

    ArrayList<Matches> lista_matches;

    final MatchesAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClickListener(Matches match);
    }

    public MatchesAdapter(ArrayList<Matches> lista_matches, Context context, MatchesAdapter.OnItemClickListener listener) {
        this.lista_matches = lista_matches;
        this.context = context;
        this.nInflater= LayoutInflater.from(context);
        this.listener = listener;
    }
    @NonNull
    @Override
    public MatchesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = nInflater.inflate(R.layout.card_matches, null);

        return new MatchesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MatchesAdapter.MyViewHolder holder, final int position) {
        //Persona persona = list.get(position);
        holder.binData(lista_matches.get(position));
    }

    @Override
    public int getItemCount() {
        return lista_matches.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre_participante1, nombre_participante2, lugar, fecha;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_participante1 = itemView.findViewById(R.id.match_card_jugador1);
            nombre_participante2 = itemView.findViewById(R.id.match_card_jugador2);
            lugar = itemView.findViewById(R.id.match_card_lugar);
            fecha = itemView.findViewById(R.id.match_card_fecha);

        }

        public void binData(final Matches match) {

            nombre_participante1.setText(match.getJugador1().getNombre());
            nombre_participante2.setText(match.getJugador2().getNombre());
            lugar.setText(match.lugar);
            fecha.setText(match.fecha);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.OnItemClickListener(match);
                }
            });

        }
    }
}
