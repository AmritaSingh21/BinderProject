package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.binder.Adapters.MatchesAdapter;
import com.example.binder.Entities.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Matches extends AppCompatActivity implements MatchesAdapter.ItemClickListener {

    List<User> users = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        //users.add(new User("Prabh","20"));

        recyclerView = findViewById(R.id.matchedPeopleList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        matchesAdapter = new MatchesAdapter(this,users);
        matchesAdapter.setClickListener(this);
        recyclerView.setAdapter(matchesAdapter);

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

        bottom_menu.setSelectedItemId(R.id.menu_likedBooks);
        bottom_menu.setOnItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case(R.id.menu_searchBook):
                    startActivity(new Intent(Matches.this,SearchBooks.class));
                    break;
                case (R.id.menu_home):
                    startActivity(new Intent(Matches.this,BookSwipe2.class));
                    break;
                case (R.id.menu_my_profile):
                    startActivity(new Intent(Matches.this,MyProfile.class));
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}