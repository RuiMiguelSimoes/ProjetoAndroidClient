package com.example.projeto;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText nomeEt;
    EditText passwordEt;
    EditText emailEt;


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nomeEt = findViewById(R.id.editTextNome);
        emailEt = findViewById(R.id.editTextTextEmailAddress);
        passwordEt = findViewById(R.id.editTextTextPassword);
        Button registerBtn = findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference utlizadoresDatabase = database.getReference("Utilizadores");

        //submeter registo

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = nomeEt.getText().toString();
                String password = passwordEt.getText().toString();
                String email = emailEt.getText().toString();

                registerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String nome = nomeEt.getText().toString();
                        String password = passwordEt.getText().toString();
                        String email = emailEt.getText().toString();

                        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)){
                            Toast.makeText(RegisterActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                        }

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Utilizador registado com sucesso!");
                                            Toast.makeText(RegisterActivity.this, "Utilizador registado com sucesso!",Toast.LENGTH_SHORT).show();

                                            utlizadoresDatabase.child(nome).setValue(email);
                                            // nomes n podem conter '.', '#', '$', '[', ou ']'
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(RegisterActivity.this, "Registo falhou!"+task.getException(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        });


    }


}
