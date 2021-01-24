package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {

    TextInputLayout fullname, email, phoneNo, password;
    TextView fullnameLabel, usernameLabel;
    Button updatebtn, backbtn;
    DatabaseReference reference;

    String _name, _username, _email, _phoneNo, _password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reference = FirebaseDatabase.getInstance().getReference("user");

        fullname = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullnameLabel = findViewById(R.id.full_name_field);
        usernameLabel = findViewById(R.id.username_field);
        updatebtn = findViewById(R.id.update_btn);
        backbtn = findViewById(R.id.back_btn);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Update();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        showAllUserData();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        _name = intent.getStringExtra("name");
        _username = intent.getStringExtra("username");
        _email = intent.getStringExtra("email");
        _phoneNo = intent.getStringExtra("phonenumber");
        _password = intent.getStringExtra("password");

        fullname.getEditText().setText(_name);
        fullnameLabel.setText(_name);
        usernameLabel.setText(_username);
        this.email.getEditText().setText(_email);
        this.phoneNo.getEditText().setText(_phoneNo);
        this.password.getEditText().setText(_password);
    }

    private void Update()
    {
        if (isNameChanged() || isPasswordChanged() || isEmailChanged() || isPhoneNoChanged())
        {
            Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "No Changes", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isPasswordChanged(){
        if(!_password.equals(password.getEditText().getText().toString()))
        {
            reference.child(_username).child("password").setValue(password.getEditText().getText().toString());
            _password=password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }
    private boolean isNameChanged(){
        if(!_name.equals(fullname.getEditText().getText().toString()))
        {
            reference.child(_username).child("name").setValue(fullname.getEditText().getText().toString());
            _name=fullname.getEditText().getText().toString();
            fullnameLabel.setText(_name);
            return true;
        }else{
            return false;
        }

    }
    private boolean isEmailChanged(){
        if(!_email.equals(email.getEditText().getText().toString()))
        {
            reference.child(_username).child("email").setValue(email.getEditText().getText().toString());
            _email=email.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }
    private boolean isPhoneNoChanged(){
        if(!_phoneNo.equals(phoneNo.getEditText().getText().toString()))
        {
            reference.child(_username).child("phonenumber").setValue(phoneNo.getEditText().getText().toString());
            _phoneNo=phoneNo.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }
}