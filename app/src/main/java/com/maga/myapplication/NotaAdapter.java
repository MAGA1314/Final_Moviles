package com.maga.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

public class NotaAdapter extends FirestoreRecyclerAdapter<Nota, NotaAdapter.NotaViewHolder> {
    private Context context;
    public NotaAdapter(@NonNull FirestoreRecyclerOptions<Nota> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotaViewHolder notaViewHolder, int position, @NonNull Nota nota) {
        notaViewHolder.TVTitulo.setText(nota.titulo);
        notaViewHolder.TVDescripcion.setText(nota.descripcion);
        notaViewHolder.TVFecha.setText(nota.fecha);
        notaViewHolder.TVTimestamp.setText(Utilidad.timestampToString(nota.timestamp));

        notaViewHolder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, Agregar_Nota.class);
            intent.putExtra("titulo",nota.titulo);
            intent.putExtra("descripcion",nota.descripcion);
            String docID = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docID);
            context.startActivity(intent);

        });
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_nota_item,parent,false);
        return new NotaViewHolder(view);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{
        TextView TVTitulo, TVDescripcion, TVFecha, TVTimestamp;

        public NotaViewHolder(@NonNull View itemView){
            super(itemView);
            TVTitulo = itemView.findViewById(R.id.nota_titulo);
            TVDescripcion = itemView.findViewById(R.id.descripcio_nota);
            TVFecha = itemView.findViewById(R.id.fecha);
            TVTimestamp = itemView.findViewById(R.id.timestamp);

        }
    }
}
