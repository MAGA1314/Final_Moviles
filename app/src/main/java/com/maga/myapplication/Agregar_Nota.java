package com.maga.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Agregar_Nota extends AppCompatActivity {

    EditText Titulo, Descripcion, Fecha;
    TextView tituloPagina;
    String title,descripcion,docId;

    ImageButton btnMisNotas, btnBorrar;
    Button btnGuardar, btnVolver; // Actualizado el nombre del botón
    Boolean editarModo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota); // Asegúrate de que este layout corresponda al nuevo layout proporcionado

        Titulo = findViewById(R.id.titulo);
        Descripcion = findViewById(R.id.descripcion);
        Fecha = findViewById(R.id.fecha);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnMisNotas = findViewById(R.id.btnVerNotas);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btn_Volver);
        tituloPagina =findViewById(R.id.titulo_VerNota);

        //Recibir los Datos
        title = getIntent().getStringExtra("titulo");
        descripcion = getIntent().getStringExtra("descripcion");
        docId = getIntent().getStringExtra("docId");

        if (docId!=null && !docId.isEmpty()){
            editarModo=true;
        }

        Titulo.setText(title);
        Descripcion.setText(descripcion);

        if (editarModo){
            tituloPagina.setText("Editar Nota");
            btnBorrar.setVisibility(View.VISIBLE);
        }else{
            btnBorrar.setVisibility(View.GONE);//Ocultar si no esta en mmodo edicion;
        }


        // Agregar TextWatcher al EditText de la fecha
        Fecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario para este ejemplo
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario para este ejemplo
            }

            @Override
            public void afterTextChanged(Editable s) {
                validarFecha(s.toString());
            }
        });
        btnMisNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Agregar_Nota.this, Ver_Nota.class));
            }
        });

        btnGuardar.setOnClickListener(v -> GuardarNota());

        btnBorrar.setOnClickListener((v)-> borrarNotaFirebase());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Agregar_Nota.this, MenuPrincipal.class));
            }
        });
    }

    private void borrarNotaFirebase() {
        Utilidad.ReferenciaDeColeccion referenciaDeColeccion = Utilidad.getReferenciaDeColeccion();
        DocumentReference documentReference;
            documentReference = Utilidad.getDocumentReference(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Nota Eliminada
                    Utilidad.verToast(Agregar_Nota.this,"Nota Eliminada");
                }else{
                    // Nota sin agregar o actualizar
                    Utilidad.verToast(Agregar_Nota.this,"Nota Sin Eliminar");
                }
            }
        });
    }

    private void validarFecha(String fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFormat.setLenient(false); // Establecer en false para una validación estricta
        try {
            Date parsedDate = dateFormat.parse(fecha.trim());
            // La fecha es válida
        } catch (ParseException e) {
            // La fecha no es válida
            Fecha.setError("Por favor, ingresa una fecha válida (YYYY-MM-DD)");
        }
    }

    void GuardarNota(){
        String notaTitulo = Titulo.getText().toString();
        String notaDescripcion = Descripcion.getText().toString();
        String notaFecha = Fecha.getText().toString();
        // Verificar si alguno de los campos está vacío
        if (notaTitulo.isEmpty()) {
            Titulo.setError("Por favor, ingresa un título");
            return;
        }
        if (notaDescripcion.isEmpty()) {
            Descripcion.setError("Por favor, ingresa una descripción");
            return;
        }
        if (notaFecha.isEmpty()) {
            Fecha.setError("Por favor, ingresa una fecha");
            return;
        }

        // Si todos los campos están llenos, proceder a guardar la nota
        Nota nota = new Nota();
        nota.setTitulo(notaTitulo);
        nota.setDescripcion(notaDescripcion);
        nota.setFecha(notaFecha);

        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        nota.setTimestamp(Timestamp.now());

        GuardarNotaFirebase(nota);
    }


    void GuardarNotaFirebase(Nota nota){
        Map<String, Object> notaData = new HashMap<>();
        notaData.put("titulo", nota.getTitulo());
        notaData.put("descripcion", nota.getDescripcion());
        notaData.put("fecha", nota.getFecha());
        notaData.put("timestamp", nota.getTimestamp());

        Utilidad.ReferenciaDeColeccion referenciaDeColeccion = Utilidad.getReferenciaDeColeccion();
        DocumentReference documentReference;

        if(editarModo){
            // Actualizar Nota
            documentReference = Utilidad.getDocumentReference(docId);
        } else {
            // Crear Nueva Nota
            // Asegúrate de que el método document() se llame sin argumentos aquí
            documentReference = referenciaDeColeccion.document();
        }

        documentReference.set(notaData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Nota agregada o actualizada
                    Utilidad.verToast(Agregar_Nota.this,"Nota Agregada o Actualizada.");
                }else{
                    // Nota sin agregar o actualizar
                    Utilidad.verToast(Agregar_Nota.this,"Nota No Agregada o Actualizada.");
                }
            }
        });
    }

}
