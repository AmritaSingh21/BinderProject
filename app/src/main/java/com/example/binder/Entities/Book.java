package com.example.binder.Entities;

public class Book {
    private String title,author,genre,year,isbn,publisher,id, description;
    private double timeSpent;
    private int picId;

    public Book() {
    }

    public Book(String title, String author, String genre, String year,
                String isbn, String publisher, String id) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.isbn = isbn;
        this.publisher = publisher;
        this.id = id;
    }

    public Book(String title, String author, String genre, String year, String description, int picId) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.description = description;
        this.picId = picId;
    }

    public Book(String title, String author, String genre, String year, String id, String description) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.id = id;
        this.description = description;
    }

    public Book(String title, String author, String isbn, double time){
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

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

