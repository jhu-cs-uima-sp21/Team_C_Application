package com.example.hopportunities.data.model;

import java.util.ArrayList;

public class Question {
    private String title;
    private String question;
    private boolean hasResponse;
    public Question(){
        this("", "", false);
    }
    public Question(
    String title,
    String question,
    boolean hasResponse){
        this.title = title;
        this.question = question;
        this.hasResponse = hasResponse;
    }

    public boolean getHasResponse() {
        return hasResponse;
    }

    public void setHasResponse(boolean hasResponse) {
        this.hasResponse = hasResponse;
    }

    public String getQuestion() {
        return question;
    }

    public String getTitle() {
        return title;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
