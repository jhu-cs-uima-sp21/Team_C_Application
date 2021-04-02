package com.example.hopportunities;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private ArrayList<String> subjects;

    public User() {} //needed for db?

    public User(String firstName, String lastName, String email, ArrayList<String> subjects,String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.subjects = subjects;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }



    public ArrayList<String> getSubjects() {
        return subjects;
    }
}
