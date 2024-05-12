package com.maga.myapplication;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utilidad {
    public static void verToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static class ReferenciaDeColeccion {
        public CollectionReference collectionReference;

        public ReferenciaDeColeccion(CollectionReference collectionReference) {
            this.collectionReference = collectionReference;
        }

        // Modificado para manejar la creación de nuevos documentos sin argumentos
        public DocumentReference document() {
            // Si no se pasa ningún argumento, se crea un nuevo documento
            return collectionReference.document();
        }
    }

    public static ReferenciaDeColeccion getReferenciaDeColeccion(){
        FirebaseUser UsuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("notas")
                .document(UsuarioActual.getUid()).collection("Mis_Notas");
        return new ReferenciaDeColeccion(collectionReference);
    }

    // Corrección: Cambiar la definición de getDocumentReference para que sea un método estático de Utilidad
    public static DocumentReference getDocumentReference(String docId) {
        FirebaseUser UsuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("notas")
                .document(UsuarioActual.getUid()).collection("Mis_Notas");
        ReferenciaDeColeccion referenciaDeColeccion = new ReferenciaDeColeccion(collectionReference);

        // Devolver una referencia de documento basada en si docId está presente o no
        if (docId != null && !docId.isEmpty()) {
            // Si docId no es nulo ni vacío, se refiere a un documento existente
            return referenciaDeColeccion.collectionReference.document(docId);
        } else {
            // Si docId es nulo o vacío, se crea un nuevo documento
            return referenciaDeColeccion.document();
        }
    }

    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
