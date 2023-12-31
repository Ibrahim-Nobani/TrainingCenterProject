package com.example.trainingcenterproject;

public class Registration {

    private int courseId;
    private String traineeEmail;
    private String status;

    // Constructor
    public Registration(int courseId, String traineeEmail, String status) {

        this.courseId = courseId;
        this.traineeEmail = traineeEmail;
        this.status = status;
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
                ", courseId=" + courseId +
                ", traineeEmail=" + traineeEmail +
                ", status='" + status + '\'' +
                '}';
    }
}
