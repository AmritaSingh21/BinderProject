package com.example.binder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
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

public class BookSwipe2 extends AppCompatActivity {

    private SwipeDeck cardStack;
    private ArrayList<Book> books = new ArrayList<>();
    String userId;

    private FirebaseAuth auth;
    private DatabaseReference dbRef;
    private FirebaseDatabase firebaseInstance;
    private ImageButton btnMessage;

    SwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_swipe2);

        auth = FirebaseAuth.getInstance();
        firebaseInstance = FirebaseDatabase.getInstance();

        // fetching books from firebase
        fetchBooks();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

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
                addUserToBook(books.get(position));

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

        btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookSwipe2.this,chatsActivity.class));
            }
        });

    }

    private void addUserToBook(Book book) {
        dbRef = firebaseInstance.getReference("books");
        userId = auth.getCurrentUser().getUid();
        dbRef.child(book.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    boolean flag = true;
                    Book dbBook = task.getResult().getValue(Book.class);
                    ArrayList<String> userIds = dbBook.getUserIds();
                    if (userIds == null) {
                        userIds = new ArrayList<>();
                    } else{
                        for (String uId: userIds) {
                            if(uId.equalsIgnoreCase(userId)){
                                flag = false;
                            }
                        }
                    }
                    if(flag){
                        userIds.add(userId);
                        dbRef.child(book.getId()).child("userIds").setValue(userIds);
                        addBookToUser(book);
                    }
                }
            }
        });
    }

    private void addBookToUser(Book book) {
        dbRef = firebaseInstance.getReference("users");
        userId = auth.getCurrentUser().getUid();
        dbRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    User user = task.getResult().getValue(User.class);
                    ArrayList<String> bookIds = user.getBookIds();
                    if (bookIds == null) {
                        bookIds = new ArrayList<>();
                    }
                    bookIds.add(book.getId());
                    dbRef.child(userId).child("bookIds").setValue(bookIds);
                }
            }
        });
    }

    private void fetchBooks() {
        dbRef = firebaseInstance.getReference("books");

        dbRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        Book book = dataSnapshot.getValue(Book.class);
                        books.add(book);
                    }
                    addImageFromDrawableToList();
                    adapter = new SwipeAdapter(books, BookSwipe2.this);
                    cardStack.setAdapter(adapter);
                }
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