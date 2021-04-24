package com.example.hopportunities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hopportunities.MainActivity;
import com.example.hopportunities.R;
import com.example.hopportunities.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private MainActivity mainActivity;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    private String id;
    private Boolean isStudent;

    private Tutor tutor;
    private Student student;

    private TextView name, type, grade, subjects, bio, avail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity = (MainActivity) getActivity();
        name = mainActivity.findViewById(R.id.userName);
        type = mainActivity.findViewById(R.id.userType);
        grade = mainActivity.findViewById(R.id.userGrade);
        subjects = mainActivity.findViewById(R.id.userSubjects);
        bio = mainActivity.findViewById(R.id.userBio);
        avail = mainActivity.findViewById(R.id.userAvail);

        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
        dbref = mdbase.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Log.d("Error", "Failed to find user");
            return;
        }

        id = user.getUid();
        findUser(id, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Button editProfile = mainActivity.findViewById(R.id.editProfile);

                editProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), EditAccount.class);
                        intent.putExtra("userType", isStudent);
                        if (isStudent) {
                            intent.putExtra("userStudent", student);
                        } else {
                            intent.putExtra("userTutor", tutor);
                        }
                        startActivity(intent);
                    }
                });
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

        Button logout = mainActivity.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    private void findUser(String id, final OnGetDataListener listener) {
        listener.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> tutors = snapshot.child("tutors").getChildren();
                for (DataSnapshot tutor : tutors) {
                    Tutor t = tutor.getValue(Tutor.class);
                    if (t.getId().equals(id)) {
                        isStudent = false;
                        buildTutorProfile(t);
                    }
                }
                Iterable<DataSnapshot> students = snapshot.child("students").getChildren();
                for (DataSnapshot student : students) {
                    Student s = student.getValue(Student.class);
                    if (s.getId().equals(id)) {
                        isStudent = true;
                        buildStudentProfile(s);
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

            name.setText(tName);
            type.setText(tType);
            grade.setText(tGrade);
            subjects.setText(tSubs);
            bio.setText(tBio);
            avail.setText(tAvailFormatted);
            tutor = t;
        }
    }

    private void buildStudentProfile(Student s) {
        if (s != null) {
            String sName = s.getFirstName() + " " + s.getLastName();
            String sType = "Student";
            String sGrade = s.getGrade();
            StringBuilder sSubs = new StringBuilder();
            ArrayList<String> subList = s.getSubjects();
            for (int i = 0; i < subList.size(); i++) {
                String subj = subList.get(i);
                sSubs.append(subj.substring(0,1).toUpperCase());
                sSubs.append(subj.substring(1));
                if (i != subList.size() - 1) {
                    sSubs.append(", ");
                }
            }

            name.setText(sName);
            type.setText(sType);
            grade.setText(sGrade);
            subjects.setText(sSubs);

            TextView tv_bio = mainActivity.findViewById(R.id.tv_bio);
            TextView tv_avail = mainActivity.findViewById(R.id.tv_avail);
            tv_bio.setVisibility(View.GONE);
            tv_avail.setVisibility(View.GONE);
            bio.setText(null);
            avail.setText(null);
            student = s;
        }
    }

    public interface OnGetDataListener {
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
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