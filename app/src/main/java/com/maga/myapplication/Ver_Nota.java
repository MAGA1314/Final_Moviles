package com.maga.myapplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton; // Asegúrate de que esta sea la única importación relacionada con FloatingActionButton
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class Ver_Nota extends AppCompatActivity {

    RecyclerView recyclerView;
    ExtendedFloatingActionButton addNota, btnVolver; // Cambia el tipo a ExtendedFloatingActionButton
    ImageButton menubtn;
    NotaAdapter notaAdapter;
    //Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_nota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addNota = findViewById(R.id.add_note); // Ahora es seguro asignar el botón flotante
        recyclerView = findViewById(R.id.recycler_view_notas);
        menubtn = findViewById(R.id.btnMenu);
        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ver_Nota.this, MenuPrincipal.class));
            }
        });
        addNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ver_Nota.this, Agregar_Nota.class));
            }
        });
        menubtn.setOnClickListener((v) -> verMenu());
        configurarRecyclerView();
    }

    void configurarRecyclerView() {
        verMenu();
    }

    void verMenu() {
        // Tu lógica para mostrar el menú
        Utilidad.ReferenciaDeColeccion referenciaDeColeccion = Utilidad.getReferenciaDeColeccion();
        CollectionReference collectionRef = referenciaDeColeccion.collectionReference;
        Query query = collectionRef.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Nota> options = new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query, Nota.class).build();
        notaAdapter = new NotaAdapter(options, this); // Inicializa notaAdapter con los datos reales
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notaAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (notaAdapter!= null) { // Asegúrate de que notaAdapter no sea null antes de llamar a startListening()
            notaAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (notaAdapter!= null) {
            notaAdapter.stopListening();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (notaAdapter!= null) {
            notaAdapter.notifyDataSetChanged();
        }
    }
}
