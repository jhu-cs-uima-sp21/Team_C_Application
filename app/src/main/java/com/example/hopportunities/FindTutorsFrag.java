package com.example.hopportunities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FindTutorsFrag extends Fragment {
    private MainActivity mainActivity;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_tutors, container, false);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "onStart");
        mainActivity = (MainActivity) getActivity();
        Button next = mainActivity.findViewById(R.id.buttonFind);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> subs = new ArrayList<>();
                ToggleButton math = mainActivity.findViewById(R.id.mathFilltTog);
                ToggleButton science = mainActivity.findViewById(R.id.scienceFiltTog);
                ToggleButton english = mainActivity.findViewById(R.id.englishFiltTog);
                ToggleButton lang = mainActivity.findViewById(R.id.langFiltTog);
                ToggleButton comp = mainActivity.findViewById(R.id.compFiltTog);
                ToggleButton history = mainActivity.findViewById(R.id.historyFiltTog);
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
                Intent intent = new Intent(mainActivity.getBaseContext(), FindTutorsActivity.class);


                intent.putExtra("subs", subs);
                startActivity(intent);


            }
        });


    }
}
