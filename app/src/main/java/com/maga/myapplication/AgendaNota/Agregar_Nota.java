package com.maga.myapplication.AgendaNota;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.maga.myapplication.R;

import java.util.Calendar;

public class Agregar_Nota extends AppCompatActivity {

    TextView Uid_Usuario, Correo_usuario, Fecha_hora_actual, Fecha, Estado;
    EditText Titulo, Descripcion;
    Button Btn_Calendario;
    int dia, mes, anio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
    }
    /*    // Inicialización existente

        // Inicializa Firebase
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        notesRef = database.getReference("Notes"); // Asume que "Notes" es el nombre de la colección en Firebase

        // Obtén el UID del usuario actualmente autenticado
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        // Inicializa las variables
        InicializarVariables();
        ObtenerDatos();
        Obtener_Fecha_Hora_Actual();

        // Botón de guardar nota
        btnGuardarNota = findViewById(R.id.btnGuardarNota); // Asume que tienes un botón con id btnGuardarNota
        btnGuardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloNota = titulo.getText().toString();
                String descripcionNota = descripcion.getText().toString();
                String fechaNota = fecha.getText().toString();
                String estadoNota = estado.getText().toString();

                // Crea un objeto Nota
                Nota nota = new Nota(tituloNota, descripcionNota, fechaNota, estadoNota);

                // Guarda la nota en Firebase
                notesRef.child(userId).push().setValue(nota)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Agregar_Nota.this, "Nota guardada exitosamente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Agregar_Nota.this, "Error al guardar la nota: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private void InicializarVariables(){
        Uid_Usuario = findViewById(R.id.uidUsuario);
        Correo_usuario = findViewById(R.id.correoUsuario);
        Fecha_hora_actual = findViewById(R.id.fecha_hora_actual);
        Fecha = findViewById(R.id.fecha);
        Estado = findViewById(R.id.txtEstado);
        Btn_Calendario = findViewById(R.id.btnCalendario);
        Titulo = findViewById(R.id.titulo);
        Descripcion = findViewById(R.id.descripcion);
    }*/
}