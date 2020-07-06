package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class attendance_card extends AppCompatActivity implements View.OnClickListener {

    CardView m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13;
     ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_card);
        ActionBar actionBar=getSupportActionBar();
         actionBar.hide();

         back=findViewById(R.id.ant1_id);
         m1=findViewById(R.id.a_1);
         m2=findViewById(R.id.a_2);
         m3=findViewById(R.id.a_3);
         m4=findViewById(R.id.a_4);
         m5=findViewById(R.id.a_5);
         m6=findViewById(R.id.a_6);
         m7=findViewById(R.id.a_7);
         m8=findViewById(R.id.a_8);
         m9=findViewById(R.id.a_9);
         m10=findViewById(R.id.a_10);
         m11=findViewById(R.id.a_11);
         m12=findViewById(R.id.a_12);
         m13=findViewById(R.id.a_13);


       m1.setOnClickListener(this);
       m2.setOnClickListener(this);
       m3.setOnClickListener(this);
       m4.setOnClickListener(this);
       m5.setOnClickListener(this);
       m6.setOnClickListener(this);
       m7.setOnClickListener(this);
       m8.setOnClickListener(this);
       m9.setOnClickListener(this);
       m10.setOnClickListener(this);
       m11.setOnClickListener(this);
       m12.setOnClickListener(this);
       m13.setOnClickListener(this);

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(attendance_card.this,dashboard.class);
               startActivity(intent);
           }
       });


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.a_1:
                Intent intent=new Intent(attendance_card.this,attendance.class);
                intent.putExtra("month_key","1");
                startActivity(intent);
            break;
            case R.id.a_2:
                Intent intent1=new Intent(attendance_card.this,attendance.class);
                intent1.putExtra("month_key","2");
                startActivity(intent1);
                break;
            case R.id.a_3:
                Intent intent3=new Intent(attendance_card.this,attendance.class);
                intent3.putExtra("month_key","3");
                startActivity(intent3);
                break;
            case R.id.a_4:
                Intent intent4=new Intent(attendance_card.this,attendance.class);
                intent4.putExtra("month_key","4");
                startActivity(intent4);
                break;
            case R.id.a_5:
                Intent intent5=new Intent(attendance_card.this,attendance.class);
                intent5.putExtra("month_key","5");
                startActivity(intent5);
                break;
            case R.id.a_6:
                Intent intent6=new Intent(attendance_card.this,attendance.class);
                intent6.putExtra("month_key","6");
                startActivity(intent6);
                break;
            case R.id.a_7:
                Intent intent7=new Intent(attendance_card.this,attendance.class);
                intent7.putExtra("month_key","7");
                startActivity(intent7);
                break;
            case R.id.a_8:
                Intent intent8=new Intent(attendance_card.this,attendance.class);
                intent8.putExtra("month_key","8");
                startActivity(intent8);
                break;
            case R.id.a_9:
                Intent intent9=new Intent(attendance_card.this,attendance.class);
                intent9.putExtra("month_key","9");
                startActivity(intent9);
                break;
            case R.id.a_10:
                Intent intent10=new Intent(attendance_card.this,attendance.class);
                intent10.putExtra("month_key","10");
                startActivity(intent10);
                break;
            case R.id.a_11:
                Intent intent11=new Intent(attendance_card.this,attendance.class);
                intent11.putExtra("month_key","11");
                startActivity(intent11);
                break;
            case R.id.a_12:
                Intent intent12=new Intent(attendance_card.this,attendance.class);
                intent12.putExtra("month_key","12");
                startActivity(intent12);
                break;

            case R.id.a_13:
                  Intent intent13=new Intent(attendance_card.this,attendance_graph.class);
                  startActivity(intent13);
                  break;

                default:
                    Toast.makeText(attendance_card.this,"Invalid Selection in Attendance_card.java",Toast.LENGTH_LONG).show();
                    break;

        }


    }
}
