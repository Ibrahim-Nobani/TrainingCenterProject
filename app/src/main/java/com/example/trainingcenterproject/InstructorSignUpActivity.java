package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InstructorSignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText mobileNumberEditText;
    private EditText addressEditText;
    private EditText specializationEditText;
    private Spinner degreeSpinner;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_sign_up);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        addressEditText = findViewById(R.id.addressEditText);
        specializationEditText = findViewById(R.id.specializationEditText);
        degreeSpinner = findViewById(R.id.degreeSpinner);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    // Create Instructor instance
                    Instructor instructor = new Instructor();
                    instructor.setEmail(emailEditText.getText().toString());
                    instructor.setPassword(passwordEditText.getText().toString());
                    instructor.setFirstName(firstNameEditText.getText().toString());
                    instructor.setLastName(lastNameEditText.getText().toString());
                    instructor.setMobileNumber(Integer.parseInt(mobileNumberEditText.getText().toString()));
                    instructor.setAddress(addressEditText.getText().toString());
                    instructor.setSpecialization(specializationEditText.getText().toString());
                    instructor.setDegree(degreeSpinner.getSelectedItem().toString());


                    DataBaseHelper dbHelper = new DataBaseHelper(InstructorSignUpActivity.this, "training", null, 1);

                    // Add the new Instructor to the database.
                    dbHelper.insertInstructor(instructor);

                    Toast.makeText(InstructorSignUpActivity.this, "Instructor Registered", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(InstructorSignUpActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validateInputs() {
        // Add your input validation logic here
        return true;
    }
}
