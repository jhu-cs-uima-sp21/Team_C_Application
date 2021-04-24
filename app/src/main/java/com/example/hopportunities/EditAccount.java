package com.example.hopportunities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class EditAccount extends AppCompatActivity {

    public String userId, userEmail, firstName, lastName, gradeEdu, bio;
    public Boolean isStudent; //true = student false = tutor
    public ArrayList<String> subs;
    public ArrayList<ArrayList<Boolean>> avail;
    public Tutor tutorAcc;
    public Student studentAcc;

    private FragmentTransaction ft;
    public FragmentManager fm;
    private Fragment editStudent;
    private Fragment editTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        isStudent = getIntent().getBooleanExtra("userType", false);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        if (ft == null) {
            Log.d("NULL", "NULL");
        }

        if (isStudent) {
            studentAcc = (Student) getIntent().getSerializableExtra("userStudent");
            userId = studentAcc.getId();
            userEmail = studentAcc.getEmail();
            firstName = studentAcc.getFirstName();
            lastName = studentAcc.getLastName();
            editStudent = new EditStudentFrag();
            ft.replace(R.id.fragment_container, editStudent).addToBackStack(null).commit();
        } else {
            tutorAcc = (Tutor) getIntent().getSerializableExtra("userTutor");
            userId = tutorAcc.getId();
            userEmail = tutorAcc.getEmail();
            firstName = tutorAcc.getFirstName();
            lastName = tutorAcc.getLastName();
            editTutor = new EditTutorFrag();
            ft.replace(R.id.fragment_container, editTutor).addToBackStack(null).commit();
        }
    }
}