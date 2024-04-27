package com.maga.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {
    Button btn_Volver, btn_Registrar;
    TextView TengoCuenta;
    EditText Nombres, Mail, Contrasena, ConfirmarContraseña;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String nombre ="", correo="", password="", confirmarpass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Asegúrate de que la ActionBar esté disponible antes de intentar modificarla
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Registrar");
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


        btn_Registrar = findViewById(R.id.btnregistro);
        Nombres = findViewById(R.id.nombre);
        Mail = findViewById(R.id.mail);
        Contrasena = findViewById(R.id.contrasena);
        ConfirmarContraseña = findViewById(R.id.confirmarcontrasena);
        TengoCuenta = findViewById(R.id.Tengocuenta);
        btn_Volver = findViewById(R.id.btnvolver);

        /*Autenticacion de FireBase*/
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Espere un momento...");
        progressDialog.setCanceledOnTouchOutside(false);

        /*Boton Registrar*/
        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarDatos();
            }
        });

        TengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
        /* Boton volver */
        btn_Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, MainActivity.class));
            }
        });


    }
    private  void ValidarDatos(){
        nombre = Nombres.getText().toString();
        correo = Mail.getText().toString();
        password = Contrasena.getText().toString();
        confirmarpass = ConfirmarContraseña.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Ingrese Nombre", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese Correo", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmarpass)) {
            Toast.makeText(this,"Confirme Contraseña", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmarpass)) {
            Toast.makeText(this,"Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
        } else {
            CrearCuenta();
        }
    }
    private void CrearCuenta(){
        progressDialog.setMessage("Creando su cuenta...");
        progressDialog.show();

        /*Crear un usuario en firebase*/
        firebaseAuth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult){
                         GuardaDatos();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GuardaDatos() {
        progressDialog.setMessage("Guardando Su Informacion");
        progressDialog.dismiss();

        //Obtener la identificacion del usuario actual
        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("corre", correo);
        Datos.put("nombres", nombre);
        Datos.put("password", password);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this, MenuPrincipal.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}