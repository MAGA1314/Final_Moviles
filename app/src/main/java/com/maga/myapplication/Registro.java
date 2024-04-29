package com.maga.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {
    DatabaseReference quotesRef;
    private EditText nombreEditText;
    private EditText mailEditText;
    private EditText passEditText;
    private EditText confpassEditText;
    private Button btnVolver, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //bind views
        nombreEditText = (EditText) findViewById(R.id.nombre);
        mailEditText = (EditText) findViewById(R.id.mail);
        passEditText = (EditText) findViewById(R.id.contrasena);
        confpassEditText = (EditText) findViewById(R.id.confirmarcontrasena);
        btnRegistrar = (Button) findViewById(R.id.btnregistro);
        btnVolver = (Button) findViewById(R.id.btnvolver);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get text
                String Nombres = nombreEditText.getText().toString();
                String Mails = mailEditText.getText().toString();
                String Contrasena = passEditText.getText().toString();
                String ConfContrasena = confpassEditText.getText().toString();

                //check if empty
                if (TextUtils.isEmpty(Nombres)) {
                    nombreEditText.setError("Nombre no puede estar vacío");
                    return;
                }
                if (TextUtils.isEmpty(Mails)) {
                    mailEditText.setError("Correo no puede estar vacío");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Mails).matches()) {
                    mailEditText.setError("Ingrese un correo válido");
                    return;
                }
                if (TextUtils.isEmpty(Contrasena)) {
                    passEditText.setError("Contraseña no puede estar vacía");
                    return;
                }
                if (TextUtils.isEmpty(ConfContrasena)) {
                    confpassEditText.setError("Confirmación de contraseña no puede estar vacía");
                    return;
                }
                if (!Contrasena.equals(ConfContrasena)) {
                    confpassEditText.setError("Las contraseñas no coinciden");
                    return;
                }

                //add to database
                addQuoteToDB(Nombres, Mails, Contrasena);
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Esto llama al método onBackPressed() de la actividad, que por defecto cierra la actividad actual y vuelve a la anterior.
            }
        });
    }

    private void addQuoteToDB(String Nombres, String Mails, String Contrasena) {
        //create a hashmap
        HashMap<String, Object> datosHashmap = new HashMap<>();
        datosHashmap.put("nombre", Nombres);
        datosHashmap.put("mail", Mails);
        datosHashmap.put("contrasena", Contrasena);

        //instantiate database connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference quotesRef = database.getReference("Usuarios");

        String key = quotesRef.push().getKey();
        datosHashmap.put("key", key);

        quotesRef.child(key).setValue(datosHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Registro.this, "Usuario Creado :)", Toast.LENGTH_SHORT).show();
                    nombreEditText.getText().clear();
                    mailEditText.getText().clear();
                    passEditText.getText().clear();
                    confpassEditText.getText().clear();
                } else {
                    Toast.makeText(Registro.this, "Error al Crear Usuario :(: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
