package com.assignment.mbas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dbutils.DBUtils;
import model.Student;


public class StudentsListAdapter extends BaseAdapter {
    List<Student> students;
    Context mContext;
    String from;

    public StudentsListAdapter(Context context) {
        mContext = context;
        getData();

    }

    public StudentsListAdapter(Context context, String dialog) {
        mContext = context;
        getData();
        from = dialog;
    }

    void getData() {
        DBUtils dbUtils = new DBUtils(mContext);
        dbUtils.open();
        Student student = new Student(mContext);
        students = new ArrayList<>();
        students = student.getStudents(mContext, null);

        dbUtils.close();
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_student, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (from == null) {
            viewHolder.name.setText("Name:" + students.get(position).getName());
            viewHolder.tNumber.setText("Trojan number:" + students.get(position).gettNumber());
            viewHolder.nameCB.setVisibility(View.GONE);
        } else {
            viewHolder.nameCB.setVisibility(View.VISIBLE);
            viewHolder.nameCB.setText(students.get(position).getName());
            viewHolder.name.setVisibility(View.GONE);
            viewHolder.tNumber.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void updateData() {
        getData();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView name, tNumber;
        CheckBox nameCB;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.name);
            tNumber = (TextView) v.findViewById(R.id.tNumber);
            nameCB = (CheckBox) v.findViewById(R.id.nameCB);
        }

    }
}
