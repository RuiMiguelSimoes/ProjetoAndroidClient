package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.Calendar;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseAuth auth;
    TextView nomeTv;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Post> list;
    String nomeDoUtilizador;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postsDatabase = database.getReference("Posts");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        nomeTv = findViewById(R.id.TextViewNome);
        recyclerView = findViewById(R.id.postsList);
        DatabaseReference utilizadoresDatabase = FirebaseDatabase.getInstance().getReference();
        floatingActionButton = findViewById(R.id.floatingButton);
        bottomNavigationView = findViewById(R.id.bottomnavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_home_btn);

        //get user name form email I guess 🤦‍
        String userEmail = user.getEmail();
        utilizadoresDatabase.child("Utilizadores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    String TAG = "MyActivity";
                    Log.i(TAG, "keys: "+ childSnapshot.getKey());
                    Log.i(TAG, "keys: "+ childSnapshot.getValue());

                    if(Objects.equals(user.getEmail(), childSnapshot.getValue().toString())){
                        nomeDoUtilizador=childSnapshot.getKey();
                    }
                }
                nomeTv.setText(nomeDoUtilizador+"'s Wall");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //lista para os posts
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        postsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){

                    Post post = dataSnapshot.getValue(Post.class);
                    list.add( post);

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
                intent.putExtra("nomeUtilizador",nomeDoUtilizador);
                startActivity(intent);
            }
        });

        //barra menu flotuante
        bottomNavigationView.setOnItemSelectedListener(item -> {
            //Botão de logout
            if (item.getItemId() == R.id.menu_logout_btn) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            //Atividade Add friend
            if (item.getItemId() == R.id.addFriend) {
                Intent intent = new Intent(getApplicationContext(), AddFriend.class);
                startActivity(intent);
                finish();
            }

            //Atividade de difinições
            if (item.getItemId() == R.id.definicoes){
                Intent intent = new Intent(getApplicationContext(), DefinicoesActivity.class);
                startActivity(intent);
                finish();
            }

            return false;
        });

    }

}