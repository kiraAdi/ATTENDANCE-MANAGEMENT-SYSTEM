package dbutils;

import java.util.Date;


public class DataContract {

    public static String _ID = "_id";

    public static class Users {
        public static String TABLE_NAME = "users";
        public static String USER_LOGIN_ID = "userLoginId";
        public static String USER_PHONE_NUMBER = "userPhoneNumber";
        public static String USER_EMAIL_ID = "userEmailId";
        public static String PASSWORD = "password";
        public static String SECURITY_QUESTION = "securityQuestion";
        public static String ANSWER = "answer";
        public static String USER_NAME = "userName";

    }

    public static class Student {
        public static String TABLE_NAME = "students";
        //        String name, String tNumber, String phoneNumber, String emailId
        public static String NAME = "name";
        public static String TNUMBER = "tNumber";
        public static String PHONE_NUMBER = "phoneNumber";
        public static String EMAIL_ID = "emailId";

    }

    public static class Lecture {

//        private String courseName, courceDetails;
//        private Date date

        public static String TABLE_NAME = "lectures";
        public static String COURSE_NAME = "course_name";
        public static String COURSE_DETAILS = "course_details";
        public static String CLASS_CODE = "class_code";
        public static String START_DATE_TIME = "start_date_time";
        public static String END_DATE_TIME = "end_date_time";
        public static final String SELECTED_STUDENTS = "selected_students";


    }

    public static class Attendance {
        public static String TABLE_NAME = "attendance";
        public static String MARK_ATTENDANCE = "mark_attendance";
        public static String LECTURE_ID = "lecture_id";
        public static String STUDENT_ID = "student_id";
    }
}
