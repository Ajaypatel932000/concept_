package com.example.concept_git;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.navigation.NavigationView;

public class dashboard extends AppCompatActivity  implements View.OnClickListener{
 ImageView navigation_btn;
    TextView titleText;
  public static boolean Status;
    DrawerLayout mdrawerlayout;
    CardView TestCard,ResultCard,NoteCard,VideoCard,LeaveCard,AttendanceCard;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();



        mdrawerlayout = findViewById(R.id.drawble);
        navigation_btn=findViewById(R.id.menu_id);
        navigationView=findViewById(R.id.navView);
        TestCard=findViewById(R.id.cardViewTest);
        ResultCard=findViewById(R.id.CardViewResult);
        NoteCard=findViewById(R.id.CardViewNote);
        VideoCard=findViewById(R.id.CardViewVideo);
        LeaveCard=findViewById(R.id.CardViewLeave);
        AttendanceCard=findViewById(R.id.CardViewAttendance);

//    this line code  effect to set icon color as it is  . your selected color .
        navigationView.setItemIconTintList(null);
        TestCard.setOnClickListener(this);
        ResultCard.setOnClickListener(this);
        NoteCard.setOnClickListener(this);
        VideoCard.setOnClickListener(this);
        LeaveCard.setOnClickListener(this);
        AttendanceCard.setOnClickListener(this);



       navigation_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mdrawerlayout.openDrawer(GravityCompat.START);



           }
        });


       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               int id = item.getItemId();
               switch (id)
               {
                   case R.id.chang_pass:
                        Intent intent=new Intent(dashboard.this,new_password.class);
                        startActivity(intent);
                       break;
                   case R.id.profile:
                       Intent intent2=new Intent(dashboard.this,profile.class);
                       startActivity(intent2);

                       break;
                   case R.id.notification:
                       Intent intent3=new Intent(dashboard.this,notification.class);
                       startActivity(intent3);
                       break;

                   case R.id.Leave_status:
                       Intent intent1=new Intent(dashboard.this,leave_recycle_view.class);
                       startActivity(intent1);
                       break;

                   case R.id.logout:
                          Intent intent4=new Intent(dashboard.this,login.class);
                          login.USER_NAME_=null;
                          login.ENROLLMENT_NO=null;
                          startActivity(intent4);
                          break;


                          default:
                           Toast.makeText(dashboard.this,"Dashboard Toast Executed",Toast.LENGTH_LONG).show();
               }
               return false;
           }
       });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.cardViewTest:
                Intent intent=new Intent(dashboard.this,test.class);
                 startActivity(intent);
                 break;
            case R.id.CardViewResult:
                Intent intent1=new Intent(dashboard.this,result.class);
                startActivity(intent1);
                break;
            case R.id.CardViewNote:
                Status=true;
                Intent intent2=new Intent(dashboard.this,subject.class);
                startActivity(intent2);
                break;
            case R.id.CardViewVideo:
                Status=false;
                Intent intent3=new Intent(dashboard.this,subject.class);
                startActivity(intent3);
                break;
            case R.id.CardViewLeave:
                Intent intent4=new Intent(dashboard.this,leave.class);
                startActivity(intent4);
                break;

            case R.id.CardViewAttendance:
                Intent intent5=new Intent(dashboard.this,attendance_card.class);
                startActivity(intent5);
                break;

            default:
                    Toast.makeText(dashboard.this,"CardView  Click Default Case Execute",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
