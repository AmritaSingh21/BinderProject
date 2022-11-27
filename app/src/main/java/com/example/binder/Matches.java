package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.binder.Adapters.BooksAdapter;
import com.example.binder.Adapters.MatchesAdapter;
import com.example.binder.Entities.Book;
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
import java.util.List;

public class Matches extends AppCompatActivity {

    List<User> users = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MatchesAdapter matchesAdapter;
    String bookId;

    private FirebaseAuth auth;
    private DatabaseReference dbRef;
    private FirebaseDatabase firebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        Log.i("USER ID", this.toString());

        //get book id from bundle
        bookId = getIntent().getExtras().get("bookID").toString();

        auth = FirebaseAuth.getInstance();
        firebaseInstance = FirebaseDatabase.getInstance();

        //getUsers();
        getUsersFromBook();

        recyclerView = findViewById(R.id.matchedPeopleList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

        bottom_menu.setSelectedItemId(R.id.menu_likedBooks);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case (R.id.menu_searchBook):
                    startActivity(new Intent(Matches.this, SearchBooks.class));
                    break;
                case (R.id.menu_home):
                    startActivity(new Intent(Matches.this, BookSwipe2.class));
                    break;
                case (R.id.menu_my_profile):
                    startActivity(new Intent(Matches.this, MyProfile.class));
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void getUsersFromBook() {
        dbRef = firebaseInstance.getReference("books");
        dbRef.child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Book book = snapshot.getValue(Book.class);
                ArrayList<String> userIds = book.getUserIds();
                if (userIds == null) {
                    userIds = new ArrayList();
                }
                fetchUserList(userIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchUserList(ArrayList<String> userIds) {
        dbRef = firebaseInstance.getReference("users");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (userIds.contains(user.getId())
                            && !user.getId().equalsIgnoreCase(auth.getCurrentUser().getUid())) {
                        users.add(user);
                    }
                }
                matchesAdapter = new MatchesAdapter(getApplicationContext(), users);
                recyclerView.setAdapter(matchesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void getUsers() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                users.clear();
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    User user = snapshot1.getValue(User.class);
//                    Log.i("USER ID", user.getId() + "   " + firebaseUser.getUid());
//
//                    if (!user.getId().equals(firebaseUser.getUid())) {
//                        users.add(user);
//                    }
//
//                }
//                matchesAdapter = new MatchesAdapter(getApplicationContext(), users);
//                recyclerView.setAdapter(matchesAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}