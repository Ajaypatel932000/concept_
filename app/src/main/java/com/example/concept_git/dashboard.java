package com.example.concept_git;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class dashboard extends AppCompatActivity {

    TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        titleText=findViewById(R.id.textView3);
        Intent getDataIntent=getIntent();

        String name = getDataIntent.getStringExtra("name_key");
        String email=getDataIntent.getStringExtra("email_key");
        String father_no=getDataIntent.getStringExtra("phone_key");
        String password=getDataIntent.getStringExtra("password_key");
        titleText.setText("Name :"+name+" \nEmail ="+email+"\n father no ="+father_no+" \n password ="+password);

    }
}
