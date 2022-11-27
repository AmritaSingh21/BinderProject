package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.binder.Adapters.SwipeAdapter;
import com.example.binder.Entities.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookSwipe2 extends AppCompatActivity {

    private SwipeDeck cardStack;
    private ArrayList<Book> books = new ArrayList<>();

    private FirebaseAuth auth;
    private DatabaseReference dbRef;
    private FirebaseDatabase firebaseInstance;

    SwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_swipe2);

        auth = FirebaseAuth.getInstance();

        // fetching books from firebase
        fetchBooks();
        Log.d("testing2", String.valueOf(books.size()));
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        // on below line we are initializing our array list and swipe deck.
//        books = new ArrayList<>();
//
//        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
//        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));
//        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
//        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));
//        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
//        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));
//        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
//        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));

        // on below line we are creating a variable for our adapter class and passing array list to it.
//        final SwipeAdapter adapter = new SwipeAdapter(books, this);

        // on below line we are setting adapter to our card stack.
//        cardStack.setAdapter(adapter);

        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
//                Toast.makeText(BookSwipe2.this, "Book Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(BookSwipe2.this, "Book Liked!", Toast.LENGTH_SHORT).show();
                addBookToLiked(books.get(position));
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                //Toast.makeText(BookSwipe2.this, "No more books present", Toast.LENGTH_SHORT).show();
                cardStack.setAdapter(adapter);
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });

        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

        bottom_menu.setSelectedItemId(R.id.menu_home);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case (R.id.menu_searchBook):
                    startActivity(new Intent(BookSwipe2.this, SearchBooks.class));
                    break;
                case (R.id.menu_likedBooks):
                    startActivity(new Intent(BookSwipe2.this, LikedBooks.class));
                    break;
                case (R.id.menu_my_profile):
                    startActivity(new Intent(BookSwipe2.this, MyProfile.class));
                    break;
                default:
                    break;
            }
            return true;
        });

    }

    private void addBookToLiked(Book book) {
    }

    private void fetchBooks() {
        firebaseInstance = FirebaseDatabase.getInstance();
        dbRef = firebaseInstance.getReference("books");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    books.add(book);
                    Log.d("testing", book.getTitle());
                }
                addImageFromDrawableToList();
                adapter = new SwipeAdapter(books, BookSwipe2.this);
                cardStack.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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