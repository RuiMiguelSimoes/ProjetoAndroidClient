package com.example.projeto;

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
    FirebaseDatabase database;
    Button submeterBtn;
    EditText conteudoEt;
    TextView cancelar;

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
        Intent intent = getIntent();
        String nomeUtilizador = intent.getStringExtra("nomeUtilizador");
        cancelar = findViewById(R.id.cancel_button);

        //submeter post
        submeterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String conteudo = conteudoEt.getText().toString();
                Date nowDate = Calendar.getInstance().getTime();
                String postKey = "pk:"+UUID.randomUUID().toString();
                DateFormat date = new SimpleDateFormat(" dd MMM yyyy, HH:mm:ss");
                String dateFormat = date.format(Calendar.getInstance().getTime());


                Post newPost = new Post(postKey ,user.getUid(), nomeUtilizador, user.getEmail(), dateFormat, conteudo);
                postsDatabase.child(postKey).setValue(newPost);
                //Toast.makeText(getApplicationContext(), "uuid: "+uuid, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //botão de cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
