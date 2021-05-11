package dbutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.User;


public class DBUtils {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static String DB_NAME = "mbas";
    private int DB_VERSION = 2;


    public DBUtils(Context context) {

        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);

    }


    public SQLiteDatabase open() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return sqLiteDatabase;
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Users.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Users.USER_LOGIN_ID +
                    " TEXT," + DataContract.Users.USER_NAME + " TEXT," + DataContract.Users.USER_PHONE_NUMBER + " TEXT," + DataContract.Users.USER_EMAIL_ID
                    + " TEXT, " + DataContract.Users.PASSWORD + " TEXT," + DataContract.Users.SECURITY_QUESTION + " text,"
                    + DataContract.Users.ANSWER + " text);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Student.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Student.NAME +
                    " TEXT," + DataContract.Student.TNUMBER + " TEXT," + DataContract.Student.PHONE_NUMBER + " TEXT,"
                    + DataContract.Student.EMAIL_ID
                    + " TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Lecture.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Lecture.COURSE_NAME +
                    " TEXT," + DataContract.Lecture.COURSE_DETAILS + " TEXT," + DataContract.Lecture.CLASS_CODE
                    + " text," + DataContract.Lecture.START_DATE_TIME + " text," + DataContract.Lecture.END_DATE_TIME + " text,"
                    + DataContract.Lecture.SELECTED_STUDENTS + " text);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Attendance.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Attendance.LECTURE_ID +
                    " INTEGER," + DataContract.Attendance.MARK_ATTENDANCE + " INTEGER," + DataContract.Attendance.STUDENT_ID
                    + " INTEGER);");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Users.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Student.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Lecture.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Attendance.TABLE_NAME + ";");

            onCreate(db);

        }
    }

    public boolean containUsers() {

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DataContract.Users.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                if (cursor.getCount() > 0)
                    return true;
            }
        }
        return false;
    }

    public User getUser(String userId, String password) {
        User user = null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DataContract.Users.TABLE_NAME, null, "userLoginId=" + userId + " and password=" + password, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                } while (cursor.moveToNext());
            }
        }
        return user;
    }


}
