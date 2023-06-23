package com.example.trainingcenterproject;

public class Registration {
    private int registrationId;
    private int courseId;
    private String traineeEmail;
    private String status;

    // Constructor
    public Registration(int registrationId, int courseId, String traineeEmail, String status) {
        this.registrationId = registrationId;
        this.courseId = courseId;
        this.traineeEmail = traineeEmail;
        this.status = status;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTraineeEmail() {
        return traineeEmail;
    }

    public void setTraineeEmail(String traineeEmail) {
        this.traineeEmail = traineeEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", courseId=" + courseId +
                ", traineeEmail=" + traineeEmail +
                ", status='" + status + '\'' +
                '}';
    }
}
