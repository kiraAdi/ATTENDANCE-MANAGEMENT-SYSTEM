package com.assignment.mbas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import model.Attendance;
import model.Student;


public class AttendanceDashboardActivity extends BaseActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        ListView attendanceListView = (ListView) findViewById(R.id.attendanceListView);
        TextView totalHeader, attendancePercentHeader;
        totalHeader = (TextView) findViewById(R.id.totalHeader);
        attendancePercentHeader = (TextView) findViewById(R.id.attendancePercentHeader);
        Button save = (Button) findViewById(R.id.save);
        int lectureID = 0;
        if (intent.hasExtra(LectureListAdapter.LECTURE_ID))
            lectureID = intent.getIntExtra(LectureListAdapter.LECTURE_ID, 0);
        if (lectureID > 0) {


            totalHeader.setVisibility(View.GONE);
            attendancePercentHeader.setVisibility(View.GONE);

            //prepare attendance ui


            final AttendanceListAdapter attendanceListAdapter = new AttendanceListAdapter(getApplicationContext(), lectureID);
            attendanceListView.setAdapter(attendanceListAdapter);


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // save to db and calculate attendance percentage,,,,,
                    List<Student> students = attendanceListAdapter.getAttendanceDetails();
                    for (Student student : students) {
                        Attendance attendance = new Attendance(student.getAbsentPresent(), student.getLectureID(), student.get_id());
                        attendance.addAttendance(getApplicationContext());
                    }


                    finish();
                }
            });
        } else {
//          show attendance based on student  normal percentages..
            totalHeader.setVisibility(View.VISIBLE);
            attendancePercentHeader.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
            attendanceListView.setAdapter(new AttendanceSheetAdapter(AttendanceDashboardActivity.this));

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
