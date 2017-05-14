package com.example.rishucuber.armywelfare.initactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rishucuber.armywelfare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupActivity extends AppCompatActivity {
    private EditText mFirstname, mLastname, mContactnumber, mEmailadd, mPass, mCpass;
    private String sName, sContactnumber, sEmailadd, sPass, sCpass;
    private Button mSignup;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    createUser();
                }
            }
        });


    }

    //Validation for forms
    public boolean validate() {
        boolean valid = true;
        sName = mFirstname.getText().toString();
        sContactnumber = mContactnumber.getText().toString();
        sEmailadd = mEmailadd.getText().toString();
        sPass = mPass.getText().toString();
        sCpass = mCpass.getText().toString();
        //FirstName
        if (sName.isEmpty()) {
            mFirstname.setError("Enter Name");
            mFirstname.requestFocus();
            valid = false;
            return valid;
        } else {
            mFirstname.setError(null);
        }
        //Contact Number
        if (sContactnumber.isEmpty() || sContactnumber.length() != 10) {
            mContactnumber.setError("Enter ValidContact");
            mContactnumber.requestFocus();
            valid = false;
            return valid;
        } else {
            mContactnumber.setError(null);
        }
        //Email Address
        if (sEmailadd.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(sEmailadd).matches()) {
            mEmailadd.setError("enter a valid email address");
            mEmailadd.requestFocus();
            valid = false;
            return valid;
        } else {
            mEmailadd.setError(null);
        }
        //password
        if (sPass.isEmpty() || sPass.length() < 4 || sPass.length() > 10) {
            mPass.setError("between 4 and 10 alphanumeric characters");
            mPass.requestFocus();
            valid = false;
            return valid;

        } else {
            mPass.setError(null);
            //Confirm password
        }
        if (sCpass.isEmpty() || sCpass.length() < 4 || sCpass.length() > 10 || !(sCpass.equals(sPass))) {
            mCpass.setError("Password Mismatch !");
            mCpass.requestFocus();
            valid = false;
            return valid;

        } else {
            mCpass.setError(null);
        }


        return valid;
    }

    //Creating initial instances
    private void init() {
        mFirstname = (EditText) findViewById(R.id.et_name);
        mContactnumber = (EditText) findViewById(R.id.et_cnumber);
        mEmailadd = (EditText) findViewById(R.id.et_email);
        mPass = (EditText) findViewById(R.id.et_pass);
        mCpass = (EditText) findViewById(R.id.et_cpass);
        mSignup = (Button) findViewById(R.id.bt_signup);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    public void createUser(){
        String email = mEmailadd.getText().toString().trim();
        String password = mPass.getText().toString().trim();
        //Progress Bar Visibility on
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_LONG).show();
                        //Progress Bar Visibility GONE
                        progressBar.setVisibility(View.GONE);
                        //Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });

    }
    @Override
    protected void onResume() {
        super.onResume();
        //By default Progress bar visibility off
        progressBar.setVisibility(View.GONE);
    }


}
