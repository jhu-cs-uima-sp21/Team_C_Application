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
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class EditStudentFrag extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditAccount editAccount;
    private Boolean spinnerSelect = false;
    private Spinner spinner;

    ToggleButton math, science, english, lang, comp, history;

    public EditStudentFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_student, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("Content Fragment", "Edit Student onStart");
        editAccount = (EditAccount) getActivity();
        spinner = (Spinner) editAccount.findViewById(R.id.gradespinner);
        if (spinner == null) {
            Log.d("spinner is null", "!!!!!");
        }
        List<String> grades = new ArrayList<>();
        grades.add("Select one");
        grades.add("Below elementary school");
        grades.add("Elementary school");
        grades.add("Middle school");
        grades.add("High school");
        grades.add("Undergrad");

        ArrayAdapter aa = new ArrayAdapter(editAccount.getBaseContext(),R.layout.support_simple_spinner_dropdown_item, grades);
        if (aa == null) {
            Log.d("aa is null", "!!!!!");
        }
        spinner.setAdapter(aa);

        String oldGrade = editAccount.studentAcc.getGrade();
        spinner.setSelection(grades.indexOf(oldGrade));

        math = editAccount.findViewById(R.id.toggleButtonMath);
        science = editAccount.findViewById(R.id.toggleButtonScience);
        english = editAccount.findViewById(R.id.toggleButtonEnglish);
        lang = editAccount.findViewById(R.id.toggleButtonLanguage);
        comp = editAccount.findViewById(R.id.toggleButtonCompsci);
        history = editAccount.findViewById(R.id.toggleButtonHistory);

        ArrayList<String> oldSubs = editAccount.studentAcc.getSubjects();

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

        Button next = editAccount.findViewById(R.id.sufs2Next);
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
                editAccount.gradeEdu = spinner.getSelectedItem().toString();
                editAccount.subs = subs;
                FragmentTransaction ft = editAccount.fm.beginTransaction();
                EditConfirmFrag conf = new EditConfirmFrag();
                ft.replace(R.id.fragment_container, conf).addToBackStack(null).commit();
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