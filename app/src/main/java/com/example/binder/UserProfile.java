package com.example.binder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binder.Adapters.ImageListAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //TODO
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.book);

        RecyclerView recyclerViewTunes = findViewById(R.id.recycler);
        ImageListAdapter adapter = new ImageListAdapter(list);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        recyclerViewTunes.setLayoutManager(lm);
        recyclerViewTunes.setAdapter(adapter);
    }

}