package com.maga.myapplication;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utilidad {
    public static void verToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static class ReferenciaDeColeccion {
        private CollectionReference collectionReference;

        public ReferenciaDeColeccion(CollectionReference collectionReference) {
            this.collectionReference = collectionReference;
        }
        public DocumentReference document() {
            return collectionReference.document();
        }

        // Aquí puedes agregar métodos adicionales si es necesario
    }

    public static ReferenciaDeColeccion getReferenciaDeColeccion(){
        FirebaseUser UsuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("notas")
                .document(UsuarioActual.getUid()).collection("Mis_Notas");
        return new ReferenciaDeColeccion(collectionReference);
    }

}
