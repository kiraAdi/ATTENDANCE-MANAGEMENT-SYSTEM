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

import java.util.ArrayList;
import java.util.List;

import dbutils.DBUtils;
import model.Lecture;

public class LectureListAdapter extends BaseAdapter {
    public static final String LECTURE_ID = "lecture_id";
    List<Lecture> lectures;
    Activity mContext;


    public LectureListAdapter(Activity applicationContext) {
        this.mContext = applicationContext;
        getData();
    }

    private void getData() {
        DBUtils dbUtils = new DBUtils(mContext);
        dbUtils.open();
        Lecture lecture = new Lecture(mContext);
        lectures = new ArrayList<>();
        lectures = lecture.getLectures(mContext, null);

        dbUtils.close();
    }

    @Override
    public int getCount() {
        return lectures.size();
    }

    @Override
    public Lecture getItem(int position) {
        return lectures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lectures.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {


            convertView = layoutInflater.inflate(R.layout.item_lecture, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.courseName.setText("Class:" + lectures.get(position).getCourseName());
//        viewHolder.courseDetails.setText("Details" + lectures.get(position).getCourceDetails());

        viewHolder.takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to attendance activity
                Intent intent = new Intent(mContext, AttendanceDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(LECTURE_ID, lectures.get(position).getId());
                mContext.startActivity(intent);
                mContext.finish();
            }
        });
        return convertView;
    }

    public void updateData() {
        DBUtils dbUtils = new DBUtils(mContext);
        dbUtils.open();
        Lecture lecture = new Lecture(mContext);
        lectures = new ArrayList<>();
        lectures = lecture.getLectures(mContext, null);
        dbUtils.close();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView courseName, courseDetails;
        Button takeAttendance;

        public ViewHolder(View v) {
            courseName = (TextView) v.findViewById(R.id.courseName);
            courseDetails = (TextView) v.findViewById(R.id.courseDetails);
            takeAttendance = (Button) v.findViewById(R.id.takeAttendance);
        }

    }
}
