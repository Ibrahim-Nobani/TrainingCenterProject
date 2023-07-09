package com.example.trainingcenterproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PrevCourseActivity extends AppCompatActivity {

    private String email;
    LinearLayout courseList;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        courseList = findViewById(R.id.courseList);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrevCourseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(PrevCourseActivity.this,"training", null,1);
        email = getIntent().getStringExtra("email");
        Cursor allCoursesCursor = dataBaseHelper.getCoursesByInstructor(email);
        courseList.removeAllViews();
        while (allCoursesCursor.moveToNext()){
            CardView cardView = new CardView(new ContextThemeWrapper(PrevCourseActivity.this, R.style.CardViewStyle), null, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            cardView.setLayoutParams(params);

            TextView textView = new TextView(PrevCourseActivity.this);
            textView.setText(
                    "ID= "+allCoursesCursor.getString(0)
                            +"\nTitle= "+allCoursesCursor.getString(1)
                            +"\nmainTopics= "+allCoursesCursor.getString(2)
                            +"\nPre= "+allCoursesCursor.getString(3)
                            +"\n\n" );
            textView.setPadding(10,10,10,10);
            textView.setTextSize(18);
            textView.setTextColor(Color.parseColor("#000000"));
            cardView.addView(textView);
            courseList.addView(cardView);
        }
    }
}

