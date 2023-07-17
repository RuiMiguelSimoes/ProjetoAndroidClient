package com.example.projeto.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projeto.LoginActivity;
import com.example.projeto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilFragment extends Fragment implements View.OnClickListener {

    FirebaseUser user;
    FirebaseAuth auth;
    String nomeDoUtilizador;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        DatabaseReference utilizadoresDatabase = FirebaseDatabase.getInstance().getReference("Utilizadores");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        TextView nome = (TextView) view.findViewById(R.id.perfil_nome);
        TextView email = view.findViewById(R.id.perfil_email);

        email.append( user.getEmail());

        utilizadoresDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot valueSnapshot = dataSnapshot;
                    if (valueSnapshot.exists()) {
                        Object value = valueSnapshot.child("nome").getValue();
                        // Do something with the retrieved value
                        nomeDoUtilizador = value.toString();
                    } else {
                        // The key does not exist in the database
                        System.out.println("Key does not exist");
                    }
                }
                nome.append(nomeDoUtilizador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, if any
            }
        });

        Button logoutBtn =  (Button) view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this::onClick);

        return view;
    }

    @Override
    public void onClick(View v) {
        // Handle button click event
        if (v.getId() == R.id.logoutBtn) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

}
