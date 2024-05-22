package com.maga.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {

    Button CerrarSesion;
    ImageButton btnmenu;
    LinearLayout CrearNota, ListarNota, BuscarNota, MiPerfil;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView NombrePrincipal, CorreoPrincipal;
    ProgressBar progressBarDatos;
    DatabaseReference Usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Agenda");
        }


        NombrePrincipal = findViewById(R.id.txtnombrePrincipal);
        CorreoPrincipal = findViewById(R.id.txtcorreoPrincipal);
        progressBarDatos = findViewById(R.id.progressdatos);

        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
        CerrarSesion = findViewById(R.id.cerrarSesion);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //--> ImageButtons
        CrearNota = findViewById(R.id.linearLayoutAgregarNota);
        ListarNota = findViewById(R.id.linearLayoutListarNota);
        BuscarNota = findViewById(R.id.linearLayoutBuscarNota);
        MiPerfil = findViewById(R.id.linearLayoutPerfil);
        btnmenu = findViewById(R.id.btnmenu);

        CrearNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Agregar_Nota.class));
            }
        });
        ListarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Ver_Nota.class));
            }
        });
        BuscarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, filtrar_Nota.class));
            }
        });
        MiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, com.maga.myapplication.MiPerfil.class));
            }
        });
        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalirAplicacion();
            }
        });
        btnmenu.setOnClickListener((v)-> ShowMenu());
    }

    public void ShowMenu(){
        PopupMenu popupMenu = new PopupMenu(MenuPrincipal.this,btnmenu);
        popupMenu.getMenu().add("Cerrar Sesion");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle()=="Cerrar Sesion"){
                    firebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
                    finish();
                }
                return false;
            }
        });
    }
    @Override
    protected void onStart() {
        ComprobarInicioSesion();
        super.onStart();
    }

    private void ComprobarInicioSesion(){
        //El usuario inicio sesion
        if(user != null){
            CargarDatos();
        }else {
            // lo dirijimos a MainActivity
            startActivity(new Intent(MenuPrincipal.this,MainActivity.class));
        }
    }
    private void CargarDatos(){
        Usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Si Existe el Usuario
                if (snapshot.exists()){
                    //Ocultamos progress
                    progressBarDatos.setVisibility(View.GONE);
                    // Mostramos Datos
                    NombrePrincipal.setVisibility(View.VISIBLE);
                    //CorreoPrincipal.setVisibility(View.VISIBLE);

                    //Obenemos datos
                    String nombres = ""+snapshot.child("nombre").getValue();
                    //String correo = ""+snapshot.child("mail").getValue();

                    //setear los datos en los textviews
                    NombrePrincipal.setText(nombres);
                    //CorreoPrincipal.setText(correo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this,"Cerraste sesion", Toast.LENGTH_SHORT).show();
    }
}