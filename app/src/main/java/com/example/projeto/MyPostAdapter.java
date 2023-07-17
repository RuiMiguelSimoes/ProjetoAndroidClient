package com.example.projeto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyViewHolder> {

    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    List<Post> postList;

    public MyPostAdapter(Context context, List<Post> list){
        this.context = context;
        this.postList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Post post = postList.get(position);
        holder.autor.setText(post.getAuthor_name());
        holder.conteudo_post.setText(post.getContent());
        holder.date.setText(post.getDate());

        //contar o numero de COMENTARIOS
        databaseReference.child("Comments").child(post.postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.numeroComentarios.setText(String.valueOf(snapshot.getChildrenCount()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();

                String postId = post.getPostKey();
                String author_name = post.author_name;
                String postContent = post.content;
                String postData = post.date;
                Intent intent = new Intent(context, PostActivity.class);

                intent.putExtra("intentExtraPostid", postId);
                intent.putExtra("intentExtraAuthorName", author_name);
                intent.putExtra("intentExtraPostContent", postContent);
                intent.putExtra("intentExtraPostDate", postData);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView autor, conteudo_post, date, numeroComentarios;
        public MyViewHolder(@NonNull View itemVew){
            super (itemVew);

            autor = itemVew.findViewById(R.id.autor_nome);
            conteudo_post = itemVew.findViewById(R.id.postContentTextView);
            date = itemVew.findViewById(R.id.dataTextView);
            numeroComentarios = itemVew.findViewById(R.id.numero_comentarios_tv);

        }
    }
}
