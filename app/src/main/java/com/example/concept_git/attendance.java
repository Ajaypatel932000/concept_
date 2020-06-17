package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class attendance extends AppCompatActivity {
    int pos;
    String month,dayOfWeek;
    ImageView back;
    RecyclerView recyclerView;
    MyAdapter2 myAdapter2;
    boolean Status;
    RequestQueue requestQueue;
    String Date, URL = "http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/getAttendance";
   int year,m,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

         back=findViewById(R.id.atn_back);
        recyclerView = findViewById(R.id.recycleView1);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(attendance.this,attendance_card.class);
                startActivity(intent);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter2 = new MyAdapter2(this, getMyList());
        recyclerView.setAdapter(myAdapter2);

        Intent getIntentData=getIntent();
         month=getIntentData.getStringExtra("month_key");
         Toast.makeText(attendance.this,"Month is  ="+month,Toast.LENGTH_LONG).show();
    }



      private ArrayList<Model2> getMyList()
      {
          final   ArrayList<Model2> model=new ArrayList<>();


          StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
              @Override
              public void onResponse(String response)
              {
                  try{

                      JSONObject jsonObject =new JSONObject(response);
                      JSONArray jsonArray= jsonObject.getJSONArray("Attendence");
                      Toast.makeText(attendance.this,"Json Length ="+jsonArray.length(),Toast.LENGTH_LONG).show();
                      if(jsonArray.length()!=0) {
                          for (int i = 0; i < jsonArray.length(); i++)
                          {



                              Log.d("Tag", String.valueOf(jsonArray.length()));
                              JSONObject object = jsonArray.getJSONObject(i);

                              Date = object.getString("date");
                              Status = object.getBoolean("Status");
                             year= Integer.parseInt(object.getString("Year"));
                             day=Integer.parseInt(object.getString("A_date"));


                         try {
                             m=Integer.parseInt(month);
                             // First convert to Date. This is one of the many ways.
                             String dateString = String.format("%d-%d-%d", year, m, day);
                             java.util.Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);

                             // Then get the day of week from the Date based on specific locale.
                              dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
                             Log.d("Ajay", dayOfWeek);

                         }catch(Exception e){}


                              // here create model object and setter the data into setter method
                              Model2 m = new Model2();
                              m.setDate(Date);
                              m.setDay(dayOfWeek);
                              if (Status) {
                                  m.setImg(R.drawable.ic_done_black_24dp);
                              } else {
                                  m.setImg(R.drawable.ic_clear);
                              }
                              model.add(m);

                              Log.d("Date =", Date);
                              Log.d("Status =", String.valueOf(Status));
                              Log.d("DayOfWeek",dayOfWeek);
                          }


                      }else
                      {
                          Model2 m = new Model2();
                          m.setDate("No Record Found");
                          model.add(m);

                      }
                  }
                  catch(JSONException e)
                  {
                      e.printStackTrace();
                  }

              }
          },new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error)
              {

                  Toast.makeText(attendance.this,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
              }

          }){
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> params = new HashMap<>();
                  params.put("enroll_key", login.ENROLLMENT_NO);
                  params.put("month_key",month);

                  return params;
              }

          };
          requestQueue.add(stringRequest);
          return model;
      }
}
