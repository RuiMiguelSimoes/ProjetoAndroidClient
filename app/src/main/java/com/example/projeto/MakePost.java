package com.example.projeto;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.UUID;

public class MakePost extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth auth;
    Button submeterBtn;
    EditText conteudoEt;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        submeterBtn = findViewById(R.id.submeter);
        conteudoEt = findViewById(R.id.conteudo);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        DatabaseReference postsDatabase = database.getReference("Posts");
        //submeter post
        submeterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conteudo = conteudoEt.getText().toString();
                Date nowDate = Calendar.getInstance().getTime();
                String uuid = UUID.randomUUID().toString();

                Post newPost = new Post(user.getEmail(), nowDate, conteudo);
                postsDatabase.child(uuid).setValue(newPost);
                //Toast.makeText(getApplicationContext(), "uuid: "+uuid, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            }
        });
    }
}
