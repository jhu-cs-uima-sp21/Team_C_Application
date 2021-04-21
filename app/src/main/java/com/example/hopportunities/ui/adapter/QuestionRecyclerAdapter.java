package com.example.hopportunities.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopportunities.R;
import com.example.hopportunities.data.model.Question;

import java.util.ArrayList;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private ArrayList<Question> list;
    private Context context;
    public QuestionRecyclerAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }


    public QuestionRecyclerAdapter(Context context, ArrayList<Question> list){
        this.context = context;
        this.list = list;
    }

    public void setItem(ArrayList<Question> list){
        this.list = list;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question,parent,false);
        ArticleViewHolder holder = new ArticleViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Question item = list.get(position);
        holder.setQuestionText(item.getQuestion());
        holder.setStatusText(item.getResponse().isEmpty() ?
                        context.getString(R.string.no_responses) :
                        context.getString(R.string.view_responses));
        holder.setTitleText(item.getTitle());
        holder.getMainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
