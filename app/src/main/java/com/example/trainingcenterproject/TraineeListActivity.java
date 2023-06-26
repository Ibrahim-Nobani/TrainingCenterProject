package com.example.trainingcenterproject;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

public class TraineeListActivity extends AppCompatActivity {
    LinearLayout xLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);
        LinearLayout firstLinearLayout=new LinearLayout(this);
        Button xbackButton = new Button(this);
        xLinearLayout= new LinearLayout(this);
        ScrollView scrollView=new ScrollView(this);
        firstLinearLayout.setOrientation(LinearLayout.VERTICAL);
        xLinearLayout.setOrientation(LinearLayout.VERTICAL);

        xbackButton.setText("Back To Main");
        xbackButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        firstLinearLayout.addView(xbackButton);
        scrollView.addView(xLinearLayout);
        firstLinearLayout.addView(scrollView);

        setContentView(firstLinearLayout);

        xbackButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(TraineeListActivity.this,MainActivity.class);
                TraineeListActivity.this.startActivity(intent);
                finish();
            }
        });
    }
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper =new DataBaseHelper(TraineeListActivity.this,"training", null,1);
        Cursor allCoursesCursor = dataBaseHelper.getAllTrainees();
        xLinearLayout.removeAllViews();
        while (allCoursesCursor.moveToNext()){
            TextView textView =new TextView(TraineeListActivity.this);
            textView.setText(
                    "Email= "+allCoursesCursor.getString(0)
                            +"\nFirstName= "+allCoursesCursor.getString(1)
                            +"\nLastName= "+allCoursesCursor.getString(2)
                            +"\n\n" );
            xLinearLayout.addView(textView);
        }
    }
}