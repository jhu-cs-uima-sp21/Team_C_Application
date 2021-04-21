package com.example.hopportunities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopportunities.CreateAccount;
import com.example.hopportunities.MainActivity;
import com.example.hopportunities.R;
import com.example.hopportunities.data.model.Question;
import com.example.hopportunities.ui.adapter.QuestionRecyclerAdapter;
import com.example.hopportunities.ui.question.NewQuestionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MainActivity mainActivity;
    private CreateAccount createAccount;
    private FloatingActionButton newQuestion;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        homeViewModel =
                new ViewModelProvider(mainActivity).get(HomeViewModel.class);
        String firstName = homeViewModel.firstName;

        View root = inflater.inflate(R.layout.fragment_student_question, container, false);
        newQuestion = root.findViewById(R.id.new_question_button);
        newQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewQuestionActivity.class));
            }
        });

        DatabaseReference refer = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/")
                .getReference().child("question");
        ArrayList<Pair<String, Question>> list = new ArrayList<>();
        RecyclerView recyclerView=root.findViewById(R.id.question_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        QuestionRecyclerAdapter adapter=new QuestionRecyclerAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Question qes = postSnapshot.getValue(Question.class);
                    qes.setHasResponse(postSnapshot.child("responses").getChildrenCount() != 0);
                    list.add(new Pair<String, Question>(postSnapshot.getKey(), qes));
                }
                adapter.setItem(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }
}
