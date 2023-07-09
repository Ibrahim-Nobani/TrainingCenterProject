package com.example.trainingcenterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.trainingcenterproject.trainee.Notification;

import java.util.ArrayList;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {
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
        sqLiteDatabase.execSQL("CREATE TABLE Registration (registrationId INTEGER PRIMARY KEY autoincrement, courseId INTEGER, traineeEmail TEXT, status TEXT, FOREIGN KEY (courseId) REFERENCES Course(courseId), FOREIGN KEY (traineeEmail) REFERENCES Trainee(email))");
        sqLiteDatabase.execSQL("CREATE TABLE InstructorCourse (instructorEmail TEXT, courseId INTEGER, FOREIGN KEY (instructorEmail) REFERENCES Instructor(email), FOREIGN KEY (courseId) REFERENCES Course(courseId))");
        sqLiteDatabase.execSQL("CREATE TABLE Notifications(id INTEGER PRIMARY KEY autoincrement, userEmail TEXT, content TEXT, FOREIGN KEY (userEmail) REFERENCES USER(email))");
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
        ArrayList<Course> courses = instructor.getCourses();
        if (courses != null) {
            for (Course course : courses) {
                associateInstructorWithCourse(instructor.getEmail(), course.getCourseId());
            }
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
    public boolean updateInstructorCourse(String instructorEmail, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("instructorEmail", instructorEmail);

        return db.update("InstructorCourse", contentValues, "courseId = ?", new String[] {String.valueOf(courseId)}) > 0;
    }


    public void insertCourse(Course course) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", course.getTitle());
        contentValues.put("mainTopics", course.getMainTopics());
        contentValues.put("prerequisites", course.getPrerequisites());
        contentValues.put("instructorEmail", course.getInstructorEmail());
        contentValues.put("registrationDeadline", course.getRegistrationDeadline());
        contentValues.put("startDate", course.getStartDate());
        contentValues.put("schedule", course.getSchedule());
        contentValues.put("venue", course.getVenue());
        long courseId =  sqLiteDatabase.insert("Course", null, contentValues);
        course.setCourseId((int) courseId);
        sqLiteDatabase.close();
    }

    public void insertRegistration(Registration Register) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("courseId", Register.getCourseId()); // this is autoincremented, might cause errors.
        contentValues.put("traineeEmail", Register.getTraineeEmail());
        contentValues.put("status", Register.getStatus());
        sqLiteDatabase.insert("Registration", null, contentValues);
    }

    public void insertRegistrationInfo(int courseId, String traineeEmail, String status) {
        Registration Register = new Registration(courseId, traineeEmail, status);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("courseId", Register.getCourseId()); // this is autoincremented, might cause errors.
        contentValues.put("traineeEmail", Register.getTraineeEmail());
        contentValues.put("status", Register.getStatus());
        sqLiteDatabase.insert("Registration", null, contentValues);
    }

    public void insertNotification(Notification notification){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userEmail",notification.getUserEmail());
        contentValues.put("content",notification.getContent());
        sqLiteDatabase.insert("Notifications", null, contentValues);
    }

    public Cursor getUserNotification(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * from Notifications where userEmail = ?",new String[]{email});
    }

    public void deleteNotification(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete("Notifications","id = ?",new String[]{String.valueOf(id)});
    }

    public Cursor getAllAdmins() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT Admin.email, User.firstName, User.lastName FROM Admin INNER JOIN User ON Admin.email = User.email", null);
    }

    public Cursor getAllTrainees() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Trainee INNER JOIN User ON Trainee.email = User.email", null);
    }

    public Trainee getTraineeDetails(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor trainee = sqLiteDatabase.rawQuery("SELECT User.*, Trainee.*\n" +
                "FROM User\n" +
                "JOIN Trainee ON User.email = Trainee.email\n" +
                "WHERE User.email = ?", new String[]{email});
        if (!trainee.moveToFirst()) {
            trainee.close();
            return null;
        } else{
            return new Trainee(trainee.getString(0),trainee.getString(1),trainee.getString(2),trainee.getString(3),Integer.parseInt(trainee.getString(5)),trainee.getString(6),null);
        }
    }

    public void updateTrainee(Trainee trainee){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        int delete = sqLiteDatabase.delete("Trainee","email = ?",new String[]{trainee.getEmail()});
        delete = sqLiteDatabase.delete("User","email = ?",new String[]{trainee.getEmail()});
        insertTrainee(trainee);
    }

    public Cursor getAllInstructors() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Instructor INNER JOIN User ON Instructor.email = User.email", null);
    }

    public Cursor getAllCourses() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Course", null);
    }

    public Cursor getCourses(String name){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM course WHERE title LIKE  ? ", new String[]{name});
    }

    public Cursor getTraineeCourses(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT Course.title, Course.venue, Course.startDate, Course.schedule, Instructor.email AS instructorEmail, User.firstName || ' ' || User.lastName AS instructorName " +
                "FROM Course " +
                "JOIN Registration ON Course.courseId = Registration.courseId " +
                "JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId " +
                "JOIN Instructor ON InstructorCourse.instructorEmail = Instructor.email " +
                "JOIN User ON Instructor.email = User.email " +
                "WHERE Registration.traineeEmail = ? " +
                "AND DATE(Course.registrationDeadline) > DATE('now')",new String[]{email});
    }


    public Cursor getTraineePastCourses(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(String.format("SELECT Course.title, Course.startDate,Course.registrationDeadline, User.firstName || ' ' || User.lastName AS instructorName FROM Course JOIN Registration ON Course.courseId = Registration.courseId JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId JOIN Instructor ON InstructorCourse.instructorEmail = Instructor.email JOIN User ON Instructor.email = User.email WHERE Registration.traineeEmail = ? AND DATE(Course.registrationDeadline) < DATE('now')"),new String[]{email});
    }

    public Cursor getAllAvailableCourses(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(String.format("SELECT DISTINCT Course.title,Course.prerequisites,Course.startDate,Course.schedule, User.firstName || ' ' || User.lastName AS instructorName FROM Course JOIN Registration ON Course.courseId = Registration.courseId JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId JOIN Instructor ON InstructorCourse.instructorEmail = Instructor.email JOIN User ON Instructor.email = User.email WHERE DATE(Course.registrationDeadline) > DATE('now')"),null);
    }

    public Cursor getAllPastCourses(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT DISTINCT Course.title, Course.startDate,Course.registrationDeadline, User.firstName || ' ' || User.lastName AS instructorName FROM Course JOIN Registration ON Course.courseId = Registration.courseId JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId JOIN Instructor ON InstructorCourse.instructorEmail = Instructor.email JOIN User ON Instructor.email = User.email WHERE DATE(Course.registrationDeadline) < DATE('now')",null);
    }

    public Cursor getAvailableCourses(String name){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(String.format("SELECT DISTINCT Course.title, Course.startDate,Course.registrationDeadline, User.firstName || ' ' || User.lastName AS instructorName FROM Course JOIN Registration ON Course.courseId = Registration.courseId JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId JOIN Instructor ON InstructorCourse.instructorEmail = Instructor.email JOIN User ON Instructor.email = User.email WHERE DATE(Course.registrationDeadline) > DATE('now') AND Course.title LIKE ?"),new String[]{name});
    }

    public Cursor getPastCourses(String name){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(String.format("SELECT DISTINCT Course.title,Course.startDate,Course.registrationDeadline, User.firstName || ' ' || User.lastName AS instructorName FROM Course JOIN Registration ON Course.courseId = Registration.courseId JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId JOIN Instructor ON InstructorCourse.instructorEmail = Instructor.email JOIN User ON Instructor.email = User.email WHERE DATE(Course.registrationDeadline) < DATE('now')AND Course.title LIKE ?"),new String[]{name});
    }

    public Cursor getTraineesForCourse(int courseId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT Trainee.email, User.firstName, User.lastName " +
                        "FROM Trainee " +
                        "INNER JOIN User ON Trainee.email = User.email " +
                        "INNER JOIN Registration ON Trainee.email = Registration.traineeEmail " +
                        "WHERE Registration.courseId = ? AND Registration.status = 'Confirmed'",
                new String[]{String.valueOf(courseId)});
    }

    public Cursor getPendingCourses() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT Course.courseId, Course.title, Course.venue, Course.startDate, Course.schedule, Instructor.email AS instructorEmail, Instructor.name AS instructorName " +
                "FROM Course " +
                "JOIN Registration ON Course.courseId = Registration.courseId " +
                "JOIN Instructor ON Course.instructorEmail = Instructor.email " +
                "WHERE Registration.traineeEmail = ? " +
                "AND DATE(Course.registrationDeadline) > DATE('now')", null);
    }

    public void acceptRegistration(String courseName, String traineeEmail) {
        SQLiteDatabase db = getWritableDatabase();

        // Obtain the courseId from the course name
        Cursor cursor = db.rawQuery("SELECT courseId FROM Course WHERE title = ?", new String[]{courseName});
        if (cursor.moveToFirst()) {
            String courseId = cursor.getString(0);

            ContentValues values = new ContentValues();
            values.put("status", "Confirmed");

            db.update("Registration", values, "courseId=? AND traineeEmail=?", new String[]{courseId, traineeEmail});
        }
        cursor.close();
        db.close();
    }

    public void rejectRegistration(String courseName, String traineeEmail) {
        SQLiteDatabase db = getWritableDatabase();

        // Obtain the courseId from the course name
        Cursor cursor = db.rawQuery("SELECT courseId FROM Course WHERE title = ?", new String[]{courseName});
        if (cursor.moveToFirst()) {
            String courseId = cursor.getString(0);

            ContentValues values = new ContentValues();
            values.put("status", "Rejected");

            db.update("Registration", values, "courseId=? AND traineeEmail=?", new String[]{courseId, traineeEmail});
        }
        cursor.close();
        db.close();
    }

    public Course getCourse(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Course WHERE courseId = ?", new String[]{String.valueOf(courseId)});

        if (cursor.moveToFirst()) {
            Course course = new Course();
            course.setCourseId(Integer.parseInt(cursor.getString(0)));
            course.setTitle(cursor.getString(1));
            course.setMainTopics(cursor.getString(2));


            cursor.close();
            db.close();
            return course;
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM User WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor.getString(2);
    }

        public String getUserRole(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM User WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        cursor.close();


        String[] tables = new String[]{"Admin", "Instructor", "Trainee"};
        String[] roles = new String[]{"admin", "instructor", "trainee"};

        for (int i = 0; i < tables.length; i++) {
            String table = tables[i];
            String role = roles[i];

            query = "SELECT * FROM " + table + " WHERE email = ?";
            cursor = db.rawQuery(query, new String[]{email});

            if (cursor.moveToFirst()) {
                cursor.close();
                return role;
            }

            cursor.close();
        }

        return null;
    }

    public boolean deleteCourse(int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Course", "courseId = ?", new String[]{String.valueOf(courseId)}) > 0;
    }

    public boolean updateCourse(int courseId, String title, String mainTopics, String prerequisites, String instructorEmail, String registrationDeadline, String startDate, String schedule, String venue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("mainTopics", mainTopics);
        contentValues.put("prerequisites", prerequisites);
        contentValues.put("instructorEmail", instructorEmail);
        contentValues.put("registrationDeadline", registrationDeadline);
        contentValues.put("startDate", startDate);
        contentValues.put("schedule", schedule);
        contentValues.put("venue", venue);
        associateInstructorWithCourse(instructorEmail, courseId);
        return db.update("Course", contentValues, "courseId = ?", new String[] {String.valueOf(courseId)}) > 0;
    }
    public Cursor getCoursesByInstructor(String instructorEmail) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(
                "SELECT Course.* " +
                        "FROM Course " +
                        "INNER JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId " +
                        "WHERE InstructorCourse.instructorEmail = ?",
                new String[]{instructorEmail}
        );
    }

    public Cursor getCurrentCoursesByInstructor(String instructorEmail) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(
                "SELECT Course.* " +
                        "FROM Course " +
                        "INNER JOIN InstructorCourse ON Course.courseId = InstructorCourse.courseId " +
                        "WHERE InstructorCourse.instructorEmail = ? " +
                        "AND Course.registrationDeadline > datetime('now')",
                new String[]{instructorEmail}
        );
    }


    public boolean updateInstructor(String email, String firstName, String lastName, String password, String mobileNumber, String address, String specialization, String degree) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValuesInstructor = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("password", password);
        contentValuesInstructor.put("email", email);
        contentValuesInstructor.put("mobileNumber", mobileNumber);
        contentValuesInstructor.put("address", address);
        contentValuesInstructor.put("specialization", specialization);
        contentValuesInstructor.put("degree", degree);
        db.update("User",contentValues,null,null);
        return db.update("Instructor", contentValuesInstructor, "email = ?", new String[] {email}) > 0;
    }
    public Cursor getTraineesForInstructor(String instructorEmail) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT Trainee.email, User.firstName, User.lastName " +
                        "FROM Trainee " +
                        "INNER JOIN User ON Trainee.email = User.email " +
                        "INNER JOIN Registration ON Trainee.email = Registration.traineeEmail " +
                        "INNER JOIN Course ON Registration.courseId = Course.courseId " +
                        "WHERE Course.instructorEmail = ? AND Registration.status = 'Confirmed'",
                new String[]{instructorEmail});
    }

    public void addDummyData() {
        SQLiteDatabase db = getWritableDatabase();
        // Clear all existing data from the tables
        db.delete("User", null, null);
        db.delete("Admin", null, null);
        db.delete("Instructor", null, null);
        db.delete("Trainee", null, null);
        db.delete("Course", null, null);
        db.delete("Registration", null, null);
        db.delete("InstructorCourse", null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='Course'");
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='Registration'");




        // Add dummy admins
        Admin admin1 = new Admin("a", "a", "John", "Doe", "p.png");
        insertAdmin(admin1);

        Admin admin2 = new Admin("admin2@example.com", "admin456", "Jane", "Smith", "p.png");
        insertAdmin(admin2);

        // Add dummy instructors
        Instructor instructor1 = new Instructor("i", "i", "Alice", "Smith", 1234567890, "123 Street, City", "Mathematics", "M.Sc", null, "");
        insertInstructor(instructor1);

        Instructor instructor2 = new Instructor("instructor2@example.com", "instructor2pass", "Bob", "Johnson", 96543210, "456 Avenue, Town", "Physics", "Ph.D", null, "");
        insertInstructor(instructor2);

        Instructor instructor3 = new Instructor("instructor3@example.com", "instructor3pass", "Eva", "Lee", 55555555, "789 Road, Village", "Chemistry", "B.Sc", null, "");
        insertInstructor(instructor3);


        // Add dummy trainees
        Trainee trainee1 = new Trainee("trainee1@example.com", "trainee1pass", "Emma", "Brown", 433443, "789 Road, Village",  "p.png");
        insertTrainee(trainee1);

        Trainee trainee2 = new Trainee("trainee2@example.com", "trainee2pass", "Jacob", "Davis", 433443, "456 Lane, County","p.png");
        insertTrainee(trainee2);

        Trainee trainee3 = new Trainee("trainee3@example.com", "trainee3pass", "Olivia", "Wilson", 433443, "321 Boulevard, State", "d");
        insertTrainee(trainee3);

        Trainee trainee4 = new Trainee("trainee4@example.com", "trainee4pass", "Sophia", "Miller", 433443, "123 Street, City",  "p.png");
        insertTrainee(trainee4);

        Trainee trainee5 = new Trainee("trainee5@example.com", "trainee5pass", "Liam", "Anderson", 433443, "456 Avenue, Town","p.png");
        insertTrainee(trainee5);

        Trainee trainee6 = new Trainee("trainee6@example.com", "trainee6pass", "Ava", "Taylor", 433443, "789 Road, Village", "d");
        insertTrainee(trainee6);


        // Add dummy courses
        Course course1 = new Course( "Basic math concepts", "None", "",instructor1.getEmail(), "2023-07-09", "2023-07-10", "mon 10:00 wed 10:00", "Conference Room A");
        insertCourse(course1);

        Course course2 = new Course( "Motion, forces, and energy", "None", "", instructor2.getEmail(), "2023-07-08", "2023-07-15", "mon 11:00 wed 12:00", "Laboratory B" );
        insertCourse(course2);

        Course course3 = new Course( "Atoms, molecules, and reactions", "None",  "", instructor3.getEmail(), "2023-07-06", "2023-07-20", "mon 8:00 wed 8:00", "Laboratory C");
        insertCourse(course3);

        // Associate instructors with courses
        associateInstructorWithCourse(instructor1.getEmail(), course1.getCourseId());
        associateInstructorWithCourse(instructor1.getEmail(), course2.getCourseId());
        associateInstructorWithCourse(instructor2.getEmail(), course2.getCourseId());
        associateInstructorWithCourse(instructor3.getEmail(), course3.getCourseId());

        // Add dummy registrations
        Registration registration1 = new Registration(course1.getCourseId(), trainee1.getEmail(), "Pending");
        insertRegistration(registration1);

        Registration registration2 = new Registration(course2.getCourseId(), trainee2.getEmail(), "Confirmed");
        insertRegistration(registration2);

        Registration registration3 = new Registration(course3.getCourseId(), trainee3.getEmail(), "Pending");
        insertRegistration(registration3);

        Registration registration4 = new Registration(course1.getCourseId(), trainee4.getEmail(), "Pending");
        insertRegistration(registration4);

        Registration registration5 = new Registration(course2.getCourseId(), trainee5.getEmail(), "Confirmed");
        insertRegistration(registration5);

        Registration registration6 = new Registration(course3.getCourseId(), trainee6.getEmail(), "Pending");
        insertRegistration(registration6);


        Registration registration7 = new Registration(course1.getCourseId(), trainee2.getEmail(), "Confirmed");
        insertRegistration(registration7);

        Registration registration8 = new Registration(course2.getCourseId(), trainee1.getEmail(), "Pending");
        insertRegistration(registration8);

        Registration registration9 = new Registration(course3.getCourseId(), trainee4.getEmail(), "Confirmed");
        insertRegistration(registration9);

        Registration registration10 = new Registration(course1.getCourseId(), trainee3.getEmail(), "Pending");
        insertRegistration(registration10);

        Registration registration11 = new Registration(course2.getCourseId(), trainee6.getEmail(), "Confirmed");
        insertRegistration(registration11);

        Registration registration12 = new Registration(course3.getCourseId(), trainee5.getEmail(), "Pending");
        insertRegistration(registration12);

        Notification notification1 = new Notification("trainee1@example.com","This is a test");
        insertNotification(notification1);

        // Close the database connection
        db.close();


    }

}
