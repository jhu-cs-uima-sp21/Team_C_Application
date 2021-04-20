package com.example.hopportunities.data.model;

public class Question {
    private String uuid;
    private String title;
    private String author;
    private String responseUuid;
    private String question;
    private String status;

    public Question(
    String uuid,
    String title,
    String author,
    String responseUuid,
    String question,
    String status){
        this.uuid = uuid;
        this.title = title;
        this.author = author;
        this.responseUuid = responseUuid;
        this.question = question;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public String getQuestion() {
        return question;
    }

    public String getTitle() {
        return title;
    }

    public String getResponseUuid() {
        return responseUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setResponseUuid(String responseUuid) {
        this.responseUuid = responseUuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
