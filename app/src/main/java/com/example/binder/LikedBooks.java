package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.binder.Adapters.BooksAdapter;
import com.example.binder.Entities.Book;

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
    }

    @Override
    public void onItemClick(View view, int position) {
        view.getContext().startActivity(new Intent(LikedBooks.this,Matches.class));
    }
}