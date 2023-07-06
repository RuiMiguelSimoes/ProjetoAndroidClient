package com.example.projeto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostActitivy extends AppCompatActivity {

    TextView comentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        comentar = findViewById(R.id.comentar);

        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PostActitivy.this, "teste", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
