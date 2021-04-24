package com.example.hopportunities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopportunities.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewTutor extends AppCompatActivity {
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    private String id;
    private TextView name, type, grade, subjects, bio, avail;
    private Tutor tutor;
    private ViewTutor viewTutor = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tutor);
    }
    @Override
    public void onResume() {

        super.onResume();
        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
        dbref = mdbase.getReference();

        id = getIntent().getStringExtra("id");
        findUser(id, new ProfileFragment.OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onStart() {
                Log.d("onStart", "Started");
            }

            @Override
            public void onFailure() {
                Log.d("onFailure", "Failed");
            }
        });
        Button email = findViewById(R.id.emailTutor);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = "Hello " + tutor.getFirstName() + " " + tutor.getLastName() + ", I found you on the Hopportunities app and I would like to find a time to meet.\nThanks!"; //fill out


                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "Hopportunities"+ "&body=" + body + "&to=" + tutor.getEmail());
                Intent i = new Intent(Intent.ACTION_VIEW,data);
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                String stuId = currentFirebaseUser.getUid();

                dbref.child("contacts").child(stuId + "-" + tutor.getId()).setValue(stuId + "-" + tutor.getId());

                try {
                    startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(viewTutor, "There are no email clients installed.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void findUser(String id, final ProfileFragment.OnGetDataListener listener) {
        listener.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> tutors = snapshot.child("tutors").getChildren();
                for (DataSnapshot tutor : tutors) {
                    Tutor t = tutor.getValue(Tutor.class);
                    if (t.getId().equals(id)) {
                        //isStudent = false;
                        buildTutorProfile(t);
                    }
                }

                listener.onSuccess(snapshot);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
                listener.onFailure();
            }
        });
    }

    private void buildTutorProfile(Tutor t) {
        if (t != null) {
            tutor = t;
            String tName = t.getFirstName() + " " + t.getLastName();
            String tType = "Tutor";
            String tGrade = t.getGrade();

            StringBuilder tSubs = new StringBuilder();
            ArrayList<String> subList = t.getSubjects();
            for (int i = 0; i < subList.size(); i++) {
                String s = subList.get(i);
                tSubs.append(s.substring(0,1).toUpperCase());
                tSubs.append(s.substring(1));
                if (i != subList.size() - 1) {
                    tSubs.append(", ");
                }
            }

            String tBio = t.getBio();

            StringBuilder tAvail = new StringBuilder();
            String tAvailFormatted = buildScheduleString(tAvail, t.getAvail());

            name = findViewById(R.id.userName);
            type = findViewById(R.id.userType);
            grade = findViewById(R.id.userGrade);
            subjects = findViewById(R.id.userSubjects);
            bio = findViewById(R.id.userBio);
            avail = findViewById(R.id.userAvail);

            name.setText(tName);
            type.setText(tType);
            grade.setText(tGrade);
            subjects.setText(tSubs);
            bio.setText(tBio);
            avail.setText(tAvailFormatted);
            //tutor = t;
        }

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
