package com.example.trainingcenterproject;

public class Trainee extends User{
    private int mobileNumber;
    private String address;
    public Trainee(String email, String password, String firstName, String lastName,
                      int mobileNumber, String address) {
        super(email, password, firstName, lastName);
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    public Trainee() {
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
    public String toString() {
        return "Instructor{" +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", address='" + address + '\'' +
                '}';
    }
}
