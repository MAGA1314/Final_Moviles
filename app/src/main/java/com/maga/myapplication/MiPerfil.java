package com.maga.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

public class MiPerfil extends AppCompatActivity {

    private TextView txtNombrePerfil, txtCorreoPerfil;
    private DatabaseReference Usuarios;
    FirebaseUser user; // Esta es la variable de instancia que usaremos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mi_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNombrePerfil = findViewById(R.id.txtNombrePerfil);
        txtCorreoPerfil = findViewById(R.id.txtCorreoPerfil);

        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
        user = FirebaseAuth.getInstance().getCurrentUser(); // Usamos la variable de instancia aquí

        if (user!= null) {
            CargarDatos();
        } else {
            // Manejar el caso en que el usuario no esté autenticado
        }
    }

    private void CargarDatos() {
        Usuarios.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombres = snapshot.child("nombre").getValue(String.class);
                    String correo = snapshot.child("mail").getValue(String.class);

                    if (nombres!= null && correo!= null) {
                        txtNombrePerfil.setText(nombres);
                        txtCorreoPerfil.setText(correo);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error
            }
        });
    }
}
