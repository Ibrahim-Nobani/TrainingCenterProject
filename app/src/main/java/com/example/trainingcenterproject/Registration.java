package com.example.trainingcenterproject;

public class Registration {
    private int registrationId;
    private int courseId;
    private int traineeId;
    private String status;

    // Constructor
    public Registration(int registrationId, int courseId, int traineeId, String status) {
        this.registrationId = registrationId;
        this.courseId = courseId;
        this.traineeId = traineeId;
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

    public int getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(int traineeId) {
        this.traineeId = traineeId;
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
                ", traineeId=" + traineeId +
                ", status='" + status + '\'' +
                '}';
    }
}
