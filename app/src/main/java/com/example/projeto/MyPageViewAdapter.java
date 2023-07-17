package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projeto.fragments.FriendsFragment;
import com.example.projeto.fragments.PerfilFragment;

public class MyPageViewAdapter extends FragmentStateAdapter {

    public MyPageViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position){
           case 0:
               return new PerfilFragment();

           case 1:
               return new FriendsFragment();

           default:
               return new FriendsFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
