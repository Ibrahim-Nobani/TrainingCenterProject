package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTraineeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trainee);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddTraineeActivity.this,"TrainingCenter",null,1);

        final EditText emailEditText = findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        final EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        final EditText mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        final EditText addressEditText = findViewById(R.id.addressEditText);


        Button addTraineeButton = (Button) findViewById(R.id.addTraineeButton);
        addTraineeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trainee newTrainee = new Trainee();
                if (emailEditText.getText().toString().isEmpty()) {
                    newTrainee.setEmail("No email specified");
                } else {
                    newTrainee.setEmail(emailEditText.getText().toString());
                }

                if (passwordEditText.getText().toString().isEmpty()) {
                    newTrainee.setPassword("No password specified");
                } else {
                    newTrainee.setPassword(passwordEditText.getText().toString());
                }

                if (firstNameEditText.getText().toString().isEmpty()) {
                    newTrainee.setFirstName("No first name specified");
                } else {
                    newTrainee.setFirstName(firstNameEditText.getText().toString());
                }

                if (lastNameEditText.getText().toString().isEmpty()) {
                    newTrainee.setLastName("No last name specified");
                } else {
                    newTrainee.setLastName(lastNameEditText.getText().toString());
                }
                if (mobileNumberEditText.getText().toString().isEmpty()) {
                    newTrainee.setMobileNumber(0); // Set a default value or handle it as per your requirements
                } else {
                    newTrainee.setMobileNumber(Integer.parseInt(mobileNumberEditText.getText().toString()));
                }

                if (addressEditText.getText().toString().isEmpty()) {
                    newTrainee.setAddress("No address specified");
                } else {
                    newTrainee.setAddress(addressEditText.getText().toString());
                }
                dataBaseHelper.insertTrainee(newTrainee);
                //Intent intent = new Intent(AddCourseActivity.this, viewCourses.class);
                //AddCourseActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}