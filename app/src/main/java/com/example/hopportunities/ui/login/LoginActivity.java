package com.example.hopportunities.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hopportunities.MainActivity;
import com.example.hopportunities.R;
import com.example.hopportunities.SignUp;
import com.example.hopportunities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

    private EditText email, password;
    private Button login;
    private TextView signup;
    private FirebaseAuth fAuth;
    private SharedPreferences myPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(logInListener);

        // Directs user to sign up page
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

//        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

//        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
//        dbref = mdbase.getReference("Users");
//        //dbref.setValue("HEllo World");
//        mItems = new ArrayList<>();
//        Context context = getApplicationContext();
//        myPrefs =
//                PreferenceManager.getDefaultSharedPreferences(context);
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                long count = snapshot.getChildrenCount();
//                //Log.d(TAG, "Children count: " + count);
//                //Log.d(TAG, "Client count: " + snapshot.child("clients").getChildrenCount());
//
//                // need to recreate the mItems list somehow
//                // another way is to use a FirebaseRecyclerView - see Sample Database code
//
//                mItems.clear();
//                Iterable<DataSnapshot> users = snapshot.child("Users").getChildren();
//                for (DataSnapshot pair : users) {
//                    mItems.add(pair.getValue(User.class));
//                }
//                //mAdapt.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                //Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    private View.OnClickListener logInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String em = email.getText().toString();
            String pw = password.getText().toString();

            // Error handling on empty / malformed fields
            if (em.isEmpty()) {
                Snackbar.make(v, "Please enter an email.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(em)) {
                Snackbar.make(v, "Please enter a valid email.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (pw.isEmpty()) {
                Snackbar.make(v, "Please enter a password.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            fAuth.signInWithEmailAndPassword(em, pw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // User verified, proceed to main activity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            });

//            if (username.getText().length() == 0 || password.getText().length() == 0) {
//                //show error toast
//                Snackbar.make(v, "Must enter username and password", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                //debugging
//                //ArrayList<String> subjects = new ArrayList<>();
//                //subjects.add("math");
//                //Student s = new Student("Bob", "Jone", "bob@asdf.edu", "1234", "seventh", subjects);
//                //dbref.child("clients").child(s.getFirstName()).setValue(s);
//                //dbref.child("messages").setValue("Hello, World");
//            } else {
//                String user = username.getText().toString();
//                String pass = password.getText().toString();
//
//                List<User> acc = containsAcc(user,pass);
//                if (acc.isEmpty()) {
//                    //toast Account doesn't exist
//                    Snackbar.make(v, "Sorry, that account doesnt exist.", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                } else if (acc.size() > 1) {
//                    //error two accounts with same login
//                } else {
//                    SharedPreferences.Editor peditor = myPrefs.edit();
//                    peditor.putString("user", user);
//                    peditor.putString("pass",pass);
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }


        }
    };

    // Checks if email is well-formed (e.g. ___@gmail.com)
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

//    private List<User> containsAcc(String user, String pass) {
//        List<User> ret = new ArrayList<User>();
//        for (User profile: mItems) {
//            if (profile.getEmail().equals(user) && profile.getPassword().equals(pass)) {
//                ret.add(profile);
//            }
//        }
//        return ret;
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

}