package com.assignment.mbas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dbutils.DBUtils;
import model.Lecture;
import model.Student;

public class LectureDashboardActivity extends BaseActivity {
    LectureListAdapter lectureListAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_dashboard);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listview);
        lectureListAdapter = new LectureListAdapter(this);
        listView.setAdapter(lectureListAdapter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lecture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add_lecture:
                //open add student fragment
                DialogFragment fragment = new AddLectureFragment();
                fragment.show(getSupportFragmentManager(), "addLecture");

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LectureDashboard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.assignment.mbas/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LectureDashboard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.assignment.mbas/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    class AddLectureFragment extends DialogFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        private EditText courseName, courseDetails, classCode;
        private TextView startTime, endTime;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.add_lecture, null);


            courseName = (EditText) rootView.findViewById(R.id.courseName);
            courseDetails = (EditText) rootView.findViewById(R.id.courseDetails);
            classCode = (EditText) rootView.findViewById(R.id.classCode);
            startTime = (TextView) rootView.findViewById(R.id.startTime);
            endTime = (TextView) rootView.findViewById(R.id.endTime);

            Button pickStartTime = (Button) rootView.findViewById(R.id.pickStartTime);
            pickStartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    count = 0;
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "start_time");
                    datePickerFragment.setArguments(bundle);
                    datePickerFragment.show(getActivity().getSupportFragmentManager(), "DatePickerFragment");

                }
            });

            Button pickEndTime = (Button) rootView.findViewById(R.id.pickEndTime);
            pickEndTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = 0;
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "end_time");
                    datePickerFragment.setArguments(bundle);
                    datePickerFragment.show(getActivity().getSupportFragmentManager(), "DatePickerFragment");
                }
            });
            final StringBuilder selectedStudents = new StringBuilder();
            Button selectStudents = (Button) rootView.findViewById(R.id.selectStudents);
            final StudentsListAdapter studentAdapter = new StudentsListAdapter(getApplicationContext(), "Dialog");
            selectStudents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    loadStudents();

                }
            });


            Button save = (Button) rootView.findViewById(R.id.save);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValidate()) {
                        //save...
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            stringBuilder.append(students.get(i).get_id() + "");
                            if (mSelectedItems.size() > 1 && i < mSelectedItems.size() - 1)
                                stringBuilder.append(",");
                        }

                        Lecture lecture = new Lecture(courseName.getText().toString(), courseDetails.getText().toString(), classCode.getText().toString(),
                                startTime.getText().toString(), endTime.getText().toString(), stringBuilder.toString());

                        lecture.addLectue(getApplicationContext());
                        lectureListAdapter.updateData();
                        dismiss();
                    } else
                        Toast.makeText(getActivity(), "Please fill the data", Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }

        final ArrayList mSelectedItems = new ArrayList();
        List<Student> students;

        void loadStudents() {

            DBUtils dbUtils = new DBUtils(getApplicationContext());
            dbUtils.open();
            Student student = new Student(getApplicationContext());

            students = student.getStudents(getApplicationContext(), null);
            final String items[] = new String[students.size()];
            for (int i = 0; i < students.size(); i++) {
                items[i] = students.get(i).getName();
            }


            AlertDialog.Builder ab = new AlertDialog.Builder(LectureDashboardActivity.this);
            ab.setTitle("Dialog Title")
                    .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                mSelectedItems.add(which);
                            } else if (mSelectedItems.contains(which)) {
                                mSelectedItems.remove(Integer.valueOf(which));
                            }
                        }

                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK, so save the mSelectedItems results somewhere
                    // or return them to the component that opened the dialog

                    //save goes here...


                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            ab.create();


            ab.show();
        }

        private boolean isValidate() {
            if (TextUtils.isEmpty(courseName.getText().toString()) ||
                    TextUtils.isEmpty(courseDetails.getText().toString()) || TextUtils.isEmpty(startTime.getText().toString()) ||
                    TextUtils.isEmpty(endTime.getText().toString())
                    ) {

                return false;
            } else if (mSelectedItems.size() == 0) {
                Toast.makeText(getApplicationContext(), "Please select the students", Toast.LENGTH_SHORT).show();
                return false;
            } else
                return true;
        }

        public void addLecture() {

        }

        public void viewLectures() {

        }

        @Override
        public void onResume() {
            super.onResume();
            Dialog dialog = getDialog();
            dialog.setTitle("Add Class");
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            Display display = dialog.getWindow().getWindowManager().getDefaultDisplay();
            float height = 0;
            if (Build.VERSION.SDK_INT > 13) {
                Point point = new Point();
                display.getSize(point);
                height = point.y / 1.25f;
            } else
                height = display.getHeight() / 1.25f;

            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, (int) height);

        }

        Calendar myCalendar;

        public class TimePickerFragment extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {


            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {


                if (myCalendar == null) {
                    myCalendar = Calendar.getInstance();
                }

                // Use the current time as the default values for the picker

                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                String am_pm = null;
                if (hourOfDay > 12)
                    am_pm = "AM";
                else am_pm = "PM";

//            timeOrDays.setText(hourOfDay + " : " + minute + am_pm);


                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);


                isEditeted = true;
                updateLabel(getArguments());
            }
        }

        private boolean isEditeted;
        int count = 0;

        public class DatePickerFragment extends DialogFragment
                implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current date as the default date in the picker
                if (myCalendar == null) {
                    myCalendar = Calendar.getInstance();
                }

                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            timeOrDays.setText(monthOfYear + " : " + dayOfMonth + " : " + year);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                isEditeted = true;


                count++;

                if (count == 1) {// TODO this is for HTC MOBILE......
                    TimePickerFragment timePickerFragment = new TimePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", getArguments().getString("from"));
                    timePickerFragment.show(getActivity().getSupportFragmentManager(), "TimePickerFragment");
                }
                updateLabel(getArguments());
            }


        }

        private void updateLabel(Bundle bundle) {
            Date newTime = myCalendar.getTime();
            if (isEditeted) {
                if (bundle != null) {
                    if (bundle.getString("from").equals("start_time"))
                        startTime.setText(MyDateUtils.convertMilliSecondsToDateFormat(newTime.getTime() + 1000));
                    else
                        endTime.setText(MyDateUtils.convertMilliSecondsToDateFormat(newTime.getTime() + 1000));
                } else {
                    if (bundle != null)
                        if (getArguments().getString("from").equals("start_time"))
                            startTime.setText(MyDateUtils.convertMilliSecondsToDateFormat(newTime.getTime()));
                        else
                            endTime.setText(MyDateUtils.convertMilliSecondsToDateFormat(newTime.getTime()));

                }
            }

        }
    }


}
