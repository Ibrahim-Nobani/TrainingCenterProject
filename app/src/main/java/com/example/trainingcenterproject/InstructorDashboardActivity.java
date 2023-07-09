package com.example.trainingcenterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InstructorDashboardActivity extends AppCompatActivity {
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_panel);

        Button prevCoursesButton = findViewById(R.id.view_prev_courses_button);
        Button scheduleButton = findViewById(R.id.view_schedule_button);
        Button viewStudentsButton = findViewById(R.id.view_students_button);
        Button viewProfileButton = findViewById(R.id.view_profile_button);

        prevCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(InstructorDashboardActivity.this, PrevCourseActivity.class);
                email = getIntent().getStringExtra("email");
                addCourse.putExtra("email", email);
                startActivity(addCourse);
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCourses = new Intent(InstructorDashboardActivity.this, ScheduleActivity.class);
                email = getIntent().getStringExtra("email");
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
