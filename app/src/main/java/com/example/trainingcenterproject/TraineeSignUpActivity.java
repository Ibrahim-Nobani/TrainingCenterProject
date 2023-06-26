package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TraineeSignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText mobileNumberEditText;
    private EditText addressEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_sign_up);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        addressEditText = findViewById(R.id.addressEditText);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    // Create a new trainee instance
                    Trainee trainee = new Trainee();
                    trainee.setEmail(emailEditText.getText().toString());
                    trainee.setPassword(passwordEditText.getText().toString());
                    trainee.setFirstName(firstNameEditText.getText().toString());
                    trainee.setLastName(lastNameEditText.getText().toString());
                    trainee.setMobileNumber(Integer.parseInt(mobileNumberEditText.getText().toString()));
                    trainee.setAddress(addressEditText.getText().toString());

                    // Add the new admin to the database
                    DataBaseHelper dbHelper = new DataBaseHelper(TraineeSignUpActivity.this, "training", null, 1);

                    Toast.makeText(TraineeSignUpActivity.this, "Trainee Registered", Toast.LENGTH_SHORT).show();
                    dbHelper.insertTrainee(trainee);
                    finish();
                } else {
                    Toast.makeText(TraineeSignUpActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs() {
        // Add your input validation logic here
        return true;
    }
}
