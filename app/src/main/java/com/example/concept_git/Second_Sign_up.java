package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Second_Sign_up extends AppCompatActivity {
    EditText datePicker,et_father_no,et_mother_no,et_address,et_full_name;
    ImageView back_btn;
    TextView titleText;
    RadioGroup radioGroup;
    Button next_btn;
    RadioButton radioButton;
    String URL="http://10.0.2.2:8768/PROJECT2020/aayesha.asmx/AddNewUser";
    RequestQueue requestQueue;
    String gender = "";
    RadioButton male, female;
   String name,email,father_no,password;
    int d,m,y;
   public String full_name,mother_no,student_no,brithDate,address;

    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__sign_up);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        back_btn =findViewById(R.id.back_button);
        next_btn=findViewById(R.id.next_btn_id);
        titleText=findViewById(R.id.sign_up);
        datePicker=findViewById(R.id.et_date);

        et_full_name=findViewById(R.id.et_name);
        et_father_no=findViewById(R.id.et_phone);
        et_mother_no=findViewById(R.id.m_et);
        et_address=findViewById(R.id.et_address);
        male = findViewById(R.id.radioButton);
        female = findViewById(R.id.radioButton2);


        radioGroup=findViewById(R.id.radio);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

         Intent getDataIntent=getIntent();

         name = getDataIntent.getStringExtra("name_key");
         email=getDataIntent.getStringExtra("email_key");
         student_no=getDataIntent.getStringExtra("phone_key");
         password=getDataIntent.getStringExtra("password_key");
        //titleText.setText("Name :"+name+" \nEmail ="+email+"\n father no ="+father_no+" \n password ="+password);

        // back to sign up first page code
         back_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(Second_Sign_up.this,sign_up.class);
                 startActivity(intent);
             }
         });

        // date picker code in edit text
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                y=calendar.get(Calendar.YEAR);
                m=calendar.get(Calendar.MONTH);
                d=calendar.get(Calendar.DATE);


                datePickerDialog=new DatePickerDialog(Second_Sign_up.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month+1;
                        d = dayOfMonth;
                        datePicker.setText(m+"/"+d+"/"+y);

                    }
                },y,m,d);

                datePickerDialog.show();
            }
        });
        male.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male" ;
                female.setError(null);
            }
        }));
        female.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
                female.setError(null);
            }
        }));

    }
    // create account move to login page code
    public void nextSignUpScreen(View view)
    {
       // full_name=et_full_name.getText().toString().trim();
       // mother_no=et_mother_no.getText().toString().trim();
        //father_no=et_father_no.getText().toString().trim();
        //address=et_address.getText().toString().trim();

      //  brithDate=d+"/"+m+"/"+y;

        CreateUser();


       }

    public void CreateUser()
    {
        if(CheckEmpty() && validation())
        {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        boolean ans = object.getBoolean("Success");
                        if (ans == true) {
                            Intent intent = new Intent(Second_Sign_up.this, login.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Second_Sign_up.this,"error ="+error.getMessage(),Toast.LENGTH_LONG).show();

                }
            }){
                protected Map<String,String> getParams() throws AuthFailureError
                {
                    Map<String,String> params=new HashMap<>();
                     params.put("username_key",name);
                     params.put("email_key",email);
                     params.put("student_key",student_no);
                     params.put("password_key",password);
                     params.put("full_name_key",et_full_name.getText().toString().trim());
                     params.put("father_no_key",et_father_no.getText().toString().trim());
                     params.put("mother_no_key",et_mother_no.getText().toString().trim());
                     params.put("gender_key",gender);
                     params.put("birthDate_key",datePicker.getText().toString().trim());
                     params.put("address_key",et_address.getText().toString().trim());
                       return params;
                 }

            };
            requestQueue.add(stringRequest);
        }

    }

    public boolean CheckEmpty() {

        if (et_full_name.getText().toString().trim().isEmpty()) {
            et_full_name.setError(" Field Can't be empty");
             return false;
        } else if (et_father_no.getText().toString().trim().isEmpty()) {
              et_father_no.setError("Field Can't be empty");
              return  false;

        }else if(et_address.getText().toString().trim().isEmpty())
        {
            et_address.setError("Field Can't be empty");
            return false;

        }else if(radioGroup.getCheckedRadioButtonId() == -1){
            female.setError("Please Select gender");
            return false;
        }

       else if(datePicker.getText().toString().trim().isEmpty())
        {
            datePicker.setError("Field Can't be empty");
            return false;
        }
        else
        {

            Toast.makeText(this," true return " ,Toast.LENGTH_LONG).show();
            RadioButton r = findViewById(radioGroup.getCheckedRadioButtonId());
            gender = r.getText().toString();
            female.setError(null);
            return true;
        }
    }

    public boolean validation()
    {

        if(!et_full_name.getText().toString().trim().matches("[a-zA-Z ]+"))
        {
            et_full_name.setError("Invalid Number");
            return false;

        }else if(!et_father_no.getText().toString().trim().matches("[0-9]{10}"))
        {
            et_father_no.setError("Invalid Number");
            return false;
        }else if(!et_mother_no.getText().toString().trim().matches("[0-9]{10}"))
        {
            et_mother_no.setError("Invalid Number ");
            return false;
        }else {
            return true;
        }
    }
}
