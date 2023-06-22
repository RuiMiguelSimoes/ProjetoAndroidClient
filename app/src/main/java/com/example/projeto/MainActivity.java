package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button logout;
    FirebaseUser user;
    FirebaseAuth auth;
    TextView nomeTv;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Post> list;
    String nomeDoUtilizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postsDatabase = database.getReference("Posts");
        logout = findViewById(R.id.logoutBtn);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        nomeTv = findViewById(R.id.TextViewNome);
        recyclerView = findViewById(R.id.postsList);
        DatabaseReference utilizadoresDatabase = FirebaseDatabase.getInstance().getReference();
        floatingActionButton = findViewById(R.id.floatingButton);

        //get user email
        utilizadoresDatabase.child("Utilizadores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    nomeDoUtilizador = childSnapshot.getKey();
                    nomeTv.setText("Bem-vindo "+nomeDoUtilizador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //nomeTv.setText(user.getEmail());

        //submeter post
        /*
        submeterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conteudo = conteudoEt.getText().toString();
                Date nowDate = Calendar.getInstance().getTime();
                String uuid = UUID.randomUUID().toString();

                Post newPost = new Post(user.getEmail(), nowDate, conteudo);
                postsDatabase.child(uuid).setValue(newPost);
                Toast.makeText(getApplicationContext(), "uuid: "+uuid, Toast.LENGTH_LONG).show();
            }
        });

         */

        //Botão de logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);


        Post post = new Post("testasda", "asdadasd");
        list.add(post);
        //myAdapter.notifyDataSetChanged();

        postsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){

                    Post post = dataSnapshot.getValue(Post.class);
                    list.add(post);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //floating button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MakePost.class);
                startActivity(intent);
            }
        });
    }

}