package com.example.hopportunities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class EditAccount extends FragmentActivity {

    public String userId, userEmail, firstName, lastName, gradeEdu, bio;
    public Boolean isStudent; //true = student false = tutor
    public ArrayList<String> subs;
    public ArrayList<ArrayList<Boolean>> avail;
    public Tutor tutorAcc;
    public Student studentAcc;

    private Fragment signUp1;
    private FragmentTransaction ft;
    public FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        isStudent = getIntent().getBooleanExtra("userType", false);

        if (isStudent) {
            studentAcc = (Student) getIntent().getSerializableExtra("userStudent");

            userId = studentAcc.getId();
            userEmail = studentAcc.getEmail();
        } else {
            tutorAcc = (Tutor) getIntent().getSerializableExtra("userTutor");

            userId = tutorAcc.getId();
            userEmail = tutorAcc.getEmail();
        }

        signUp1 = new SignUpFrag1();
        fm = getSupportFragmentManager();
        ft = getSupportFragmentManager().beginTransaction();
        if (ft == null) {
            Log.d("NULL", "NULL");
        }
        ft.replace(R.id.fragment_container_edit, signUp1).addToBackStack(null).commit();
    }
}