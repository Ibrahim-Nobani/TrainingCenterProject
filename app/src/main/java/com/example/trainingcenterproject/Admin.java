package com.example.trainingcenterproject;

public class Admin extends User{
    public Admin(int userId, String email, String password, String firstName, String lastName) {
        super(userId, email, password, firstName, lastName);
    }
    @Override
    public String toString() {
        return "Admin{" +
                "userId=" + getUserId() +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
