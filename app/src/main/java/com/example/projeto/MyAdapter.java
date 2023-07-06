package com.example.projeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postsDatabase = database.getReference("Posts");

    ArrayList<Post> list;

    public MyAdapter(Context context, ArrayList<Post> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.post, parent, false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Post post = list.get(position);
        holder.autor.setText(post.getAuthor_name());
        holder.conteudo_post.setText(post.getContent());
        holder.date.setText(post.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                Post clickedPost = list.get(clickedPosition);

                Toast.makeText(context, "context clicado", Toast.LENGTH_SHORT).show();
            }
        });

        holder.submitCommentBtn.setOnClickListener(new View.OnClickListener() {

            int clickedPosition = holder.getAdapterPosition();
            Post clickedPost = list.get(clickedPosition);

            @Override
            public void onClick(View view) {
                postsDatabase.equalTo(clickedPost.postKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String commentKey = "ck:"+UUID.randomUUID().toString();

                        Comment comment = new Comment(commentKey, "utilizador", holder.comentarioTextTv.getText().toString());

                        postsDatabase.child(post.getPostKey()).child("comments").child(commentKey).setValue(comment);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                holder.comentarioTextTv.setText("");
            }
        });

        //contar os likes
        postsDatabase.child(post.getPostKey()).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.numeroComentarios.setText(String.valueOf(snapshot.getChildrenCount()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView autor, conteudo_post, date, comentarioTextTv, numeroComentarios;
        ImageButton submitCommentBtn;

        public MyViewHolder(@NonNull View itemVew){
            super (itemVew);

            autor = itemVew.findViewById(R.id.autor_nome);
            conteudo_post = itemVew.findViewById(R.id.conteudo_post);
            date = itemVew.findViewById(R.id.data);
            submitCommentBtn = itemVew.findViewById(R.id.submit_comment_button);
            comentarioTextTv = itemVew.findViewById(R.id.comment_edittext);
            numeroComentarios = itemVew.findViewById(R.id.numero_comentarios_tv);

        }
    }
}
