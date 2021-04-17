package com.example.hopportunities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

        TextView name = createAccount.findViewById(R.id.cName);
        TextView grade = createAccount.findViewById(R.id.cGrade);
        TextView subs = createAccount.findViewById(R.id.cSub);
        TextView bio = createAccount.findViewById(R.id.cBio);
        TextView avail = createAccount.findViewById(R.id.cAvail);

        bio.setText(null);
        avail.setText(null);

        String userName = createAccount.firstName + " " + createAccount.lastName;
        String userGrade = createAccount.gradeEdu;
        StringBuilder userSubs = new StringBuilder();

        for (int i = 0; i < createAccount.subs.size(); i++) {
            String s = createAccount.subs.get(i);
            userSubs.append(s.substring(0,1).toUpperCase());
            userSubs.append(s.substring(1));
            if (i != createAccount.subs.size() - 1) {
                userSubs.append(", ");
            }
        }

        name.setText(userName);
        grade.setText(userGrade);
        subs.setText(userSubs.toString());

        if (createAccount.student) {
            TextView tv_bio = createAccount.findViewById(R.id.bio_tv);
            TextView tv_avail = createAccount.findViewById(R.id.avail);
            tv_bio.setVisibility(View.GONE);
            tv_avail.setVisibility(View.GONE);
        } else {
            String userBio = createAccount.bio;
            StringBuilder userAvail = new StringBuilder();
            String userAvailFormatted = buildScheduleString(userAvail, createAccount.avail);

            bio.setText(userBio);
            avail.setText(userAvailFormatted);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createAccount.student) {
                    //create and add student
                    Student newStudent = new Student(createAccount.firstName,createAccount.lastName,
                            createAccount.userEmail, createAccount.gradeEdu, createAccount.subs,createAccount.userId);
                    dbref.child("students").child(newStudent.getId()).setValue(newStudent);
                } else {
                    Tutor newTutor = new Tutor(createAccount.firstName,createAccount.lastName,
                            createAccount.userEmail,createAccount.subs,createAccount.avail,createAccount.bio,
                            createAccount.userId, createAccount.gradeEdu);
                    dbref.child("tutors").child(newTutor.getId()).setValue(newTutor);
                }

                Intent intent = new Intent(createAccount.getBaseContext(), MainActivity.class);

                startActivity(intent);
            }
        });
    }

    private String buildScheduleString(StringBuilder userAvail, ArrayList<ArrayList<Boolean>> schedule) {
        String formattedSchedule = "";

        for (int i = 0; i < schedule.size(); i++) {
            if (!schedule.get(i).get(0) && !schedule.get(i).get(1) && !schedule.get(i).get(2)) {
                continue;
            }
            switch(i) {
                case 0:
                    userAvail.append("Monday ");
                    break;
                case 1:
                    userAvail.append("Tuesday ");
                    break;
                case 2:
                    userAvail.append("Wednesday ");
                    break;
                case 3:
                    userAvail.append("Thursday ");
                    break;
                case 4:
                    userAvail.append("Friday ");
                    break;
                case 5:
                    userAvail.append("Saturday ");
                    break;
                case 6:
                    userAvail.append("Sunday ");
                    break;
            }
            if (schedule.get(i).get(0)) {
                userAvail.append("morning ");
            }
            if (schedule.get(i).get(1)) {
                userAvail.append("afternoon ");
            }
            if (schedule.get(i).get(2)) {
                userAvail.append("evening ");
            }
            if (i != schedule.size() - 1) {
                userAvail.append("/ ");
            }
        }

        formattedSchedule = userAvail.toString();
        return formattedSchedule;
    }
}
