package com.example.hopportunities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditConfirmFrag extends Fragment {

    EditAccount editAccount;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        editAccount = (EditAccount) getActivity();
        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
        dbref = mdbase.getReference();
        return inflater.inflate(R.layout.fragment_edit_confirm, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "onStart");

        // Apply any required UI change now that the Fragment is visible.
        Button confirm = editAccount.findViewById(R.id.buttonConfirm);

        TextView name = editAccount.findViewById(R.id.cName);
        TextView grade = editAccount.findViewById(R.id.cGrade);
        TextView subs = editAccount.findViewById(R.id.cSub);
        TextView bio = editAccount.findViewById(R.id.cBio);
        TextView avail = editAccount.findViewById(R.id.cAvail);

        bio.setText(null);
        avail.setText(null);

        String userName = editAccount.firstName + " " + editAccount.lastName;
        String userGrade = editAccount.gradeEdu;
        StringBuilder userSubs = new StringBuilder();

        for (int i = 0; i < editAccount.subs.size(); i++) {
            String s = editAccount.subs.get(i);
            userSubs.append(s.substring(0,1).toUpperCase());
            userSubs.append(s.substring(1));
            if (i != editAccount.subs.size() - 1) {
                userSubs.append(", ");
            }
        }

        name.setText(userName);
        grade.setText(userGrade);
        subs.setText(userSubs.toString());

        if (editAccount.isStudent) {
            TextView tv_bio = editAccount.findViewById(R.id.bio_tv);
            TextView tv_avail = editAccount.findViewById(R.id.avail);
            tv_bio.setVisibility(View.GONE);
            tv_avail.setVisibility(View.GONE);
        } else {
            String userBio = editAccount.bio;
            StringBuilder userAvail = new StringBuilder();
            String userAvailFormatted = buildScheduleString(userAvail, editAccount.avail);

            bio.setText(userBio);
            avail.setText(userAvailFormatted);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editAccount.isStudent) {
                    dbref.child("students/" + editAccount.userId + "/grade").setValue(editAccount.gradeEdu);
                    dbref.child("students/" + editAccount.userId + "/subjects").setValue(editAccount.subs);
                } else {
                    dbref.child("tutors/" + editAccount.userId + "/grade").setValue(editAccount.gradeEdu);
                    dbref.child("tutors/" + editAccount.userId + "/subjects").setValue(editAccount.subs);
                    dbref.child("tutors/" + editAccount.userId + "/bio").setValue(editAccount.bio);
                    dbref.child("tutors/" + editAccount.userId + "/avail").setValue(editAccount.avail);
                }

                Intent intent = new Intent(editAccount.getBaseContext(), MainActivity.class);

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