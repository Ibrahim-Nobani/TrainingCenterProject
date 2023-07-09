package com.example.trainingcenterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

public class TraineeSignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText mobileNumberEditText;
    private EditText addressEditText;
    private Button registerButton;


    private Button pickImageButton;
    private Trainee trainee = new Trainee();
    private  String photoPath;
    private static final int PICK_IMAGE_REQUEST_CODE = 100;

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
                    // Create a new trainee instance
                    Trainee trainee = new Trainee();
                    trainee.setEmail(emailEditText.getText().toString());
                    trainee.setPassword(passwordEditText.getText().toString());
                    trainee.setFirstName(firstNameEditText.getText().toString());
                    trainee.setLastName(lastNameEditText.getText().toString());
                    trainee.setMobileNumber(Integer.parseInt(mobileNumberEditText.getText().toString()));
                    trainee.setAddress(addressEditText.getText().toString());
                    trainee.setPhotoPath(photoPath);
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
