package com.example.a20191660_lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            redirectUserBasedOnRole(user.getEmail());
        }
    }
    private void redirectUserBasedOnRole(String email) {
            Query query = db.collection("usuarios").whereEqualTo("correo", email);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        Map<String, Object> data = document.getData();
                        String rol = (String) data.get("rol");
                        String estadoStr = (String) data.get("estado");

                        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tipoUsuario", rol);
                        editor.apply();

                        if("activo".equals(estadoStr)){
                            switch (rol) {
                                case "Gestor de Salón de Belleza":
                                    startActivity(new Intent(MainActivity.this, BaseGeneralActivity.class));
                                    break;
                                case "Cliente":
                                    startActivity(new Intent(MainActivity.this, ClienteActivity.class));
                                    break;
                                default:
                                   startActivity(new Intent(MainActivity.this, ClienteActivity.class));
                                    break;
                            }
                            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "La cuenta no se encuentra habilitada, comuníquese con el administrador.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                         Toast.makeText(MainActivity.this, "Usuario no encontrado en la base de datos.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener datos del usuario.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
