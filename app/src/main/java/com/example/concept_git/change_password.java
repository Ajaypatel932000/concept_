package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class change_password extends AppCompatActivity implements View.OnClickListener{

    EditText et_pass,et_confirm_pass;
    Button restButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();


        et_pass=findViewById(R.id.pass);
        et_confirm_pass=findViewById(R.id.confirm_pass);
        restButton=findViewById(R.id.reset_btn_id);
        backButton=findViewById(R.id.cancle_btn);

        restButton.setOnClickListener(this);
        backButton.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.reset_btn_id:
                Intent intent=new Intent(change_password.this,login.class);
                startActivity(intent);
                break;

            case R.id.pinview:
                Intent intent1=new Intent(change_password.this,login.class);
                startActivity(intent1);
                break;

                case R.id.cancle_btn:
                    Intent intent2=new Intent(change_password.this,login.class);
                    startActivity(intent2);

                   break;

            default:
                    Toast.makeText(change_password.this,"Swich Case Defalut execute in Change_password",Toast.LENGTH_LONG).show();
                    break;
        }


    }
}
