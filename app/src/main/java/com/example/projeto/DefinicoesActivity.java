package com.example.projeto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class DefinicoesActivity extends AppCompatActivity {

    TabLayout tabLayout;
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    MyPageViewAdapter myViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicoes);

        bottomNavigationView = findViewById(R.id.bottomnavigationView);
        bottomNavigationView.setSelectedItemId(R.id.definicoes);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyPageViewAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        //click das tabs do tablayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {

            //butão home
            if (item.getItemId() == R.id.menu_home){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            //Atividade Add friend
            if (item.getItemId() == R.id.addFriend) {
                Intent intent = new Intent(getApplicationContext(), AddFriend.class);
                startActivity(intent);
                finish();
                return true;
            }

            //Atividade de difinições
            if (item.getItemId() == R.id.definicoes){
                Intent intent = new Intent(getApplicationContext(), DefinicoesActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });
    }
}
