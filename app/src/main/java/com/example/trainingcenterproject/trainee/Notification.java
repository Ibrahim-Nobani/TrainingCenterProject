package com.example.trainingcenterproject.trainee;

public class Notification {
    private int id;
    private String userEmail;
    private String content;

    public Notification(String userEmail, String content) {
        this.userEmail = userEmail;
        this.content = content;
    }

    public Notification(int id, String userEmail, String content) {
        this.id = id;
        this.userEmail = userEmail;
        this.content = content;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
