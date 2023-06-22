package com.example.trainingcenterproject;

public class Instructor extends User{
    private int mobileNumber;
    private String address;
    private String specialization;
    private String degree;
    public Instructor(int userId, String email, String password, String firstName, String lastName,
                      int mobileNumber, String address ,String specialization, String degree) {
        super(userId, email, password, firstName, lastName);
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.specialization = specialization;
        this.degree = degree;
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
    @Override
    public String toString() {
        return "Instructor{" +
                "userId=" + getUserId() +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", address='" + address + '\'' +
                ", specialization='" + specialization + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }
}
