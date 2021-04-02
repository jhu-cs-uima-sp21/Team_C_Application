package com.example.hopportunities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class CreateAccount  extends FragmentActivity {
    private Fragment signUp1;
    private String userId;
    private String userEmail;
    private String userPassword;
    public String firstName;
    public String lastName;
    private FragmentTransaction ft;
    public Boolean student; //true = student false = tutor
    public FragmentManager fm;
    public String gradeEdu;
    public List<String> subs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userId = getIntent().getStringExtra("id");
        userEmail = getIntent().getStringExtra("email");
        signUp1 = new SignUpFrag1();
        fm = getSupportFragmentManager();
        ft = getSupportFragmentManager().beginTransaction();
        if (ft == null) {
            Log.d("NULL", "NULL");
        }
        ft.replace(R.id.fragment_container, signUp1).addToBackStack(null).commit();
    }
}