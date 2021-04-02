package com.example.hopportunities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hopportunities.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

public class SignUpFrag1 extends Fragment {

    CreateAccount createAccount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        createAccount = (CreateAccount) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_1, container, false);
    }
    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Content Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.
        Button next = createAccount.findViewById(R.id.tellUsNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstnameBox = createAccount.findViewById(R.id.firstName);
                EditText lastnameBox = createAccount.findViewById(R.id.lastName);
                String firstname = firstnameBox.getText().toString();
                String lastname = lastnameBox.getText().toString();
                if (firstname.isEmpty()) {
                    Snackbar.make(v, "Please enter your first name.", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (lastname.isEmpty()) {
                    Snackbar.make(v, "Please enter your last name.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                RadioButton student = createAccount.findViewById(R.id.radio_student);
                RadioButton tutor = createAccount.findViewById(R.id.radio_tutor);

                if (!student.isChecked() && !tutor.isChecked()) {
                    Snackbar.make(v, "Select either student or tutor", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (student.isChecked()) {

                    createAccount.student = true;
                    createAccount.firstName = firstname;
                    createAccount.lastName = lastname;
                    //launch student fragment 2
                    SignUpFrag2Student suf = new SignUpFrag2Student();
                    FragmentTransaction ftr = createAccount.fm.beginTransaction();
                    ftr.replace(R.id.fragment_container, suf).addToBackStack(null).commit();
                } else {
                    createAccount.student = false;
                    createAccount.firstName = firstname;
                    createAccount.lastName = lastname;
                    // launch tutor fragment 2
                }



                //createAccount.ft.replace(R.id.fragment_container, signUp2).addToBackStack(null).commit();
            }
        });


    }

}
