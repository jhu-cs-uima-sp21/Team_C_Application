package com.example.hopportunities.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopportunities.R;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    private TextView titleText;
    private TextView questionText;
    private TextView StatusText;
    private View mainView;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        mainView = itemView;
        titleText = itemView.findViewById(R.id.title);
        questionText = itemView.findViewById(R.id.question);
        StatusText = itemView.findViewById(R.id.status);
    }

    public View getMainView() {
        return mainView;
    }

    public void setTitleText(String titleText) {
        this.titleText.setText(titleText);
    }

    public void setQuestionText(String questionText) {
        this.questionText.setText(questionText);
    }

    public void setStatusText(String statusText) {
        this.StatusText.setText(statusText);
    }
}
