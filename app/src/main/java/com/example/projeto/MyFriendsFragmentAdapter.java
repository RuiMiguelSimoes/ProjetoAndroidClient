package com.example.projeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto.fragments.FriendsFragment;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsFragmentAdapter extends RecyclerView.Adapter<MyFriendsFragmentAdapter.ViewHolder> {

    private List<User> friendsList;

    public MyFriendsFragmentAdapter( List<User> friendsList) {
        this.friendsList = friendsList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User friend = friendsList.get(position);

        holder.nome.setText(friend.nome);
    }
    /*
        @Override
        public int getItemCount() {
            return friendsList.si();
        }
   */
        @Override
        public int getItemCount() {
            return friendsList != null ? friendsList.size() : 0;
        }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nome;

        public ViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome_do_amigo_tv);
        }

        public void bind(String friend) {
            // Bind your item data to the views inside the item layout
            // For example:
            nome.setText(friend);
        }
    }
}
