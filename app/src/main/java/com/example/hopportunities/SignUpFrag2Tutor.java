package com.example.hopportunities;

import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SignUpFrag2Tutor extends Fragment implements
        AdapterView.OnItemSelectedListener {
    private CreateAccount createAccount;
    private Boolean spinnerSelect = false;
    private Spinner spinner;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up_2_student, container, false);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "onStart");
        createAccount = (CreateAccount) getActivity();
        spinner = (Spinner) createAccount.findViewById(R.id.gradespinner);
        if (spinner == null) {
            Log.d("spinner is null", "!!!!!");
        }
        List<String> school = new ArrayList<>();
        school.add("Select one");
        school.add("Johns Hopkins University");
        school.add("Out of school");
        school.add("Other");

        ArrayAdapter aa = new ArrayAdapter(createAccount.getBaseContext(),R.layout.support_simple_spinner_dropdown_item, school);
        if (aa == null) {
            Log.d("aa is null", "!!!!!");
        }
        spinner.setAdapter(aa);
        Button next = createAccount.findViewById(R.id.sufs2Next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem().toString().equals("Select one")  ) {
                    Snackbar.make(v, "Please select a grade level.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> subs = new ArrayList<>();
                ToggleButton math = createAccount.findViewById(R.id.toggleButtonMath);
                ToggleButton science = createAccount.findViewById(R.id.toggleButtonScience);
                ToggleButton english = createAccount.findViewById(R.id.toggleButtonEnglish);
                ToggleButton lang = createAccount.findViewById(R.id.toggleButtonLanguage);
                ToggleButton comp = createAccount.findViewById(R.id.toggleButtonCompsci);
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
                createAccount.gradeEdu = spinner.getSelectedItem().toString();
                createAccount.subs = subs;
                FragmentTransaction ft = createAccount.fm.beginTransaction();


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
=======

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SignUpFrag2Tutor extends Fragment {



    public SignUpFrag2Tutor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_frag2_tutor, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
>>>>>>> main
