package com.example.binder.Entities;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String email;
    private String name;
    private String bio;
    private String dob;
    private ArrayList<String> preferences;
    private String gender;
    private ArrayList<Integer> bookIds;

    public User() {
    }

    public User(String email, String name, String bio, String dob, ArrayList<String> preferences, String gender) {
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.dob = dob;
        this.preferences = preferences;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<String> preferences) {
        this.preferences = preferences;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(ArrayList<Integer> bookIds) {
        this.bookIds = bookIds;
    }
}
