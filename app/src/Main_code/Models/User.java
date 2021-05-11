package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dbutils.DBUtils;
import dbutils.DataContract;


public class User {
    private String userLoginId;
    private String userName;
    private String userPhoneNumber;
    private String userEmailId;
    private String password;
    private String securityQuestion;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    private String answer;

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void adduser(Context context, User user) {
        DBUtils dbUtils = new DBUtils(context);
        SQLiteDatabase sqLiteDatabase = dbUtils.open();
        ContentValues cv = new ContentValues();
        cv.put(DataContract.Users.USER_NAME, user.getUserName());
        cv.put(DataContract.Users.USER_LOGIN_ID, user.getUserLoginId());
        cv.put(DataContract.Users.USER_PHONE_NUMBER, user.getUserPhoneNumber());
        cv.put(DataContract.Users.PASSWORD, user.getPassword());
        cv.put(DataContract.Users.USER_EMAIL_ID, user.getUserEmailId());
        cv.put(DataContract.Users.SECURITY_QUESTION, user.getSecurityQuestion());
        cv.put(DataContract.Users.ANSWER, user.getAnswer());
        sqLiteDatabase.insert(DataContract.Users.TABLE_NAME, null, cv);
        dbUtils.close();
    }


    public boolean loginAuthentication(Context context, String loginId, String password) {

        DBUtils dbUtils = new DBUtils(context);
        SQLiteDatabase sqLiteDatabase = dbUtils.open();
        Cursor cursor = sqLiteDatabase.query(DataContract.Users.TABLE_NAME, null,
                DataContract.Users.USER_LOGIN_ID + "='" + loginId + "' and " + DataContract.Users.PASSWORD + "='" + password + "'",
                null, null, null, null);
        try {
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    return true;
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbUtils.close();
        }

        return false;
    }


}
