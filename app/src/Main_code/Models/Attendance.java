package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dbutils.DBUtils;
import dbutils.DataContract;


public class Attendance {
    public Attendance() {


    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    private int _id;


    public Attendance(int markAttendance, int lectureID, int studentID) {
        this.markAttendance = markAttendance;
        this.lectureID = lectureID;
        this.studentID = studentID;
    }

   public void addAttendance(Context context) {
        DBUtils dbUtils = new DBUtils(context);
        SQLiteDatabase sqLiteDatabase = dbUtils.open();
        ContentValues cv = new ContentValues();
        cv.put(DataContract.Attendance.LECTURE_ID, lectureID);
        cv.put(DataContract.Attendance.MARK_ATTENDANCE, markAttendance);
        cv.put(DataContract.Attendance.STUDENT_ID, studentID);
        sqLiteDatabase.insert(DataContract.Attendance.TABLE_NAME, null, cv);
        dbUtils.close();
    }

    public List<Attendance> getAttendance(Context context, String where) {
        List<Attendance> attendances = null;
        DBUtils dbUtils = new DBUtils(context);
        SQLiteDatabase sqLiteDatabase = dbUtils.open();
        Cursor cursor = sqLiteDatabase.query(DataContract.Attendance.TABLE_NAME, null, where, null, null, null, null);
        if (cursor != null)
            attendances = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                Attendance attendance = new Attendance(
                        cursor.getInt(cursor.getColumnIndex(DataContract.Attendance.MARK_ATTENDANCE)),
                        cursor.getInt(cursor.getColumnIndex(DataContract.Attendance.LECTURE_ID)),
                        cursor.getInt(cursor.getColumnIndex(DataContract.Attendance.STUDENT_ID)));
                attendance.set_id(cursor.getInt(cursor.getColumnIndex(DataContract._ID)));

                attendances.add(attendance);
            } while (cursor.moveToNext());
        }
        dbUtils.close();

        return attendances;
    }


    private int markAttendance;
    private int lectureID;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getLectureID() {
        return lectureID;
    }

    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    public int getMarkAttendance() {
        return markAttendance;
    }

    public void setMarkAttendance(int markAttendance) {
        this.markAttendance = markAttendance;
    }


    private int studentID;


}
