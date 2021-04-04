package com.example.hopportunities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hopportunities.CreateAccount;
import com.example.hopportunities.R;
import com.example.hopportunities.Student;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConfirmFragment extends Fragment {
    CreateAccount createAccount;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        createAccount = (CreateAccount) getActivity();
        // Inflate the layout for this fragment
        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
        dbref = mdbase.getReference();
        return inflater.inflate(R.layout.confirm, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.
        Button confirm = createAccount.findViewById(R.id.buttonConfirm);
        TextView name = createAccount.findViewById(R.id.confirmName);
        TextView grade = createAccount.findViewById(R.id.confirmGrade);
        TextView subs = createAccount.findViewById(R.id.confirmSubs);
        name.setText(null);
        grade.setText(null);
        subs.setText(null);
        name.setText("Name:" + createAccount.firstName + " " + createAccount.lastName);
        grade.setText("Level of education: " + createAccount.gradeEdu);
        subs.setText("Subjects: " + createAccount.subs.toString());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createAccount.student) {
                    //create and add student
                    Student newStudent = new Student(createAccount.firstName,createAccount.lastName,createAccount.userEmail, createAccount.gradeEdu, createAccount.subs,createAccount.userId);
                    dbref.child("students").child(newStudent.getId()).setValue(newStudent);
                } else {
                    Tutor newTutor = new Tutor(createAccount.firstName,createAccount.lastName,createAccount.userEmail,createAccount.subs,createAccount.avail,createAccount.bio,createAccount.userId, createAccount.gradeEdu);
                    dbref.child("tutors").child(newTutor.getId()).setValue(newTutor);
                }

                Intent intent = new Intent(createAccount.getBaseContext(), MainActivity.class);

                startActivity(intent);
            }
        });
    }
}
