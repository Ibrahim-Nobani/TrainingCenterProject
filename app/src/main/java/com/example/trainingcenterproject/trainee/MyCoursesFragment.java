package com.example.trainingcenterproject.trainee;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCoursesFragment extends Fragment {

    LinearLayout courses;
    Context thiscontext;

    String email;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCoursesFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myCoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCoursesFragment newInstance(String param1, String param2) {
        MyCoursesFragment fragment = new MyCoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thiscontext = container.getContext();
        email = this.getArguments().getString("email");
        View view = inflater.inflate(R.layout.fragment_my_courses, container, false);
        courses = (LinearLayout) view.findViewById(R.id.trainee_courses_fragment);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(thiscontext,"training", null,1);
        Cursor allCoursesCursor = dataBaseHelper.getTraineeCourses(email);
        generateCards(allCoursesCursor);
        return view;
    }

    private void generateCards(Cursor cursor){
        courses.removeAllViews();
        while (cursor.moveToNext()){
            CardView cardView = new CardView(new ContextThemeWrapper(thiscontext, R.style.CardViewStyle), null, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            LinearLayout insideCard = new LinearLayout(thiscontext);
            insideCard.setOrientation(LinearLayout.VERTICAL);
            params.setMargins(0, 0, 0, 10);
            insideCard.setLayoutParams(params);
            cardView.setLayoutParams(params);
            TextView textView = new TextView(thiscontext);
            textView.setText(String.format("Title= %s\n", cursor.getString(0)));
            textView.setPadding(10,5,10,5);
            textView.setTextSize(18);
            textView.setTextColor(Color.parseColor("#000000"));
            insideCard.addView(textView,0);
            TextView textView2 = new TextView(thiscontext);
            textView2.setText(String.format("Start Date: %s\n Schedule:%s\n Venue:%s\n Instuctor Name: %s\n Instructor Email: %s", cursor.getString(2), cursor.getString(3), cursor.getString(1),cursor.getString(5),cursor.getString(4)));
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