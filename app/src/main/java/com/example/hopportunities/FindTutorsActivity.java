package com.example.hopportunities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FindTutorsActivity extends AppCompatActivity {
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    private String userId;
    private List<Tutor> tutors;
    private static final String TAG = "dbref: ";
    protected TutorAdapter aa;
    private FindTutorsActivity fta;
    ArrayList<String> subs;
    ArrayList<ArrayList<Boolean>> avail;
    ArrayList<Boolean> tempAvail;
    FindTutorsActivity thisAct;

    //if any overlap in scheds, return true
    public Boolean filter_avail(Tutor t, List<Tutor> tList){

        ArrayList<ArrayList<Boolean>> tAvail = t.getAvail();
        for (int i = 0; i < 7; i++) {
            for(int j = 0; j < 3; j++) {
                if (avail.get(i).get(j) == tAvail.get(i).get(j) && avail.get(i).get(j) == true) {
                    return true;
                }
            }
        }
        return false;
    }


    public Boolean filter_sub(Tutor t, List<Tutor> tList){
        Boolean fits = true;
        for(String s : subs) {
            if (!t.getSubjects().contains(s)) {
                fits = false;

            }
        }
        return fits;
        //if (fits) {
        //    tList.add(t);
        //}

    }


    //I had to flatten the nested arraylist to pass in bundle, this "unflattens" it
    //actually nevermind, will probably get rid of this
    /*
    private ArrayList<ArrayList<Boolean>> toCorrectAvailFormat(boolean[] bWrong) {
        ArrayList<ArrayList<Boolean>> correctAvail = new ArrayList<>();
        correctAvail.add(new ArrayList<>()); //one list for each day, each has one bool for morning, evening, night
        correctAvail.add(new ArrayList<>());
        correctAvail.add(new ArrayList<>());
        correctAvail.add(new ArrayList<>());
        correctAvail.add(new ArrayList<>());
        correctAvail.add(new ArrayList<>());
        correctAvail.add(new ArrayList<>());
        int i = 0;
        for (Boolean b: bWrong) {
            correctAvail.get(i / 3).add(b);
        }
        return correctAvail;
    }

     */

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
        fta = this;
        //avail = toCorrectAvailFormat( getIntent().getBooleanArrayExtra("avail"));
        avail = (ArrayList<ArrayList<Boolean>>) getIntent().getSerializableExtra("avail");
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

                    if (filter_sub(t,tutors) && filter_avail(t,tutors)) {
                        tutors.add(t);
                    }
                }
                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        thisAct = this;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tutor t = tutors.get(position);



                AlertDialog.Builder builder = new AlertDialog.Builder(thisAct);
                builder.setTitle("Contact");
                builder.setMessage("Email this tutor to set up meeting?");

                // add the buttons


                builder.setPositiveButton("View Profile", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(fta, ViewTutor.class);
                        intent.putExtra("id", t.getId());
                        startActivity(intent);

                    }
                } );

                builder.setNegativeButton("Cancel", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
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