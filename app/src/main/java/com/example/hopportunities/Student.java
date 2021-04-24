package com.example.hopportunities;

import java.io.Serializable;
import java.util.ArrayList;

public class Student extends User implements Serializable {
    Student () {} //needed for db?

    public Student(String firstName, String lastName, String email, String grade, ArrayList<String> subjects, String id) {
        super(firstName, lastName, email,subjects, id, grade);
        //this.grade = grade;
    }
}
