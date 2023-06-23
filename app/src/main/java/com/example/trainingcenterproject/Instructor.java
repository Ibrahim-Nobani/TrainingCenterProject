package com.example.trainingcenterproject;

import java.util.ArrayList;

public class Instructor extends User{
    private int mobileNumber;
    private String address;
    private String specialization;
    private String degree;
    private ArrayList<Course> courses= new ArrayList<>();

    public Instructor(String email, String password, String firstName, String lastName,
                      int mobileNumber, String address ,String specialization, String degree, ArrayList<Course> courses) {
        super(email, password, firstName, lastName);
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.specialization = specialization;
        this.degree = degree;
        this.courses=courses;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    @Override
    public String toString() {
        return "Instructor{" +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", address='" + address + '\'' +
                ", specialization='" + specialization + '\'' +
                ", degree='" + degree + '\'' +
                ", Instructor{" + "courses=" + courses +
                '}';
    }
}
