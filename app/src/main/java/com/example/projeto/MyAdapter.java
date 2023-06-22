package com.example.projeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

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
        holder.autor.setText(post.getBy());
        holder.conteudo_post.setText(post.getContent());
        //holder.date.setText(post.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView autor, conteudo_post;
        //TextView date;

        public MyViewHolder(@NonNull View itemVew){
            super (itemVew);

            autor = itemVew.findViewById(R.id.autor);
            conteudo_post = itemVew.findViewById(R.id.conteudo_post);
            //date = itemVew.findViewById(R.id.data);
        }
    }
}
