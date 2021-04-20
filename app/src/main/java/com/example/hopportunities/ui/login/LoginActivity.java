package com.example.hopportunities.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
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
import com.example.hopportunities.SignUp;
import com.example.hopportunities.Student;
import com.example.hopportunities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
    private Button createAccountButton;
    private SharedPreferences myPrefs;

    private List<User> containsAcc(String user, String pass) {
        List<User> ret = new ArrayList<User>();
        Log.i("Item", "Size : " + ret.size());


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
            if (usernameEditText.getText().length() == 0 || passwordEditText.getText().length() == 0) {
                //show error toast
                Snackbar.make(v, "Must enter username and password", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //debugging
                //ArrayList<String> subjects = new ArrayList<>();
                //subjects.add("math");
                //Student s = new Student("Bob", "Jone", "bob@asdf.edu", "1234", "seventh", subjects);
                //dbref.child("clients").child(s.getFirstName()).setValue(s);
                //dbref.child("messages").setValue("Hello, World");
            } else {
                String user = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();

                List<User> acc = containsAcc(user,pass);
                if (acc.isEmpty()) {
                    //toast Account doesn't exist
                    Snackbar.make(v, "Sorry, that account doesnt exist.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (acc.size() > 1) {
                    //error two accounts with same login
                } else {
                    SharedPreferences.Editor peditor = myPrefs.edit();
                    peditor.putString("user", user);
                    peditor.putString("pass",pass);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
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
        createAccountButton= findViewById(R.id.createAccount);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com");
        dbref = mdbase.getReference("users");
        //dbref.setValue("HEllo World");
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

                Log.d("DB", "Exists : " + snapshot.exists());
                Log.d("DB", "Children count: " + count);
                Log.d("DB", "Client count: " + snapshot.child("users").getChildrenCount());

                // need to recreate the mItems list somehow
                // another way is to use a FirebaseRecyclerView - see Sample Database code

                mItems.clear();
                Iterable<DataSnapshot> users = snapshot.getChildren();
                for (DataSnapshot pair : users) {
                    User user = pair.getValue(User.class);
                    Log.i("User", user.toString());
                    mItems.add(user);
                }
                //mAdapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(logInListener);
    }
    public void onLogIn(View view) {

    }

}