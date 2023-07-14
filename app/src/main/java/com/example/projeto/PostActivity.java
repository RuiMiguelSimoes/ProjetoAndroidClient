package com.example.projeto;

import static com.example.projeto.MainActivity.nomeDoUtilizador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView userName;
    TextView postContent;
    EditText commentEditText;
    TextView dataEditText;
    TextView commentCount;
    ImageButton sendComment;
    RecyclerView commentsRecyclerView;
    ArrayList<Comment> commentArrayList;
    CommentAdapter commentAdapter;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        userName = findViewById(R.id.autor_nome);
        postContent = findViewById(R.id.postContentTextView);
        sendComment = findViewById(R.id.submitCommentImageButton);
        commentEditText = findViewById(R.id.comment_edittext);
        dataEditText = findViewById(R.id.data);
        commentCount = findViewById(R.id.commentsCountTextView);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference=database.getReference();

        Bundle extras = getIntent().getExtras();
        userName.setText(extras.getString("intentExtraAuthorName"));
        postContent.setText(extras.getString("intentExtraPostContent"));
        dataEditText.setText(extras.getString("intentExtraPostDate"));
        String postId = extras.getString("intentExtraPostid");

        //contar comentários
        databaseReference.child("Comments").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentCount.setText(String.valueOf(snapshot.getChildrenCount()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //lista para os comentarios
        commentArrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,commentArrayList);
        commentsRecyclerView.setAdapter(commentAdapter);

        databaseReference.child("Comments").child(postId).orderByChild("commenteDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                commentArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){

                    Comment comment = dataSnapshot.getValue(Comment.class);
                    commentArrayList.add(comment);
                }
                commentAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostActivity.this, "Erro ao carregar comentários :(", Toast.LENGTH_SHORT).show();
            }
        });

        //adicionar comentario
        sendComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String commentEditTextContent = commentEditText.getText().toString();
                String commentKey = UUID.randomUUID().toString();
                DateFormat date = new SimpleDateFormat(" dd MMM yyyy, HH:mm:ss");
                String dateFormat = date.format(Calendar.getInstance().getTime());
                Comment comment = new Comment(commentKey, nomeDoUtilizador, commentEditTextContent, dateFormat, user.getUid());

                databaseReference.child("Comments").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("Comments").child(postId).child(commentKey).setValue(comment);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                commentEditText.setText("");
            }
        });


    }


}