package com.example.trainingcenterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper{
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE User (email TEXT PRIMARY KEY, password varchar(15), firstName varchar(20), lastName varchar(20))");
        sqLiteDatabase.execSQL("CREATE TABLE Admin (email TEXT PRIMARY KEY, FOREIGN KEY (email) REFERENCES User(email))");
        sqLiteDatabase.execSQL("CREATE TABLE Instructor (email TEXT PRIMARY KEY, mobileNumber INTEGER, address TEXT, specialization TEXT, degree TEXT, FOREIGN KEY (email) REFERENCES User(email))");
        sqLiteDatabase.execSQL("CREATE TABLE Trainee (email TEXT PRIMARY KEY, mobileNumber INTEGER, address TEXT, FOREIGN KEY (email) REFERENCES User(email))");
        sqLiteDatabase.execSQL("CREATE TABLE Course (courseId INTEGER PRIMARY KEY autoincrement, title TEXT, mainTopics TEXT, prerequisites TEXT, instructorEmail TEXT, registrationDeadline TEXT, startDate TEXT, schedule TEXT, venue TEXT, FOREIGN KEY (instructorEmail) REFERENCES Instructor(email))");
        sqLiteDatabase.execSQL("CREATE TABLE Registration (registrationId INTEGER PRIMARY KEY, courseId INTEGER, traineeEmail TEXT, status TEXT, FOREIGN KEY (courseId) REFERENCES Course(courseId), FOREIGN KEY (traineeEmail) REFERENCES Trainee(email))");
        sqLiteDatabase.execSQL("CREATE TABLE InstructorCourse (instructorEmail TEXT, courseId INTEGER, FOREIGN KEY (instructorEmail) REFERENCES Instructor(email), FOREIGN KEY (courseId) REFERENCES Course(courseId))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertAdmin(Admin admin) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValuesUser = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", admin.getEmail());
        contentValues.put("password", admin.getPassword());
        contentValues.put("firstName", admin.getFirstName());
        contentValues.put("lastName", admin.getLastName());
        sqLiteDatabase.insert("User", null, contentValues);
        contentValuesUser.put("email", admin.getEmail());
        sqLiteDatabase.insert("Admin", null, contentValuesUser);
    }
    public void insertInstructor(Instructor instructor) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValuesUser = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", instructor.getEmail());
        contentValues.put("password", instructor.getPassword());
        contentValues.put("firstName", instructor.getFirstName());
        contentValues.put("lastName", instructor.getLastName());
        sqLiteDatabase.insert("User", null, contentValues);
        contentValuesUser.put("email", instructor.getEmail());
        contentValuesUser.put("mobileNumber", instructor.getMobileNumber());
        contentValuesUser.put("address", instructor.getAddress());
        contentValuesUser.put("specialization", instructor.getSpecialization());
        contentValuesUser.put("degree", instructor.getDegree());
        sqLiteDatabase.insert("Instructor", null, contentValuesUser);
        for (Course course : instructor.getCourses()) {
            //insertCourse(course);
            associateInstructorWithCourse(instructor.getEmail(), course.getCourseId());
        }
    }
    public void associateInstructorWithCourse(String instructorId, int courseId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("instructorEmail", instructorId);
        contentValues.put("courseId", courseId);
        sqLiteDatabase.insert("InstructorCourse", null, contentValues);
    }
    public void insertTrainee(Trainee trainee) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValuesUser = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", trainee.getEmail());
        contentValues.put("password", trainee.getPassword());
        contentValues.put("firstName", trainee.getFirstName());
        contentValues.put("lastName", trainee.getLastName());
        sqLiteDatabase.insert("User", null, contentValues);
        contentValuesUser.put("email", trainee.getEmail());
        contentValuesUser.put("mobileNumber", trainee.getMobileNumber());
        contentValuesUser.put("address", trainee.getAddress());
        sqLiteDatabase.insert("Trainee", null, contentValuesUser);
    }
    public void insertCourse(Course course) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("courseId", course.getCourseId());
        contentValues.put("title", course.getTitle());
        contentValues.put("mainTopics", course.getMainTopics());
        contentValues.put("prerequisites", course.getPrerequisites());
        contentValues.put("instructorEmail", course.getInstructorEmail());
        contentValues.put("registrationDeadline", course.getRegistrationDeadline());
        contentValues.put("startDate", course.getStartDate());
        contentValues.put("schedule", course.getSchedule());
        contentValues.put("venue", course.getVenue());
        sqLiteDatabase.insert("Course", null, contentValues);
    }

    public void insertRegistration(Registration Register) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("registrationId", Register.getRegistrationId()); // this is autoincremented, might cause errors.
        contentValues.put("courseId", Register.getCourseId()); // this is autoincremented, might cause errors.
        contentValues.put("traineeEmail", Register.getTraineeEmail());
        contentValues.put("status",Register.getStatus() );
        sqLiteDatabase.insert("Registration", null, contentValues);
    }
    public Cursor getAllAdmins() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM ADMIN", null);
    }
}
