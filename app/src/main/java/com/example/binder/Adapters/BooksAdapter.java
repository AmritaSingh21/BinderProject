package com.example.binder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binder.Entities.Book;
import com.example.binder.R;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    List<Book> booksList;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    public BooksAdapter(Context context,List<Book> booksList){
        inflater = LayoutInflater.from(context);
        this.booksList = booksList;

    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.liked_books_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        Book book = booksList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookGenre.setText(book.getGenre()+" | ");
        holder.bookYear.setText(book.getYear());
        holder.bookImage.setImageResource(R.drawable.book_cover_2);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{
        TextView bookTitle,bookAuthor,bookGenre,bookYear;
        ImageView bookImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.uAge);
            bookAuthor = itemView.findViewById(R.id.uName);
            bookGenre = itemView.findViewById(R.id.book_genre);
            bookYear = itemView.findViewById(R.id.book_year);
            bookImage = itemView.findViewById(R.id.uImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener!=null)
                itemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }


}
