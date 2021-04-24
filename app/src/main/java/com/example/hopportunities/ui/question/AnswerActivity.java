package com.example.hopportunities.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopportunities.MainActivity;
import com.example.hopportunities.R;
import com.example.hopportunities.data.model.QuestionResponse;
import com.example.hopportunities.ui.adapter.QuestionResponseRecyclerAdapter;
import com.example.hopportunities.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class AnswerActivity extends AppCompatActivity implements ValueEventListener {
    private String firstName, lastName, uid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        setTitle(R.string.answer_question);

        String title = getIntent().getStringExtra("title");
        String question = getIntent().getStringExtra("question");
        String id = getIntent().getStringExtra("id");
        uid = getIntent().getStringExtra("uid");
        Log.i("ViewModel", uid);
        FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/").getReference().child("students").addValueEventListener(this);
        FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/").getReference().child("tutors").addValueEventListener(this);
        DatabaseReference refer = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/")
                .getReference().child("question").child(id).child("responses");
        Button answer = findViewById(R.id.answer_submit);
        TextView titleText = findViewById(R.id.title);
        TextView questionText = findViewById(R.id.question);
        EditText answerText = findViewById(R.id.answer_text);
        titleText.setText(title);
        questionText.setText(question);

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Answer", "name : " + firstName + " " + lastName);
                if(answerText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference item = refer.push();
                    QuestionResponse answer = new QuestionResponse(firstName, lastName, answerText.getText().toString());
                    item.setValue(answer);

                    DatabaseReference dbref  = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/").getReference();
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("tutors").child(id).getValue() != null) {
                                int ans;
                                if (dataSnapshot.child("numAns") == null) {
                                    ans = 0;
                                } else {
                                    ans = parseInt(dataSnapshot.child("numAns").child(id).getValue().toString());
                                }
                                dbref.child("numAns").child(id).setValue(ans + 1);
                            }

                        }
                        @Override public void onCancelled(DatabaseError error) { }
                    });
                    finish();
                }
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            String dataId = postSnapshot.child("id").getValue().toString();
            if(dataId.equals(uid)){
                firstName = postSnapshot.child("firstName").getValue().toString();
                lastName = postSnapshot.child("lastName").getValue().toString();
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
