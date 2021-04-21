package com.example.hopportunities.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopportunities.R;
import com.example.hopportunities.data.model.Question;
import com.example.hopportunities.data.model.QuestionResponse;
import com.example.hopportunities.ui.adapter.QuestionRecyclerAdapter;
import com.example.hopportunities.ui.adapter.QuestionResponseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResponseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question_responses);


        String question = getIntent().getStringExtra("question");
        String title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference refer = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/")
                .getReference().child("question").child(id).child("responses");
        ArrayList<QuestionResponse> list = new ArrayList<>();
        RecyclerView recyclerView= findViewById(R.id.response_list);
        Button answer = findViewById(R.id.goto_answer);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("question", question);
                intent.putExtra("id", id);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        QuestionResponseRecyclerAdapter adapter=new QuestionResponseRecyclerAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);
        adapter.setUid(uid);
        adapter.setId(id);
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    QuestionResponse qes = postSnapshot.getValue(QuestionResponse.class);
                    list.add(qes);
                }
                adapter.setItem(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
