package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.binder.Adapters.MatchesAdapter;
import com.example.binder.Entities.User;

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

        users.add(new User("Prabh","20"));

        recyclerView = findViewById(R.id.matchedPeopleList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        matchesAdapter = new MatchesAdapter(this,users);
        matchesAdapter.setClickListener(this);
        recyclerView.setAdapter(matchesAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}