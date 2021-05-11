package com.assignment.mbas;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.assignment.mbas.R;

import model.Student;


public class StudentDashboardActivity extends BaseActivity {
    private StudentsListAdapter studentsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listview);
        studentsListAdapter = new StudentsListAdapter(getApplicationContext());
        listView.setAdapter(studentsListAdapter);

    }

    public void addStudent() {

    }

    public void viewStudents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add_student:
                //open add student fragment
                DialogFragment fragment = new AddStudentFragment();
                fragment.show(getSupportFragmentManager(), "addStudent");

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    class AddStudentFragment extends DialogFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        }

        private EditText name, tNumber, phoneNumber, emailid;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.add_student, null);


            name = (EditText) rootView.findViewById(R.id.name);
            tNumber = (EditText) rootView.findViewById(R.id.tNumber);
            phoneNumber = (EditText) rootView.findViewById(R.id.phoneNumber);
            emailid = (EditText) rootView.findViewById(R.id.emailId);
            Button save = (Button) rootView.findViewById(R.id.save);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValidate()) {
                        Student student = new Student(name.getText().toString(), tNumber.getText().toString(),
                                phoneNumber.getText().toString(), emailid.getText().toString());

                        student.addStudent(getApplicationContext());
                        studentsListAdapter.updateData();
                        dismiss();
                    } else
                        Toast.makeText(getActivity(), "Please fill the data", Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }

        private boolean isValidate() {
            if (TextUtils.isEmpty(name.getText().toString()) ||
                    TextUtils.isEmpty(tNumber.getText().toString()) || TextUtils.isEmpty(phoneNumber.getText().toString()) ||
                    TextUtils.isEmpty(emailid.getText().toString())) {
                return false;
            } else
                return true;
        }

        @Override
        public void onResume() {
            super.onResume();
            Dialog dialog = getDialog();
            dialog.setTitle("Add Student");
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
    }


}
