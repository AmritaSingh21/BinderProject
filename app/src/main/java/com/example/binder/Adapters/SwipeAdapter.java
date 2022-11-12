package com.example.binder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.binder.Entities.Book;
import com.example.binder.R;

import java.util.ArrayList;

public class SwipeAdapter extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<Book> books;
    private Context context;

    // on below line we have created constructor for our variables.
    public SwipeAdapter(ArrayList<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // in get view method we are inflating our layout on below line.
        View v = convertView;
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_swipe_item, parent, false);
        }
        // on below line we are initializing our variables and setting data to our variables.
        ((TextView) v.findViewById(R.id.title)).setText(books.get(position).getTitle());
        ((TextView) v.findViewById(R.id.author)).setText(books.get(position).getAuthor());
        ((TextView) v.findViewById(R.id.genre)).setText(books.get(position).getGenre());
        ((TextView) v.findViewById(R.id.description)).setText(books.get(position).getDescription());
        ((ImageView) v.findViewById(R.id.bookPic)).setImageResource(books.get(position).getPicId());
        return v;
    }
}