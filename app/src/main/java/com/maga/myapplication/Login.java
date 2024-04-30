package com.maga.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText mailEditText;
    private EditText passEditText;
    private TextView newusuario;
    private Button btnLogin, btnVolver;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Enlaza las vistas
        mailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnVolver = findViewById(R.id.btnvolver);
        newusuario = findViewById(R.id.usuarioNuevo);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mailEditText.getText().toString();
                String password = passEditText.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser(email, password);
            }
        });
        newusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            // Aquí puedes redirigir al usuario a la actividad principal de tu aplicación
                            startActivity(new Intent(Login.this, MenuPrincipal.class));
                        } else {
                            // Si el inicio de sesión falla, maneja las excepciones específicas
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                // La contraseña es incorre
                                // cta
                                Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                // El usuario no existe
                                Toast.makeText(Login.this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                            } else {
                                // Otros errores
                                Toast.makeText(Login.this, "Error en el inicio de sesión: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}