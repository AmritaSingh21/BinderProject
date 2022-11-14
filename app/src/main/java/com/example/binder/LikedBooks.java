package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.binder.Adapters.BooksAdapter;
import com.example.binder.Entities.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LikedBooks extends AppCompatActivity implements BooksAdapter.ItemClickListener{

    List<Book> bList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BooksAdapter booksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_books);

        bList = new ArrayList<>();
        Book b = new Book("Harry Potter","JK Rowlins","Fiction","2002","83752662893","AB Pubs","1");
        bList.add(b);

        recyclerView = findViewById(R.id.likedBooksList);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        booksAdapter = new BooksAdapter(this,bList);
        booksAdapter.setClickListener(this);
        recyclerView.setAdapter(booksAdapter);

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

        bottom_menu.setSelectedItemId(R.id.menu_likedBooks);
        bottom_menu.setOnItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case(R.id.menu_searchBook):
                    startActivity(new Intent(LikedBooks.this,SearchBooks.class));
                    break;
                case (R.id.menu_home):
                    startActivity(new Intent(LikedBooks.this,BookSwipe2.class));
                    break;
                case (R.id.menu_my_profile):
                    startActivity(new Intent(LikedBooks.this,MyProfile.class));
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        view.getContext().startActivity(new Intent(LikedBooks.this,Matches.class));
    }
}