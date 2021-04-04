package com.example.hopportunities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FindTutor1 extends Fragment implements
        AdapterView.OnItemSelectedListener {

    private CreateAccount createAccount;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_tutors_1, container, false);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "onStart");
        Button next = createAccount.findViewById(R.id.find_tutor_submit);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> subs = new ArrayList<>();
                ToggleButton math = createAccount.findViewById(R.id.find_tutor_math);
                ToggleButton science = createAccount.findViewById(R.id.find_tutor_science);
                ToggleButton english = createAccount.findViewById(R.id.find_tutor_english);
                ToggleButton lang = createAccount.findViewById(R.id.find_tutor_lang);
                ToggleButton comp = createAccount.findViewById(R.id.find_tutor_compsci);
                if (math.isChecked()) {
                    subs.add("math");
                }
                if (science.isChecked()) {
                    subs.add("science");
                }
                if (english.isChecked()) {
                    subs.add("english");
                }
                if (lang.isChecked()) {
                    subs.add("lang");
                }
                if (comp.isChecked()) {
                    subs.add("comp");
                }
                EditText bio = createAccount.findViewById(R.id.bio);
                createAccount.bio = bio.getText().toString();
                createAccount.subs = subs;
                FragmentTransaction ft = createAccount.fm.beginTransaction();
                ScheduleFrag sched = new ScheduleFrag();
                ft.replace(R.id.fragment_container, sched).addToBackStack(null).commit();
                return;


            }
        });
    }