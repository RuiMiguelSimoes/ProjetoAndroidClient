package com.example.projeto.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.MyAdapter;
import com.example.projeto.MyFriendsFragmentAdapter;
import com.example.projeto.Post;
import com.example.projeto.R;
import com.example.projeto.User;
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

public class FriendsFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyFriendsFragmentAdapter adapter;
    FirebaseUser user;
    FirebaseAuth auth;
    private ArrayList<User> friendsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        DatabaseReference utilizadoresDatabase = FirebaseDatabase.getInstance().getReference("Utilizadores");

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        utilizadoresDatabase.child(user.getUid()).child("amigos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){

                    User user1 = dataSnapshot.getValue(User.class);
                    friendsList.add(user1);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        TextView tv = view.findViewById(R.id.numero_amigos_tv);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MyFriendsFragmentAdapter(friendsList);
        recyclerView.setAdapter(adapter);

        return view;

    }

}