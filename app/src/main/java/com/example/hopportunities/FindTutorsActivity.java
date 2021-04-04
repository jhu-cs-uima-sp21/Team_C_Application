package com.example.hopportunities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindTutorsActivity extends AppCompatActivity {
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    private String userId;
    private List<Tutor> tutors;
    private static final String TAG = "dbref: ";
    protected TutorAdapter aa;
    ArrayList<String> subs;

    public void filter(Tutor t, List<Tutor> tList){
        Boolean fits = true;
        for(String s : subs) {
            if (!t.getSubjects().contains(s)) {
                fits = false;

            }
        }
        if (fits) {
            tList.add(t);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutors);

        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
        dbref = mdbase.getReference();
        tutors = new ArrayList<>();
        aa = new TutorAdapter(this,R.layout.tutor_layout,tutors);
        ListView lv = findViewById(R.id.find_tutors_list);
        lv.setAdapter(aa);
        subs = getIntent().getStringArrayListExtra("subs");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Tutor count: " + snapshot.child("tutors").getChildrenCount());

                // need to recreate the mItems list somehow
                // another way is to use a FirebaseRecyclerView - see Sample Database code

                tutors.clear();
                Iterable<DataSnapshot> clients = snapshot.child("tutors").getChildren();
                for (DataSnapshot pair : clients) {
                    Tutor t = pair.getValue(Tutor.class);

                    filter(t,tutors);
                }
                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        /*
        ArrayList<String> subjects = new ArrayList<>();
        subjects.add("math");
        ArrayList<ArrayList<Boolean>> avail = new ArrayList<>();
        avail.add(new ArrayList<>());
        tutors.add(new Tutor("Bob", "smith", "a@a.com",subjects,avail,"hi","id" ,"grad" ));
        aa.notifyDataSetChanged(); */
    }
}