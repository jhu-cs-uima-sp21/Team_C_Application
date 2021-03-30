package com.example.hopportunities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class SignUp extends AppCompatActivity {
    private Fragment signUp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp1 = new SignUpFrag1();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, signUp1).commit();
    }
}