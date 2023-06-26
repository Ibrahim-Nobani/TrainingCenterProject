package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddInstructorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddInstructorActivity.this,"TrainingCenter",null,1);

        final EditText emailEditText = findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        final EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        final EditText mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        final EditText addressEditText = findViewById(R.id.addressEditText);
        final EditText specializationEditText = findViewById(R.id.specializationEditText);
        final EditText degreeEditText = findViewById(R.id.degreeEditText);


        Button addInstructorButton = (Button) findViewById(R.id.addInstructorButton);
        addInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Instructor newInstructor = new Instructor();
                if (emailEditText.getText().toString().isEmpty()) {
                    newInstructor.setEmail("No email specified");
                } else {
                    newInstructor.setEmail(emailEditText.getText().toString());
                }

                if (passwordEditText.getText().toString().isEmpty()) {
                    newInstructor.setPassword("No password specified");
                } else {
                    newInstructor.setPassword(passwordEditText.getText().toString());
                }

                if (firstNameEditText.getText().toString().isEmpty()) {
                    newInstructor.setFirstName("No first name specified");
                } else {
                    newInstructor.setFirstName(firstNameEditText.getText().toString());
                }

                if (lastNameEditText.getText().toString().isEmpty()) {
                    newInstructor.setLastName("No last name specified");
                } else {
                    newInstructor.setLastName(lastNameEditText.getText().toString());
                }
                if (mobileNumberEditText.getText().toString().isEmpty()) {
                    newInstructor.setMobileNumber(0); // Set a default value or handle it as per your requirements
                } else {
                    newInstructor.setMobileNumber(Integer.parseInt(mobileNumberEditText.getText().toString()));
                }

                if (addressEditText.getText().toString().isEmpty()) {
                    newInstructor.setAddress("No address specified");
                } else {
                    newInstructor.setAddress(addressEditText.getText().toString());
                }

                if (specializationEditText.getText().toString().isEmpty()) {
                    newInstructor.setSpecialization("No specialization specified");
                } else {
                    newInstructor.setSpecialization(specializationEditText.getText().toString());
                }

                if (degreeEditText.getText().toString().isEmpty()) {
                    newInstructor.setDegree("No degree specified");
                } else {
                    newInstructor.setDegree(degreeEditText.getText().toString());
                }


                dataBaseHelper.insertInstructor(newInstructor);
                //Intent intent = new Intent(AddCourseActivity.this, viewCourses.class);
                //AddCourseActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}