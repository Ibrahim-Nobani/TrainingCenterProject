package com.example.trainingcenterproject;

public class Admin extends User{
    public Admin(String email, String password, String firstName, String lastName, String photo) {
        super(email, password, firstName, lastName, photo);
    }

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
