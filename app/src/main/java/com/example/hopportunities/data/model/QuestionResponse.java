package com.example.hopportunities.data.model;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class QuestionResponse {
    private String firstName;
    private String lastName;
    private String response;
    private Date timestamp;

    public QuestionResponse(){
        this("", "", "");
    }

    public QuestionResponse(String firstName,
                            String lastName,
                            String response){
        this.firstName = firstName;
        this.lastName = lastName;
        this.response = response;
        this.timestamp = Calendar.getInstance().getTime();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
