package com.assignment.mbas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dbutils.DataContract;
import model.Lecture;
import model.Student;


public class AttendanceListAdapter extends BaseAdapter {
    private Context mContext;
    List<Student> students;
    int lectureID;

    public AttendanceListAdapter(Context applicationContext, int lectureID) {
        mContext = applicationContext;
        this.lectureID = lectureID;
        Lecture lecture = new Lecture(applicationContext);
        List<Lecture> lectures = lecture.getLectures(applicationContext, DataContract._ID + " = " + lectureID);
        Lecture mLecture = lectures.get(0);
        students = new ArrayList<>();
        String studentsWithCommaSeperate = mLecture.getSelectedStudents();
        //getstudents here....
        List<String> studentsList = Arrays.asList(studentsWithCommaSeperate.split(","));

        Student student = new Student(applicationContext);
        for (int i = 0; i < studentsList.size(); i++) {
            try {
                Student mStudent = student.getStudents(applicationContext, DataContract._ID + "=" + Integer.parseInt(studentsList.get(i).toString())).get(0);
                students.add(mStudent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).get_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater;
        ViewHolder viewHolder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_attendance_sheet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(students.get(position).getName());
        viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.present) {
                    //some code
                    students.get(position).setAbsentPresent(1);
                    students.get(position).setLectureID(lectureID);
                } else if (checkedId == R.id.absent) {
                    //some code
                    students.get(position).setAbsentPresent(0);
                    students.get(position).setLectureID(lectureID);
                }
            }
        });


        return convertView;
    }

    public List<Student> getAttendanceDetails() {

//        List<Student> students = new ArrayList<>();
//        for (Student student : students) {
//            students.add(student);
//        }
        return students;
    }

    class ViewHolder {
        TextView name;
        RadioGroup radioGroup;
        Button save;

        public ViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.name);
            radioGroup = (RadioGroup) convertView.findViewById(R.id.attendanceRG);

        }
    }

}
