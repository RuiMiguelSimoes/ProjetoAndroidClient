package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseDatabase database;
    TextView nomeTv;
    RecyclerView recyclerView;
    MyPostAdapter postAdapter;
    ArrayList<Post> postList;
    EditText postEditText;
    DatabaseReference databaseReference;
    BottomNavigationView bottomNavigationView;
    String nomeDoUtilizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        nomeTv = findViewById(R.id.TextViewNome);
        recyclerView = findViewById(R.id.postsList);
        database = FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
        DatabaseReference postsDatabase = database.getReference("Posts");
        DatabaseReference utilizadoresDatabase = FirebaseDatabase.getInstance().getReference("Utilizadores");
        bottomNavigationView = findViewById(R.id.bottomnavigationView);

        //get user name form email I guess 🤦‍
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
                nomeTv.setText(nomeDoUtilizador+"'s Wall");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, if any
            }
        });


        //lista para os posts
        postList = new ArrayList<>();
        postAdapter = new MyPostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);
        databaseReference.child("Posts").orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){

                    Post post = dataSnapshot.getValue(Post.class);
                    postList.add(post);

                }
                postAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Set up the input fields
        postEditText = findViewById(R.id.postEditText);

        // Set up the post button click listener
        Button postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postContent = postEditText.getText().toString().trim();
                String postKey = "pk:"+UUID.randomUUID().toString();
                DateFormat date = new SimpleDateFormat(" dd MMM yyyy, HH:mm:ss");
                String dateFormat = date.format(Calendar.getInstance().getTime());

                if (!postContent.isEmpty()) {
                    addPost(postContent);
                    postEditText.setText("");
                }
            }
        });

        //menu de navigação 
        bottomNavigationView.setOnItemSelectedListener(item -> {

            //butão home
            if (item.getItemId() == R.id.menu_home){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            //Atividade Add friend
            if (item.getItemId() == R.id.addFriend) {
                Intent intent = new Intent(getApplicationContext(), AddFriend.class);
                startActivity(intent);
                finish();
                return true;
            }

            //Atividade de difinições
            if (item.getItemId() == R.id.definicoes){
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });

    }
    //adicionar post
    private void addPost(String postContent) {
        String postId = UUID.randomUUID().toString();
        DateFormat date = new SimpleDateFormat(" dd MMM yyyy, HH:mm:ss");
        String dateFormat = date.format(Calendar.getInstance().getTime());
        Post post = new Post(postId, user.getUid(), nomeDoUtilizador, user.getEmail(), dateFormat, postContent);
        databaseReference.child("Posts").child(postId).setValue(post);
        postList.add(post);
        postAdapter.notifyDataSetChanged();

    }

}