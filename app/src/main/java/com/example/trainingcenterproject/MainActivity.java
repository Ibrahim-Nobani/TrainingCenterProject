package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trainingcenterproject.trainee.TraineeMainActivity;

public class MainActivity extends AppCompatActivity {

    // User Input Elements
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;

    // Buttons
    private Button signInButton;
    private Button signUpButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // If not, request it
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }



        // Create the database
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "training", null, 1);
        dbHelper.addDummyData();
        // Initialize user input elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        // Initialize buttons
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

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
                            adminIntent.putExtra("email", email);
                            startActivity(adminIntent);
                            break;
                        case "trainee":
                            saveNameIntoPreferences();
                            Intent traineeIntent = new Intent(MainActivity.this, TraineeMainActivity.class);
                            traineeIntent.putExtra("email", email);
                            startActivity(traineeIntent);
                            //Toast.makeText(MainActivity.this, "Trainee Activity is under development.", Toast.LENGTH_SHORT).show();
                            break;
                        case "instructor":
                             Intent instructorIntent = new Intent(MainActivity.this, InstructorDashboardActivity.class);
                             instructorIntent.putExtra("email", email);
                             startActivity(instructorIntent);
//                            Toast.makeText(MainActivity.this, "Instructor Activity is under development.", Toast.LENGTH_SHORT).show();
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


    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.apply();
    }

    private void saveNameIntoPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", emailEditText.getText().toString());
        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        emailEditText.setText(sharedPreferences.getString("email", ""));
        passwordEditText.setText(sharedPreferences.getString("password", ""));
    }
}
