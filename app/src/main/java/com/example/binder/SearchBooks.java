package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.binder.Adapters.BooksAdapter;
import com.example.binder.Entities.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchBooks extends AppCompatActivity {
    EditText inpKey;
    DatabaseReference reference;
    RecyclerView recyclerView;
    BooksAdapter adapter;
    List<Book> books;
    FloatingActionButton btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        inpKey = findViewById(R.id.book_search);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.searchedBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference("books");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = inpKey.getText().toString();
                books = new ArrayList<>();
                if(!key.isEmpty()){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                Book book = snapshot1.getValue(Book.class);
                                if(book.getTitle().toLowerCase().contains(key.toLowerCase())){
                                    books.add(book);
                                }
                            }
                            if((books.size() == 0))
                                Toast.makeText(SearchBooks.this,"No books found",Toast.LENGTH_SHORT).show();
                            else {
                                addImageFromDrawableToList();
                                adapter = new BooksAdapter(getApplicationContext(), books);
                                recyclerView.setAdapter(adapter);
                                inpKey.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else {
                    Toast.makeText(SearchBooks.this,"Please enter a title",Toast.LENGTH_SHORT).show();

                }
            }
        });

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

        bottom_menu.setSelectedItemId(R.id.menu_searchBook);
        bottom_menu.setOnItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case(R.id.menu_home):
                    startActivity(new Intent(SearchBooks.this,BookSwipe2.class));
                    break;
                case (R.id.menu_likedBooks):
                    startActivity(new Intent(SearchBooks.this,LikedBooks.class));
                    break;
                case (R.id.menu_my_profile):
                    startActivity(new Intent(SearchBooks.this,MyProfile.class));
                    break;
                default:
                    break;
            }
            return true;
        });
    }
    private void addImageFromDrawableToList() {
        for (int i = 0; i < books.size(); i++) {
            String name = "b" + books.get(i).getId();
            int drawable = getResources().getIdentifier(name,
                    "drawable", getPackageName());
            books.get(i).setPicId(drawable);
        }
    }
}