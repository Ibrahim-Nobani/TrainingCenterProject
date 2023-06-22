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
        sqLiteDatabase.execSQL("CREATE TABLE User (userId INTEGER PRIMARY KEY, email TEXT, password TEXT, firstName TEXT, lastName TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Admin (userId INTEGER PRIMARY KEY, FOREIGN KEY (userId) REFERENCES User(userId))");
        sqLiteDatabase.execSQL("CREATE TABLE Instructor (userId INTEGER PRIMARY KEY, mobileNumber INTEGER, address TEXT, specialization TEXT, degree TEXT, FOREIGN KEY (userId) REFERENCES User(userId))");
        sqLiteDatabase.execSQL("CREATE TABLE Trainee (userId INTEGER PRIMARY KEY, mobileNumber INTEGER, address TEXT, FOREIGN KEY (userId) REFERENCES User(userId))");
        sqLiteDatabase.execSQL("CREATE TABLE Course (courseId INTEGER PRIMARY KEY autoincrement, title TEXT, mainTopics TEXT, prerequisites TEXT, instructorId INTEGER, registrationDeadline TEXT, startDate TEXT, schedule TEXT, venue TEXT, FOREIGN KEY (instructorId) REFERENCES Instructor(userId))");
        sqLiteDatabase.execSQL("CREATE TABLE Registration (registrationId INTEGER PRIMARY KEY, courseId INTEGER, traineeId INTEGER, status TEXT, FOREIGN KEY (courseId) REFERENCES Course(courseId), FOREIGN KEY (traineeId) REFERENCES Trainee(userId))");
        sqLiteDatabase.execSQL("CREATE TABLE InstructorCourse (instructorId INTEGER, courseId INTEGER, FOREIGN KEY (instructorId) REFERENCES Instructor(userId), FOREIGN KEY (courseId) REFERENCES Course(courseId))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertAdmin(Admin admin) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValuesUser = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", admin.getUserId());
        contentValues.put("email", admin.getEmail());
        contentValues.put("password", admin.getPassword());
        contentValues.put("firstName", admin.getFirstName());
        contentValues.put("lastName", admin.getLastName());
        sqLiteDatabase.insert("User", null, contentValues);
        contentValuesUser.put("userID", admin.getUserId());
        sqLiteDatabase.insert("Admin", null, contentValuesUser);
    }
    public void insertInstructor(Instructor instructor) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValuesUser = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", instructor.getUserId());
        contentValues.put("email", instructor.getEmail());
        contentValues.put("password", instructor.getPassword());
        contentValues.put("firstName", instructor.getFirstName());
        contentValues.put("lastName", instructor.getLastName());
        sqLiteDatabase.insert("User", null, contentValues);
        contentValuesUser.put("userID", instructor.getUserId());
        contentValuesUser.put("mobileNumber", instructor.getMobileNumber());
        contentValuesUser.put("address", instructor.getAddress());
        contentValuesUser.put("specialization", instructor.getSpecialization());
        contentValuesUser.put("degree", instructor.getDegree());
        sqLiteDatabase.insert("Instructor", null, contentValuesUser);
        for (Course course : instructor.getCourses()) {
            //insertCourse(course);
            associateInstructorWithCourse(instructor.getUserId(), course.getCourseId());
        }
    }
    public void associateInstructorWithCourse(int instructorId, int courseId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("instructorId", instructorId);
        contentValues.put("courseId", courseId);
        sqLiteDatabase.insert("InstructorCourse", null, contentValues);
    }
    public void insertTrainee(Trainee trainee) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValuesUser = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", trainee.getUserId());
        contentValues.put("email", trainee.getEmail());
        contentValues.put("password", trainee.getPassword());
        contentValues.put("firstName", trainee.getFirstName());
        contentValues.put("lastName", trainee.getLastName());
        sqLiteDatabase.insert("User", null, contentValues);
        contentValuesUser.put("userID", trainee.getUserId());
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
        contentValues.put("instructorId", course.getInstructorId());
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
        contentValues.put("traineeId", Register.getTraineeId());
        contentValues.put("status",Register.getStatus() );
        sqLiteDatabase.insert("Registration", null, contentValues);
    }
    public Cursor getAllAdmins() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM ADMIN", null);
    }
}
