package com.example.hopportunities.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopportunities.MainActivity;
import com.example.hopportunities.R;
import com.example.hopportunities.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    private List<User> mItems;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private SharedPreferences myPrefs;

    private List<User> containsAcc(String user, String pass) {
        List<User> ret = new ArrayList<User>();

        for (User profile: mItems) {
            if (profile.getEmail().equals(user) && profile.getPassword().equals(pass)) {
                ret.add(profile);
            }
        }
        return ret;
    }
    private View.OnClickListener logInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (usernameEditText.getText().equals("") || passwordEditText.getText().equals("")) {
                //show error toast
            } else {
                String user = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();

                List<User> acc = containsAcc(user,pass);
                if (acc.isEmpty()) {
                    //toast Account doesn't exist
                } else if (acc.size() > 1) {
                    //error two accounts with same login
                } else {
                    SharedPreferences.Editor peditor = myPrefs.edit();
                    peditor.putString("user", user);
                    peditor.putString("pass",pass);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        mdbase = FirebaseDatabase.getInstance();
        dbref = mdbase.getReference();
        mItems = new ArrayList<>();
        Context context = getApplicationContext();
        myPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long count = snapshot.getChildrenCount();
                //Log.d(TAG, "Children count: " + count);
                //Log.d(TAG, "Client count: " + snapshot.child("clients").getChildrenCount());

                // need to recreate the mItems list somehow
                // another way is to use a FirebaseRecyclerView - see Sample Database code

                mItems.clear();
                Iterable<DataSnapshot> users = snapshot.child("Users").getChildren();
                for (DataSnapshot pair : users) {
                    mItems.add(pair.getValue(User.class));
                }
                //mAdapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        loginButton.setOnClickListener(logInListener);
    }
    public void onLogIn(View view) {

    }

}