package com.example.hopportunities.data.model;

public class QuestionResponse {
    private String uuid;
    private String title;
    private String author;
    private String responseUuid;
    private String response;

public QuestionResponse(String uuid,
String title,
        String author,
        String responseUuid,
        String response){
    this.uuid = uuid;
    this.title = title;
    this.author = author;
    this.responseUuid = responseUuid;
    this.response = response;
}

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResponseUuid(String responseUuid) {
        this.responseUuid = responseUuid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUuid() {
        return uuid;
    }

    public String getResponseUuid() {
        return responseUuid;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
