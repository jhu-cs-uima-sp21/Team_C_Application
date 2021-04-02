package com.example.hopportunities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopportunities.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText rEmail, rPassword, rConfirm;
    private Button register;
    private TextView backToLogin;

    FirebaseAuth fAuth;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;

    private Fragment signUp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rEmail = findViewById(R.id.rEmail);
        rPassword = findViewById(R.id.rPassword);
        rConfirm = findViewById(R.id.rConfirm);
        register = findViewById(R.id.register);
        backToLogin = findViewById(R.id.backToLogin);

        fAuth = FirebaseAuth.getInstance();
        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
        dbref = mdbase.getReference();

        register.setOnClickListener(registerListener);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Onclick listener for Register button
    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Extract data from the screen
            String email = rEmail.getText().toString();
            String password = rPassword.getText().toString();
            String confirm = rConfirm.getText().toString();

            // Error handling for empty and/or malformed fields
            if (email.isEmpty()) {
                Snackbar.make(v, "Please enter an email.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Snackbar.make(v, "Please enter a valid email.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Snackbar.make(v, "Please enter a password.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (confirm.isEmpty()) {
                Snackbar.make(v, "Please confirm your password.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // Password doesn't match with confirmation
            if (!password.equals(confirm)) {
                Snackbar.make(v, "Password does not match the confirmation.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // Data is well-formed, register the user to the DB
            fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // TODO: Send user to the sign-up process

                    Snackbar.make(v, "User added to the database.", Snackbar.LENGTH_SHORT).show();


                    // Linking Firebase Auth to Realtime Database
                    String id = fAuth.getCurrentUser().getUid();
                    User newUser = new User(id, email);
                    // then add to database, remember it's a map data structure
                    dbref.child("users").child(newUser.getId()).setValue(newUser);

                    //         Re-add fragment container later
                    //        signUp1 = new SignUpFrag1();
                    //        getSupportFragmentManager().beginTransaction()
                    //                .add(R.id.fragment_container, signUp1).commit();
                    Intent intent = new Intent(getBaseContext(), CreateAccount.class);
                    intent.putExtra("id", id);
                    intent.putExtra("email", email);
                    startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            });
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


    // TODO: Temporary class for testing, DELETE later
    public class User {
        public String id;
        public String email;

        public User() {
            // for calls to DataSnapshot.getValue(Client.class)
        }

        public User(String id, String email) {
            this.id = id;
            this.email = email;
        }

        public String toString() {
            return this.id + ": " + this.email;
        }

        public String getId() {
            return this.id;
        }
    }

}