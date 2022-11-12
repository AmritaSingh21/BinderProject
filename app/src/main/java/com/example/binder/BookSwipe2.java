package com.example.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.binder.Adapters.SwipeAdapter;
import com.example.binder.Entities.Book;

import java.util.ArrayList;

public class BookSwipe2 extends AppCompatActivity {

    private SwipeDeck cardStack;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_swipe2);

        // on below line we are initializing our array list and swipe deck.
        books = new ArrayList<>();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));
        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));
        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));
        books.add(new Book("Midnight Library", "Matt Haig", "Fic", "2019", "Andbwehgjfd", R.drawable.book));
        books.add(new Book("Circe", "Madeline Miller", "Greek Myth", "2019", "dsfhsdkjhkjsd", R.drawable.book2));
        // on below line we are creating a variable for our adapter class and passing array list to it.
        final SwipeAdapter adapter = new SwipeAdapter(books, this);

        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);

        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(BookSwipe2.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(BookSwipe2.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(BookSwipe2.this, "No more courses present", Toast.LENGTH_SHORT).show();
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

    }
}