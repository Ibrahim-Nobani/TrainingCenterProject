package com.example.trainingcenterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);

        Button createCourseButton = findViewById(R.id.create_course_button);
        Button editDeleteCourseButton = findViewById(R.id.edit_delete_course_button);
        Button decisionRegistrationButton = findViewById(R.id.decision_registration_button);
        Button viewProfilesButton = findViewById(R.id.view_profiles_button);
        Button viewCoursesButton = findViewById(R.id.view_courses);

        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, AddCourseActivity.class);
                startActivity(addCourse);
            }
        });

        viewCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCourses = new Intent(AdminDashboardActivity.this, CourseListActivity.class);
                startActivity(viewCourses);
            }
        });

        editDeleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, DeleteCourseActivity.class);
                startActivity(addCourse);


            }
        });



        decisionRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, PendingCoursesActivity.class);
                startActivity(addCourse);
            }
        });

        viewProfilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, ViewAllActivity.class);
                startActivity(addCourse);
            }
        });


    }
}
