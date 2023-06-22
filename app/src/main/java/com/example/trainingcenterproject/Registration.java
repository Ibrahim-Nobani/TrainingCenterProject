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
