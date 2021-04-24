package com.example.hopportunities;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String grade;
    private ArrayList<String> subjects;

    public User() {} //needed for db?

    public User(String firstName, String lastName, String email, ArrayList<String> subjects,String id, String grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.subjects = subjects;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
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
