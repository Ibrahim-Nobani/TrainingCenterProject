package com.example.trainingcenterproject.trainee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;
import com.example.trainingcenterproject.Trainee;

public class TraineeEditProfileActivity extends AppCompatActivity {

    private String emailText;
    private Trainee trainee;
    EditText email;
    EditText password;
    EditText firstName;
    EditText lastName;
    EditText mobileNumber;
    EditText address;

    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_edit_profile);

        email = (EditText) findViewById(R.id.email_edit_trainee);
        email.setFocusable(false);
        password = (EditText) findViewById(R.id.password_edit_trainee);
        firstName = (EditText) findViewById(R.id.fName_edit_trainee);
        lastName = (EditText) findViewById(R.id.lName_edit_trainee);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber_edit_trainee);
        address =  (EditText) findViewById(R.id.address_edit_trainee);

        cancel = (Button) findViewById(R.id.edit_cancel_button_trainee);
        save = (Button) findViewById(R.id.edit_save_button_trainee);
        loadUserDetails();
        setButtonListners();
    }


    private void setButtonListners(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(TraineeEditProfileActivity.this, TraineeMainActivity.class);
                startActivity(cancel);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeEditProfileActivity.this,"training", null,1);
                trainee.setPassword(password.getText().toString());
                trainee.setFirstName(firstName.getText().toString());
                trainee.setLastName(lastName.getText().toString());
                trainee.setMobileNumber(Integer.parseInt( mobileNumber.getText().toString()));
                trainee.setAddress(address.getText().toString());
                dataBaseHelper.updateTrainee(trainee);
                Intent exit = new Intent(TraineeEditProfileActivity.this, TraineeMainActivity.class);
                startActivity(exit);
            }
        });
    }

    private void loadUserDetails(){
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        emailText = sharedPreferences.getString("email","");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeEditProfileActivity.this,"training", null,1);
        trainee = dataBaseHelper.getTraineeDetails(emailText);
      if(trainee != null) {
           email.setText(trainee.getEmail());
           password.setText(trainee.getPassword());
            firstName.setText(trainee.getFirstName());
            lastName.setText(trainee.getLastName());
            mobileNumber.setText(String.format("%d", trainee.getMobileNumber()));
            address.setText(trainee.getAddress());
       }
    }
}