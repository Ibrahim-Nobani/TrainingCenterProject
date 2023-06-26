package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // User Input Elements
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;

    // Buttons
    private Button signInButton;
    private Button signUpButton;
    private Button listButton;
    private Button listInstructorButton;
    private Button listTraineeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the database
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "training", null, 1);

        // Initialize user input elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        // Initialize buttons
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        listButton = findViewById(R.id.list);
        listTraineeButton = findViewById(R.id.listTrinee);
        listInstructorButton = findViewById(R.id.listInstruct);

        // Load preferences if they exist
        loadPreferences();

        // Set on click listeners for the buttons
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add sign in logic here (query the database, start new activity, etc.)
                if (rememberMeCheckBox.isChecked()) {
                    savePreferences();
                }

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "training", null, 1);
                String role = dbHelper.getUserRole(email, password); // get role from database

                if (role == null) {
                    Toast.makeText(MainActivity.this, "Sign in failed. Invalid email or password", Toast.LENGTH_SHORT).show();
                } else {
                    switch (role) {
                        case "admin":
                            Intent adminIntent = new Intent(MainActivity.this, AdminDashboardActivity.class);
                            startActivity(adminIntent);
                            break;
                        case "trainee":
                            // Intent traineeIntent = new Intent(MainActivity.this, TraineeActivity.class);
                            // startActivity(traineeIntent);
                            Toast.makeText(MainActivity.this, "Trainee Activity is under development.", Toast.LENGTH_SHORT).show();
                            break;
                        case "instructor":
                            // Intent instructorIntent = new Intent(MainActivity.this, InstructorActivity.class);
                            // startActivity(instructorIntent);
                            Toast.makeText(MainActivity.this, "Instructor Activity is under development.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "Sign in failed. Invalid role", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminList = new Intent(MainActivity.this, AdminListActivity.class);
                startActivity(adminList);
            }
        });

        listTraineeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent traineeList = new Intent(MainActivity.this, TraineeListActivity.class);
                startActivity(traineeList);
            }
        });

        listInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructorList = new Intent(MainActivity.this, InstructorListActivity.class);
                startActivity(instructorList);
            }
        });
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        emailEditText.setText(sharedPreferences.getString("email", ""));
        passwordEditText.setText(sharedPreferences.getString("password", ""));
    }
}
