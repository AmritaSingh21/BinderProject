package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

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
}