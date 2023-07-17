package com.example.projeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto.fragments.FriendsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsFragmentAdapter extends RecyclerView.Adapter<MyFriendsFragmentAdapter.ViewHolder> {

    private List<User> friendsList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();

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

        holder.nome.setText(friend.getEmail());

        holder.eliminarAmigoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("Utilizadores").child(user.getUid()).child("amigos").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren() ){

                            Object value = dataSnapshot.child("email").getValue();
                            
                            if (value == friend.email){
                                databaseReference.child("Utilizadores").child(user.getUid()).child("amigos").child(dataSnapshot.getKey()).removeValue();
                                Toast.makeText(view.getContext(),"Amigo eliminado com sucesso!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
        @Override
        public int getItemCount() {
            return friendsList != null ? friendsList.size() : 0;
        }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nome;
        public Button eliminarAmigoBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome_do_amigo_tv);
            eliminarAmigoBtn = itemView.findViewById(R.id.eliminar);
        }

        public void bind(String friend) {
            // Bind your item data to the views inside the item layout
            // For example:
            nome.setText(friend);
        }
    }
}
