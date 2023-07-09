package com.example.trainingcenterproject.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;

public class TraineeSearchCourses extends AppCompatActivity {

    Button back;
    Button search;
    EditText searchBar;
    LinearLayout courses;

    Switch available;
    Boolean searching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_search_courses);

        back = (Button) findViewById(R.id.trainee_course_search_back_button);
        search = (Button) findViewById(R.id.trainee__course_search_button);
        searchBar = (EditText) findViewById(R.id.trainee_course_name_search);
        courses = (LinearLayout) findViewById(R.id.trainee_courses_search);
        available = (Switch) findViewById(R.id.trainee_aivailable_courses_switch);
        available.setChecked(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(TraineeSearchCourses.this,TraineeMainActivity.class);
                startActivity(back);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchBar.getText().toString().isEmpty()){
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeSearchCourses.this,"training", null,1);
                    Cursor CoursesCursor;
                    if(!searching){
                        CoursesCursor = (available.isChecked()) ? dataBaseHelper.getAvailableCourses("%"+searchBar.getText().toString()+"%") : dataBaseHelper.getPastCourses("%"+searchBar.getText().toString()+"%") ;
                        if (CoursesCursor.getCount() > 0)
                            if (available.isChecked())
                                generateCards(CoursesCursor);
                            else
                                generateCards4Past(CoursesCursor);
                        else{
                            courses.removeAllViews();
                            TextView textView = new TextView(TraineeSearchCourses.this);
                            textView.setText("No Courses Were Found Under The provided Name");
                            textView.setPadding(10,5,10,5);
                            textView.setTextSize(22);
                            textView.setTextColor(Color.parseColor("#000000"));
                            TextView textView2 = new TextView(TraineeSearchCourses.this);
                            textView2.setText("No Courses Were Found Under The provided Name");
                            textView2.setPadding(10,5,10,5);
                            textView2.setTextSize(22);
                            textView2.setTextColor(Color.parseColor("#000000"));
                            courses.addView(textView);
                            courses.addView(textView2);
                        }
                        searching = true;
                        //search.setBackgroundResource(R.drawable.ic_action_cancel);
                    }else{
                        if ((available.isChecked())) {
                            CoursesCursor = dataBaseHelper.getAllAvailableCourses();
                            generateCards(CoursesCursor);
                        } else {
                            CoursesCursor = dataBaseHelper.getAllPastCourses();
                            generateCards4Past(CoursesCursor);
                        }
                        searching = false;
                        searchBar.setText("");
                        //search.setBackgroundResource(R.drawable.ic_action_search);
                    }
                }
            }
        });

        available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeSearchCourses.this,"training", null,1);
                Cursor CoursesCursor;
                if(b){
                    CoursesCursor = dataBaseHelper.getAllAvailableCourses();
                    generateCards(CoursesCursor);
                } else {
                    CoursesCursor = dataBaseHelper.getAllPastCourses();
                    generateCards4Past(CoursesCursor);
                }
                searching = false;
                searchBar.setText("");
            }
        });
    }

    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeSearchCourses.this,"training", null,1);
        Cursor allCoursesCursor = dataBaseHelper.getAllAvailableCourses();
        generateCards(allCoursesCursor);
    }

    private void generateCards(Cursor cursor){
        courses.removeAllViews();
        while (cursor.moveToNext()){
            CardView cardView = new CardView(new ContextThemeWrapper(TraineeSearchCourses.this, R.style.CardViewStyle), null, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            LinearLayout insideCard = new LinearLayout(TraineeSearchCourses.this);
            insideCard.setOrientation(LinearLayout.VERTICAL);
            params.setMargins(0, 0, 0, 10);
            insideCard.setLayoutParams(params);
            cardView.setLayoutParams(params);
            TextView textView = new TextView(TraineeSearchCourses.this);
            textView.setText(String.format("Title= %s\n", cursor.getString(0)));
            textView.setPadding(10,5,10,5);
            textView.setTextSize(18);
            textView.setTextColor(Color.parseColor("#000000"));
            insideCard.addView(textView,0);
            TextView textView2 = new TextView(TraineeSearchCourses.this);
            textView2.setText(String.format("Prerequisites: %s\n Start Date:%s\n Schedule: %s\n Instructor: %s\n", cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)));
            textView2.setPadding(10,5,10,5);
            textView2.setTextSize(18);
            textView2.setTextColor(Color.parseColor("#000000"));
            textView2.setVisibility(View.GONE);
            insideCard.addView(textView2,1);
            Button enroll = new Button(TraineeSearchCourses.this);
            enroll.setText("Enroll");
            enroll.setWidth(40);
            enroll.setHeight(30);
            enroll.setTextColor(Color.parseColor("#000000"));
            enroll.setBackgroundColor(Color.parseColor("#0aa632"));
            enroll.setVisibility(View.GONE);
            enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });
            insideCard.addView(enroll);
            cardView.addView(insideCard);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textView2.getVisibility() == View.GONE){
                        textView2.setVisibility(View.VISIBLE);
                        enroll.setVisibility(View.VISIBLE);
                    } else{
                        textView2.setVisibility(View.GONE);
                        enroll.setVisibility(View.GONE);
                    }
                }
            });
            courses.addView(cardView);
            courses.refreshDrawableState();
        }
    }

    private void generateCards4Past(Cursor cursor){
        courses.removeAllViews();
        while (cursor.moveToNext()){
            CardView cardView = new CardView(new ContextThemeWrapper(TraineeSearchCourses.this, R.style.CardViewStyle), null, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            LinearLayout insideCard = new LinearLayout(TraineeSearchCourses.this);
            insideCard.setOrientation(LinearLayout.VERTICAL);
            params.setMargins(0, 0, 0, 10);
            insideCard.setLayoutParams(params);
            cardView.setLayoutParams(params);
            TextView textView = new TextView(TraineeSearchCourses.this);
            textView.setText(String.format("Title= %s\n", cursor.getString(0)));
            textView.setPadding(10,5,10,5);
            textView.setTextSize(18);
            textView.setTextColor(Color.parseColor("#000000"));
            insideCard.addView(textView,0);
            TextView textView2 = new TextView(TraineeSearchCourses.this);
            textView2.setText(String.format("Date Started:%s\n Date Ended: %s\n Instructor: %s\n", cursor.getString(1), cursor.getString(2), cursor.getString(3)));
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