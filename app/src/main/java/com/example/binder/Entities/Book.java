package com.example.binder.Entities;

public class Book {
    private String title,author,genre,year,isbn,publisher,id;
    private double timeSpent;

    public Book(String title, String author, String genre, String year,
                 String isbn,String publisher,String id) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.isbn = isbn;
        this.publisher = publisher;
        this.id = id;
    }

    public Book(String title, String author,String isbn, double time){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        timeSpent = time;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }


    public String getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getId() {
        return id;
    }

    public double getTime() {
        return timeSpent;
    }


}

