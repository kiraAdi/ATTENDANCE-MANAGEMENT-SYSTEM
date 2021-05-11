package com.assignment.mbas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import dbutils.DataContract;
import model.Attendance;
import model.Student;


public class AttendanceSheetAdapter extends BaseAdapter {

    List<Student> students;
    Activity context;

    public AttendanceSheetAdapter(Activity applicationContext) {
        Student student = new Student(applicationContext);
        students = student.getStudents(applicationContext, null);
        context = applicationContext;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.item_attendance_caluculate_sheet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Student mStudent = students.get(position);
        viewHolder.name.setText(mStudent.getName());
        Attendance attendance = new Attendance();
        List<Attendance> mAttendances = attendance.getAttendance(context, DataContract.Attendance.STUDENT_ID + "=" + mStudent.get_id());
        int absent = 0, present = 0;
        float total;
        final float percentage;

        for (Attendance mAttendance : mAttendances) {
            if (mAttendance.getMarkAttendance() == 0)
                absent++;
            present += mAttendance.getMarkAttendance();
        }

        total = mAttendances.size();
        percentage = (present / total) * 100f;

        viewHolder.present.setText(present + "");
        viewHolder.absent.setText(absent + "");
        viewHolder.total.setText(total + "");
        viewHolder.attendancePercentage.setText(Math.round(percentage * 100.0) / 100.0 + "%");
        viewHolder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_EMAIL, students.get(position).getEmailId());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Attendance");
                intent.putExtra(Intent.EXTRA_TEXT, "Hi " + students.get(position).getName() + ", your attendance percentage is " + Math.round(percentage * 100.0) / 100.0 + "%");

                context.startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView present, absent, total, attendancePercentage, name;
        Button send;

        public ViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.name);
            present = (TextView) convertView.findViewById(R.id.present);
            absent = (TextView) convertView.findViewById(R.id.absent);
            total = (TextView) convertView.findViewById(R.id.totalHeader);
            attendancePercentage = (TextView) convertView.findViewById(R.id.attendancePercentHeader);
            send = (Button) convertView.findViewById(R.id.send);
        }
    }
}
