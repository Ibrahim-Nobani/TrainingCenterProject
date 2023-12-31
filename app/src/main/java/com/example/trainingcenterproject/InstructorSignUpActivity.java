package com.example.trainingcenterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

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
    private Button pickImageButton;
    private Instructor instructor = new Instructor();

    private  String photoPath;
    private static final int PICK_IMAGE_REQUEST_CODE = 100;

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

        pickImageButton = findViewById(R.id.pickImageButton);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST_CODE);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    // Create Instructor instance

                    instructor.setEmail(emailEditText.getText().toString());
                    instructor.setPassword(passwordEditText.getText().toString());
                    instructor.setFirstName(firstNameEditText.getText().toString());
                    instructor.setLastName(lastNameEditText.getText().toString());
                    instructor.setMobileNumber(Integer.parseInt(mobileNumberEditText.getText().toString()));
                    instructor.setAddress(addressEditText.getText().toString());
                    instructor.setSpecialization(specializationEditText.getText().toString());
                    instructor.setDegree(degreeSpinner.getSelectedItem().toString());
                    instructor.setPhotoPath(photoPath);

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
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(InstructorSignUpActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Add further validation here as needed
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                // Display an error
                return;
            }
            Uri photoUri = data.getData();
            String fileName = copyImageToInternalStorage(photoUri);
            if (fileName != null) {
                photoPath = fileName;
            }

        }
    }

    private String copyImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);

            String fileName = UUID.randomUUID().toString() + ".png";
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);

            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
