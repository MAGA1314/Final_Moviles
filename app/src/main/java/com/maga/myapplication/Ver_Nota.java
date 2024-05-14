package com.maga.myapplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class Ver_Nota extends AppCompatActivity {

    RecyclerView recyclerView;
    ExtendedFloatingActionButton AddNota, BtnVolver, BtnBuscar; // Cambia el tipo a ExtendedFloatingActionButton
    ImageButton menubtn;
    NotaAdapter notaAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

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
        AddNota = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recycler_view_notas);
        menubtn = findViewById(R.id.btnMenu);
        BtnVolver = findViewById(R.id.btnVolver);
        BtnBuscar = findViewById(R.id.btnBuscar);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        BtnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ver_Nota.this, MenuPrincipal.class));
            }
        });
        AddNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ver_Nota.this, Agregar_Nota.class));
            }
        });
        BtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ver_Nota.this, filtrar_Nota.class));
            }
        });
        menubtn.setOnClickListener((v) -> ShowMenu()); // Llama a ShowMenu() cuando se hace clic en el botón del menú
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
        if (notaAdapter!= null) {
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

    public void ShowMenu() {
        PopupMenu popupMenu = new PopupMenu(Ver_Nota.this, menubtn);
        popupMenu.getMenu().add("Mi Perfil");
        popupMenu.getMenu().add("Cerrar Sesion");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Cerrar Sesion")) {
                    SalirAplicacion();
                }
                else if (item.getTitle().equals("Mi Perfil")){
                    startActivity(new Intent(Ver_Nota.this, MiPerfil.class));
                }
                return false;
            }
        });
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(Ver_Nota.this, MainActivity.class)); // Asegúrate de cambiar MainActivity por la actividad principal de tu aplicación
        finish();
    }
}
