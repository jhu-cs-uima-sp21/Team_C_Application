package com.example.hopportunities;

import java.io.Serializable;
import java.util.ArrayList;

public class Tutor extends User implements Serializable {
    private ArrayList<ArrayList<Boolean>> avail;
    private String bio;
    //private ArrayList<String> qualifications;

    public ArrayList<ArrayList<Boolean>> getAvail() {
        return avail;
    }

    public String getBio() {
        return bio;
    }

    //public ArrayList<String> getQualifications() {
    //    return qualifications;
   // }

    Tutor() {} //needed for db

    public Tutor(String firstName, String lastName, String email, ArrayList<String> subjects, ArrayList<ArrayList<Boolean>> avail, String bio,  String id, String grade) {
        super(firstName, lastName, email, subjects, id, grade);
        this.avail = avail;
        this.bio = bio;
        //this.qualifications = qualifications;

    }
}
