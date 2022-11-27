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
import com.example.binder.Adapters.SwipeAdapter;
import com.example.binder.Entities.Book;
import com.example.binder.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LikedBooks extends AppCompatActivity implements BooksAdapter.ItemClickListener {

    List<Book> bList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BooksAdapter booksAdapter;

    private FirebaseAuth auth;
    private DatabaseReference dbRef;
    private FirebaseDatabase firebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_books);

        bList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        firebaseInstance = FirebaseDatabase.getInstance();

        fetchBooksFromUser();

        recyclerView = findViewById(R.id.likedBooksList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

        bottom_menu.setSelectedItemId(R.id.menu_likedBooks);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case (R.id.menu_searchBook):
                    startActivity(new Intent(LikedBooks.this, SearchBooks.class));
                    break;
                case (R.id.menu_home):
                    startActivity(new Intent(LikedBooks.this, BookSwipe2.class));
                    break;
                case (R.id.menu_my_profile):
                    startActivity(new Intent(LikedBooks.this, MyProfile.class));
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void fetchBooksFromUser() {
        dbRef = firebaseInstance.getReference("users");
        dbRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                ArrayList<String> bookIds = user.getBookIds();
                if (bookIds == null) {
                    bookIds = new ArrayList<>();
                }
                fetchBooks(bookIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchBooks(ArrayList<String> bookIds) {
        dbRef = firebaseInstance.getReference("books");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    if(bookIds.contains(book.getId())){
                        bList.add(book);
                    }
                }
                addImageFromDrawableToList();
                booksAdapter = new BooksAdapter(LikedBooks.this, bList);
                recyclerView.setAdapter(booksAdapter);
                booksAdapter.setClickListener(LikedBooks.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addImageFromDrawableToList() {
        for (int i = 0; i < bList.size(); i++) {
            String name = "b" + bList.get(i).getId();
            int drawable = getResources().getIdentifier(name,
                    "drawable", getPackageName());
            bList.get(i).setPicId(drawable);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        view.getContext().startActivity(new Intent(LikedBooks.this, Matches.class));
    }
}