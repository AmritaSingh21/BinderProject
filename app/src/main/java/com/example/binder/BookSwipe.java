package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.binder.listeners.CustomTouchListener;

import java.util.Objects;

public class BookSwipe extends AppCompatActivity {

    TextView title, author, description, year, genre;
    ImageView bookImage;

    private static String TAG = "BookSwipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_swipe);

        title = findViewById(R.id.bookTitle);
        author = findViewById(R.id.bookAuthor);
        description = findViewById(R.id.txtDescription);
        year = findViewById(R.id.txtYear);
        genre = findViewById(R.id.txtGeneres);
        bookImage = findViewById(R.id.bookPic);

        LinearLayout layout = findViewById(R.id.swipeLayout2);
        bookImage.setOnTouchListener(new CustomTouchListener(BookSwipe.this){
            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
            }

            @Override
            public void onSwipeDown() {
                super.onSwipeDown();
            }

            @Override
            public void onSwipeLeft() {
                Log.d(TAG, "swiped");
                super.onSwipeLeft();
                Drawable book2 = ResourcesCompat.getDrawable(
                        getResources(), R.drawable.book2, getTheme());
                Objects.requireNonNull(book2);
                if (bookImage.getDrawable().getConstantState()
                        != book2.getConstantState()) {
                    bookImage.setImageResource(R.drawable.book2);
                    title.setText("Circe");
                    author.setText("Madeline Miller");
                } else {
                    bookImage.setImageResource(R.drawable.book);
                    title.setText("Midnight Library");
                    author.setText("Matt Haig");
                }
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
            }
        });
    }
}