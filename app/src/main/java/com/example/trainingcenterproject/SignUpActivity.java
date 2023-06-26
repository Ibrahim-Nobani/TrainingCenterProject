package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    private Button adminButton;
    private Button traineeButton;
    private Button instructorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        adminButton = findViewById(R.id.adminButton);
        traineeButton = findViewById(R.id.traineeButton);
        instructorButton = findViewById(R.id.instructorButton);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminSignUpIntent = new Intent(SignUpActivity.this, AdminSignUpActivity.class);
                startActivity(adminSignUpIntent);
            }
        });

        traineeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent traineeSignUpIntent = new Intent(SignUpActivity.this, TraineeSignUpActivity.class);
                startActivity(traineeSignUpIntent);
            }
        });

        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructorSignUpIntent = new Intent(SignUpActivity.this, InstructorSignUpActivity.class);
                startActivity(instructorSignUpIntent);
            }
        });
    }
}
