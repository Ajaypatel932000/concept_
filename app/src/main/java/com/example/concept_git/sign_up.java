package com.example.concept_git;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Set the title of Activity
       getSupportActionBar().setTitle("SignUp Form");
    }
}
