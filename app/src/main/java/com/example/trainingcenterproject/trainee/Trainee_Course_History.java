package com.example.trainingcenterproject.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;

public class Trainee_Course_History extends AppCompatActivity {

    private String email;
    LinearLayout courses;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_course_history);
        courses = (LinearLayout) findViewById(R.id.trainee_past_courses);
        back = (Button) findViewById(R.id.trainee_past_courses_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Trainee_Course_History.this,TraineeMainActivity.class);
                startActivity(back);
            }
        });

        loadUserDetails();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(Trainee_Course_History.this,"training", null,1);
        Cursor allCoursesCursor = dataBaseHelper.getTraineePastCourses(email);
        generateCards(allCoursesCursor);
    }

    private void loadUserDetails() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(Trainee_Course_History.this,"training", null,1);
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
    }

    private void generateCards(Cursor cursor){
        courses.removeAllViews();
        while (cursor.moveToNext()){
            CardView cardView = new CardView(new ContextThemeWrapper(Trainee_Course_History.this, R.style.CardViewStyle), null, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            LinearLayout insideCard = new LinearLayout(Trainee_Course_History.this);
            insideCard.setOrientation(LinearLayout.VERTICAL);
            params.setMargins(0, 0, 0, 10);
            insideCard.setLayoutParams(params);
            cardView.setLayoutParams(params);
            TextView textView = new TextView(Trainee_Course_History.this);
            textView.setText(String.format("Title= %s\n", cursor.getString(0)));
            textView.setPadding(10,5,10,5);
            textView.setTextSize(18);
            textView.setTextColor(Color.parseColor("#000000"));
            insideCard.addView(textView,0);
            TextView textView2 = new TextView(Trainee_Course_History.this);
            textView2.setText(String.format("Start Date: %s\n End Date:%s\n Instructor Name: %s", cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            textView2.setPadding(10,5,10,5);
            textView2.setTextSize(18);
            textView2.setTextColor(Color.parseColor("#000000"));
            textView2.setVisibility(View.GONE);
            insideCard.addView(textView2,1);
            cardView.addView(insideCard);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textView2.getVisibility() == View.GONE){
                        textView2.setVisibility(View.VISIBLE);
                    } else{
                        textView2.setVisibility(View.GONE);
                    }
                }
            });
            courses.addView(cardView);
            courses.refreshDrawableState();
        }
    }
}