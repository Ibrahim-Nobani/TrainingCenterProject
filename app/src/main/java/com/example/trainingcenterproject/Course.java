package com.example.trainingcenterproject;

public class Course {
    private int courseId;
    private String title;
    private String mainTopics;
    private String prerequisites;
    private String instructorEmail;
    private String registrationDeadline;
    private String startDate;
    private String schedule;
    private String venue;

    // Constructor
    public Course(String title, String mainTopics, String prerequisites,
                  String instructorEmail, String registrationDeadline, String startDate,
                  String schedule, String venue) {
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.instructorEmail = instructorEmail;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.schedule = schedule;
        this.venue = venue;
    }

    public Course() {
    }
// Getters and setters


    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getCourseId() {
        return this.courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainTopics() {
        return mainTopics;
    }

    public void setMainTopics(String mainTopics) {
        this.mainTopics = mainTopics;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", mainTopics='" + mainTopics + '\'' +
                ", prerequisites='" + prerequisites + '\'' +
                ", instructorEmail=" + instructorEmail +
                ", registrationDeadline='" + registrationDeadline + '\'' +
                ", startDate='" + startDate + '\'' +
                ", schedule='" + schedule + '\'' +
                ", venue='" + venue + '\'' +
                '}';
    }
}
