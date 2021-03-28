package com.example.hopportunities;

import java.util.ArrayList;

public class Student extends User {
    private String grade;


    Student () {} //needed for db?

    public Student(String firstName, String lastName, String email, String password, String grade, ArrayList<String> subjects) {
        super(firstName, lastName, email, password,subjects);
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}
