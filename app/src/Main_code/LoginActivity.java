package com.assignment.mbas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import dbutils.DBUtils;
import model.User;


public class LoginActivity extends AppCompatActivity {
    private DBUtils dbUtils;
    LinearLayout regLayout, loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regLayout = (LinearLayout) findViewById(R.id.regLayout);
        loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        
        dbUtils = new DBUtils(getApplicationContext());
        dbUtils.open();
        if (dbUtils.containUsers()) {
            regLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        } else {
            regLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
            registerUser();
        }
        dbUtils.close();

    }


    private void registerUser() {

        regUserName = (EditText) findViewById(R.id.regUserName);
        regEmailId = (EditText) findViewById(R.id.regEmailId);
        regPhoneNumber = (EditText) findViewById(R.id.regPhoneNumber);
        regUserId = (EditText) findViewById(R.id.regUserId);
        regPassword = (EditText) findViewById(R.id.regPassword);

        regSecurityQuestion = (EditText) findViewById(R.id.regSecurityQuestion);
        regSecurityAnswer = (EditText) findViewById(R.id.regSecurityAnswer);
        Button btnRegister = (Button) findViewById(R.id.register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    //reg goes here...
                    User user = new User();
                    user.setUserName(regUserName.getText().toString());
                    user.setUserEmailId(regEmailId.getText().toString());
                    user.setUserLoginId(regUserId.getText().toString());
                    user.setUserPhoneNumber(regPhoneNumber.getText().toString());
                    user.setPassword(regPassword.getText().toString());
                    user.adduser(getApplicationContext(), user);

                    //ds
                    if (dbUtils.containUsers()) {
                        regLayout.setVisibility(View.GONE);
                        loginLayout.setVisibility(View.VISIBLE);
                    } else {
                        regLayout.setVisibility(View.VISIBLE);
                        loginLayout.setVisibility(View.GONE);
                        registerUser();
                    }

                }
            }
        });


    }

    EditText regUserName, regEmailId, regPhoneNumber, regUserId, regPassword, regSecurityQuestion, regSecurityAnswer;

    private boolean isValidate() {

        if (TextUtils.isEmpty(regUserName.getText().toString()) || TextUtils.isEmpty(regEmailId.getText().toString()) || TextUtils.isEmpty(regPhoneNumber.getText().toString()) ||
                TextUtils.isEmpty(regUserId.getText().toString()) || TextUtils.isEmpty(regPassword.getText().toString()) || TextUtils.isEmpty(regSecurityQuestion.getText().toString()) || TextUtils.isEmpty(regSecurityAnswer.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please fill the data in all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public void login(View v) {

        EditText loginId, password;
        loginId = (EditText) findViewById(R.id.loginId);
        password = (EditText) findViewById(R.id.password);

        User user = new User();
        if (user.loginAuthentication(getApplicationContext(), loginId.getText().toString(), password.getText().toString())) {
            startActivity(new Intent(LoginActivity.this, ModuleDashboardActivity.class));
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Please check the login id and password", Toast.LENGTH_SHORT).show();
        }

    }

}
