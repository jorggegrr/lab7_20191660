package com.example.a20191660_lab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a20191660_lab7.entity.Salonesdto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClienteActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private static final String TAG = "ClienteActivity";

    private List<Salonesdto> publicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);


        db = FirebaseFirestore.getInstance();
        cargarDatos();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos();
            }
        });


    }
    private void cargarDatos() {
        db.collection("salones")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            publicaciones.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Salonesdto publicacion = document.toObject(Salonesdto.class);
                                publicaciones.add(publicacion);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        // Detener la animación de recarga
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void cargarPublicaciones() {
        db.collection("salones")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            publicaciones.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Salonesdto publicacion = document.toObject(Salonesdto.class);
                                publicaciones.add(publicacion);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }


                });
    }

}
