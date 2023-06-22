package com.example.trainingcenterproject;

public class Course {
    private int courseId;
    private String courseNumber;
    private String title;
    private String mainTopics;
    private String prerequisites;
    private int instructorId;
    private String registrationDeadline;
    private String startDate;
    private String schedule;
    private String venue;

    // Constructor
    public Course(int courseId, String courseNumber, String title, String mainTopics, String prerequisites,
                  int instructorId, String registrationDeadline, String startDate,
                  String schedule, String venue) {
        this.courseId = courseId;
        this.courseNumber = courseNumber;
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.instructorId = instructorId;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.schedule = schedule;
        this.venue = venue;
    }

    // Getters and setters


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
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

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
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
                ", courseNumber='" + courseNumber + '\'' +
                ", title='" + title + '\'' +
                ", mainTopics='" + mainTopics + '\'' +
                ", prerequisites='" + prerequisites + '\'' +
                ", instructorId=" + instructorId +
                ", registrationDeadline='" + registrationDeadline + '\'' +
                ", startDate='" + startDate + '\'' +
                ", schedule='" + schedule + '\'' +
                ", venue='" + venue + '\'' +
                '}';
    }
}
