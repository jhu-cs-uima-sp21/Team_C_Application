package com.example.hopportunities.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopportunities.R;
import com.example.hopportunities.data.model.Question;
import com.example.hopportunities.data.model.QuestionResponse;
import com.example.hopportunities.ui.question.AnswerActivity;
import com.example.hopportunities.ui.question.ResponseActivity;

import java.util.ArrayList;

public class QuestionResponseRecyclerAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private ArrayList<QuestionResponse> list;
    private Context context;
    private String id;
    private String uid;
    private String title;
    private String question;
    public QuestionResponseRecyclerAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    public void setUid(String uid){
        this.uid = uid;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setQuestion(String question){
        this.question = question;
    }

    public void setId(String id){
        this.id = id;
    }


    public QuestionResponseRecyclerAdapter(Context context, ArrayList<QuestionResponse> list){
        this.context = context;
        this.list = list;
    }

    public void setItem(ArrayList<QuestionResponse> list){
        this.list = list;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question,parent,false);
        QuestionViewHolder holder = new QuestionViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionResponse item = list.get(position);
        holder.setQuestionText(item.getResponse());

        holder.setTitleText(item.getFirstName() + " " + item.getLastName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
