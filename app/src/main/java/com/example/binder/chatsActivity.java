package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.binder.Adapters.MatchesAdapter;
import com.example.binder.Entities.Message;
import com.example.binder.Entities.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

public class chatsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MatchesAdapter matchesAdapter;
    private List<User> users;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List<String> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

        bottom_menu.setSelectedItemId(R.id.menu_likedBooks);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case (R.id.menu_searchBook):
                    startActivity(new Intent(chatsActivity.this, SearchBooks.class));
                    break;
                case (R.id.menu_home):
                    startActivity(new Intent(chatsActivity.this, BookSwipe2.class));
                    break;
                case (R.id.menu_my_profile):
                    startActivity(new Intent(chatsActivity.this, MyProfile.class));
                    break;
                case (R.id.menu_likedBooks):
                    startActivity(new Intent(chatsActivity.this, LikedBooks.class));
                    break;
                default:
                    break;
            }
            return true;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Message message = snapshot1.getValue(Message.class);
                    if(message.getSender().equals(firebaseUser.getUid())){
                        usersList.add(message.getReceiver());
                    }
                    if(message.getReceiver().equals(firebaseUser.getUid())){
                        usersList.add(message.getSender());
                    }
                }
                readMessages();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessages() {
        users = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);

                    for(String id: usersList){
                        if (user.getId().equals(id) && !users.contains(user)){
                                users.add(user);
                        }
                    }
                }

                matchesAdapter = new MatchesAdapter(getApplicationContext(),users);
                recyclerView.setAdapter(matchesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}