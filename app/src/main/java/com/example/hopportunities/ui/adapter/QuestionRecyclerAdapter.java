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
import com.example.hopportunities.ui.question.ResponseActivity;

import java.util.ArrayList;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private ArrayList<Pair<String, Question>> list;
    private Context context;
    public QuestionRecyclerAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }


    public QuestionRecyclerAdapter(Context context, ArrayList<Pair<String, Question>> list){
        this.context = context;
        this.list = list;
    }

    public void setItem(ArrayList<Pair<String, Question>> list){
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
        Pair<String, Question> pair = list.get(position);
        Question item = pair.second;
        holder.setQuestionText(item.getQuestion());
        holder.setStatusText(item.getHasResponse() ?
                        context.getString(R.string.view_responses) :
                        context.getString(R.string.no_responses));
        holder.setTitleText(item.getTitle());
        holder.getMainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResponseActivity.class);
                intent.putExtra("id", pair.first);
                intent.putExtra("title", pair.second.getTitle());
                intent.putExtra("question", pair.second.getQuestion());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
