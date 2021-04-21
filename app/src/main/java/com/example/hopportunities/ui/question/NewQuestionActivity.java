package com.example.hopportunities.ui.question;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hopportunities.MainActivity;
import com.example.hopportunities.R;
import com.example.hopportunities.SignUp;
import com.example.hopportunities.User;
import com.example.hopportunities.data.model.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewQuestionActivity extends AppCompatActivity {
    private Button submit;
    private EditText title;
    private EditText question;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        setTitle(R.string.ask_your_question);
        submit = findViewById(R.id.question_submit);
        title = findViewById(R.id.title);
        question = findViewById(R.id.question);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question q = new Question(
                        title.getText().toString(),
                        question.getText().toString(),
                        new ArrayList<>());
                if(q.getTitle().isEmpty() || q.getQuestion().isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_SHORT).show();
                }else{
                    String uid = FirebaseAuth.getInstance().getUid();
                    DatabaseReference refer =  FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/")
                            .getReference().child("question");
                    DatabaseReference userData = refer.child(uid);
                    DatabaseReference newPos = userData.push();
                    newPos.setValue(q);
                    finish();
                }
            }
        });
    }

}
