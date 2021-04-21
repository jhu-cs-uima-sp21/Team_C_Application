package com.example.hopportunities.data.model;

import java.util.ArrayList;

public class Question {
    private String title;
    private String question;
    private ArrayList<QuestionResponse> responses;

    public Question(){
        this("", "", new ArrayList<QuestionResponse>());
    }
    public Question(
    String title,
    String question,
    ArrayList<QuestionResponse> responses){
        this.title = title;
        this.question = question;
        this.responses = responses;
    }

    public void setResponse(ArrayList<QuestionResponse> response) {
        this.responses = response;
    }

    public void addResponse(QuestionResponse response){
        this.responses.add(response);
    }

    public ArrayList<QuestionResponse> getResponse() {
        return responses;
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
