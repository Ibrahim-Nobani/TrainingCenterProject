package com.example.trainingcenterproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InstructorDashboardActivity extends AppCompatActivity {
    private String email;
    private ImageView adminPhoto;

    private Bitmap defaultBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_panel);
        defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.andrew);
        Button prevCoursesButton = findViewById(R.id.view_prev_courses_button);
        Button scheduleButton = findViewById(R.id.view_schedule_button);
        Button viewStudentsButton = findViewById(R.id.view_students_button);
        Button viewProfileButton = findViewById(R.id.view_profile_button);

        adminPhoto = findViewById(R.id.admin_photo);
        DataBaseHelper dbHelper = new DataBaseHelper(this, "training", null, 1);
        email = getIntent().getStringExtra("email");
        if (email == null) {
            Log.e("Email", "Email is null!");
        } else {
            Log.i("Email", "Email is: " + email);
        }

        Cursor res = dbHelper.getInstructorPhoto(email);

        if (res != null && res.moveToFirst()){
            String fileName = res.getString(0);
            if (fileName != null) {  // check if fileName is null
                try {
                    FileInputStream fis = openFileInput(fileName);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    adminPhoto.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    adminPhoto.setImageBitmap(defaultBitmap);
                    e.printStackTrace();
                } finally {
                    res.close();
                }
            } else {
                // fileName is null, set to default
                adminPhoto.setImageBitmap(defaultBitmap);
            }
        } else {
            // cursor is null or empty, set to default
            adminPhoto.setImageBitmap(defaultBitmap);
        }

        prevCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(InstructorDashboardActivity.this, PrevCourseActivity.class);
                addCourse.putExtra("email", email);
                startActivity(addCourse);
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCourses = new Intent(InstructorDashboardActivity.this, ScheduleActivity.class);
                viewCourses.putExtra("email", email);
                startActivity(viewCourses);
            }
        });

        viewStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(InstructorDashboardActivity.this, InstructorTraineeListActivity.class);
                email = getIntent().getStringExtra("email");
                addCourse.putExtra("email", email);
                startActivity(addCourse);


            }
        });
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(InstructorDashboardActivity.this, EditInstructorActivity.class);
                email = getIntent().getStringExtra("email");
                profile.putExtra("email", email);
                startActivity(profile);




            }
        });





    }
}
