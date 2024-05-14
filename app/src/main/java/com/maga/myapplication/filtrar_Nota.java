package com.maga.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class filtrar_Nota extends AppCompatActivity {

    ImageButton BtnMenu;
    EditText BuscarTexto;
    FirebaseAuth firebaseAuth;

    ExtendedFloatingActionButton btnVolver, btnAgregar, btnPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filtrar_nota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BtnMenu = findViewById(R.id.btnMenu);
        btnVolver = findViewById(R.id.btnVolver);
        btnAgregar = findViewById(R.id.add_note);
        btnPerfil = findViewById(R.id.perfilbtn);
        BuscarTexto = findViewById(R.id.ETbuscar);

        firebaseAuth = FirebaseAuth.getInstance();


        BtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(filtrar_Nota.this,MenuPrincipal.class));
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(filtrar_Nota.this, Agregar_Nota.class));
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(filtrar_Nota.this, MiPerfil.class));
            }
        });
    }
    public void ShowMenu(){
        PopupMenu popupMenu = new PopupMenu(filtrar_Nota.this,BtnMenu);
        popupMenu.getMenu().add("Agregar Nota");
        popupMenu.getMenu().add("Ver Mis Notas");
        popupMenu.getMenu().add("Mi Perfil");
        popupMenu.getMenu().add("Cerrar Sesion");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle()=="Cerrar Sesion"){
                    firebaseAuth.getInstance().signOut();
                    startActivity(new Intent(filtrar_Nota.this, MainActivity.class));
                    finish();
                } else if (item.getTitle()=="Agregar Nota") {
                    startActivity(new Intent(filtrar_Nota.this, Agregar_Nota.class));
                } else if (item.getTitle()=="Ver Mis Notas") {
                    startActivity(new Intent(filtrar_Nota.this, Ver_Nota.class));
                } else if (item.getTitle()=="Mi Perfil") {
                    startActivity(new Intent(filtrar_Nota.this, MiPerfil.class));
                }
                return false;
            }
        });
    }
}