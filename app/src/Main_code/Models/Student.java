package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dbutils.DBUtils;
import dbutils.DataContract;

public class Student {
    public int getLectureID() {
        return lectureID;
    }

    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    int lectureID;
    public int getAbsentPresent() {
        return absentPresent;
    }

    public void setAbsentPresent(int absentPresent) {
        this.absentPresent = absentPresent;
    }

    /**
     * 0=absent
     * 1= present
     */
    private int absentPresent;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    private int _id;
    private String name;
    private String tNumber;
    private String phoneNumber;
    private String emailId;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String gettNumber() {
        return tNumber;
    }

    public void settNumber(String tNumber) {
        this.tNumber = tNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(String name, String tNumber, String phoneNumber, String emailId) {
        this.name = name;
        this.tNumber = tNumber;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public Student(Context context) {

    }


    public void addStudent(Context context) {
        DBUtils dbUtils = new DBUtils(context);
        SQLiteDatabase sqLiteDatabase = dbUtils.open();
        ContentValues cv = new ContentValues();
        cv.put(DataContract.Student.NAME, name);
        cv.put(DataContract.Student.EMAIL_ID, emailId);
        cv.put(DataContract.Student.PHONE_NUMBER, phoneNumber);
        cv.put(DataContract.Student.TNUMBER, tNumber);
        sqLiteDatabase.insert(DataContract.Student.TABLE_NAME, null, cv);
        dbUtils.close();
    }

    public List<Student> getStudents(Context context, String where) {
        List<Student> students = null;
        DBUtils dbUtils = new DBUtils(context);
        SQLiteDatabase sqLiteDatabase = dbUtils.open();
        Cursor cursor = sqLiteDatabase.query(DataContract.Student.TABLE_NAME, null, where, null, null, null, null);
        if (cursor != null)
            students = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                Student student = new Student(

                        cursor.getString(cursor.getColumnIndex(DataContract.Student.NAME)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Student.TNUMBER)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Student.PHONE_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Student.EMAIL_ID)));
                student.set_id(cursor.getInt(cursor.getColumnIndex(DataContract._ID)));

                students.add(student);
            } while (cursor.moveToNext());
        }
        dbUtils.close();

        return students;
    }
}

