package com.example.hopportunities.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                Toast.makeText(getContext(), "Question?", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<Question> list = new ArrayList<>();
        for(int i = 0;i<100;i++){
            list.add(new Question(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "asdf", "asdfasdf", "asdfasdf", "asdf"));
        }
        RecyclerView recyclerView=root.findViewById(R.id.question_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        QuestionRecyclerAdapter adapter=new QuestionRecyclerAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        return root;
    }
}
