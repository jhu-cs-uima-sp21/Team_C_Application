package com.example.hopportunities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class EditTutorFrag extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditAccount editAccount;
    private Boolean spinnerSelect = false;
    private Spinner spinner;

    ToggleButton math, science, english, lang, comp, history;
    EditText bio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_tutor, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "Edit Tutor onStart");

        editAccount = (EditAccount) getActivity();
        spinner = (Spinner) editAccount.findViewById(R.id.edu_level_spinner);
        if (spinner == null) {
            Log.d("spinner is null", "!!!!!");
        }
        List<String> grades = new ArrayList<>();
        grades.add("Select one");
        grades.add("Undergrad");
        grades.add("Graduate");
        grades.add("Other (Explain in Bio)");

        ArrayAdapter aa = new ArrayAdapter(editAccount.getBaseContext(),R.layout.support_simple_spinner_dropdown_item, grades);
        if (aa == null) {
            Log.d("aa is null", "!!!!!");
        }
        spinner.setAdapter(aa);
        String oldGrade = editAccount.tutorAcc.getGrade();
        spinner.setSelection(grades.indexOf(oldGrade));

        math = editAccount.findViewById(R.id.tbtn_math);
        science = editAccount.findViewById(R.id.tbtn_science);
        english = editAccount.findViewById(R.id.tbtn_english);
        lang = editAccount.findViewById(R.id.tbtn_lang);
        comp = editAccount.findViewById(R.id.tbtn_cs);
        history = editAccount.findViewById(R.id.tbtn_history);

        ArrayList<String> oldSubs = editAccount.tutorAcc.getSubjects();
        if (oldSubs.contains("math")) {
            math.setChecked(true);
        }
        if (oldSubs.contains("science")) {
            science.setChecked(true);
        }
        if (oldSubs.contains("english")) {
            english.setChecked(true);
        }
        if (oldSubs.contains("lang")) {
            lang.setChecked(true);
        }
        if (oldSubs.contains("comp")) {
            comp.setChecked(true);
        }
        if (oldSubs.contains("history")) {
            history.setChecked(true);
        }

        bio = editAccount.findViewById(R.id.bio);
        bio.setText(editAccount.tutorAcc.getBio());

        Button next = editAccount.findViewById(R.id.btn_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem().toString().equals("Select one")  ) {
                    Snackbar.make(v, "Please select a grade level.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> subs = new ArrayList<>();
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
                if (history.isChecked()) {
                    subs.add("history");
                }
                editAccount.bio = bio.getText().toString();
                editAccount.gradeEdu = spinner.getSelectedItem().toString();
                editAccount.subs = subs;
                FragmentTransaction ft = editAccount.fm.beginTransaction();
                EditTutorScheduleFrag sched = new EditTutorScheduleFrag();
                ft.replace(R.id.fragment_container, sched).addToBackStack(null).commit();
                return;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}