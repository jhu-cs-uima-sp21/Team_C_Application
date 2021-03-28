package com.example.hopportunities;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<String> subjects;

    public User() {} //needed for db?

    public User(String firstName, String lastName, String email, String password, ArrayList<String> subjects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.subjects = subjects;
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

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }
}
