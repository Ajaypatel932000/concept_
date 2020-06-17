package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

public class leave extends AppCompatActivity implements View.OnClickListener {

    RequestQueue requestQueue;
    String URL="http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/ApplayToLeave";
    EditText et_from_date,et_to_date,et_reason;
    Button submit_btn;
    ImageView back_btn;
    DatePickerDialog datePickerDialog;
    int d,y,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();


            et_from_date=findViewById(R.id.from_date);
            et_to_date=findViewById(R.id.to_date);
            et_reason=findViewById(R.id.leave_text);
            submit_btn=findViewById(R.id.leave_btn_id);
            back_btn=findViewById(R.id.cancle_btn);

            requestQueue= Volley.newRequestQueue(getApplicationContext());

            back_btn.setOnClickListener(this);
            et_from_date.setOnClickListener(this);
            et_to_date.setOnClickListener(this);
            submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cancle_btn:
                Intent intent=new Intent(leave.this,dashboard.class);
                startActivity(intent);
                break;
            case R.id.from_date:

                        Calendar calendar=Calendar.getInstance();
                        y=calendar.get(Calendar.YEAR);
                        m=calendar.get(Calendar.MONTH);
                        d=calendar.get(Calendar.DATE);


                        datePickerDialog=new DatePickerDialog(leave.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                y = year;
                                m = month+1;
                                d = dayOfMonth;
                                et_from_date.setText(m+"/"+d+"/"+y);

                            }
                        },y,m,d);

                        datePickerDialog.show();

                 break;
            case R.id.to_date:
                datePickerDialog=new DatePickerDialog(leave.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_to_date.setText(month+1+"/"+dayOfMonth+"/"+year);
                    }
                },y,m,d);
                datePickerDialog.show();
                break;
            case R.id.leave_btn_id:


                  applyToLeave();


                break;

                default:
                    Toast.makeText(leave.this,"Leave page Defult case Executed ",Toast.LENGTH_LONG).show();
                    break;


        }
    }


    public boolean checkEmpty() {

        if (et_from_date.getText().toString().trim().isEmpty()) {


            et_from_date.setError("Field Can't be empty");
            return false;

        } else if (et_to_date.getText().toString().trim().isEmpty()) {

            et_to_date.setError("Field Can't be empty");
            return false;

        } else if (et_reason.getText().toString().trim().isEmpty()) {

            et_reason.setError("Field Can't be empty");
            return false;


        } else
            {
            return true;
        }

    }
    public boolean Validation()
    {
                if (et_from_date.getText().toString().trim().compareTo(et_to_date.getText().toString().trim()) > 0) {

                    et_from_date.setError("Invalid Date ");
                    return false;
                } else if (!et_reason.getText().toString().trim().matches("[a-zA-Z ]+")) {
                    et_reason.setError("Invalid text");
                    return false;
                } else {
                    return true;
                }
    }

   public  void applyToLeave()
   {
            if(checkEmpty() && Validation())
            {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Boolean ans = object.getBoolean("key");
                            if (ans) {

                                Toast.makeText(leave.this, "Leave Applied ", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(leave.this,"Leave Already applied ", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                        }
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(leave.this, "Volley Response Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    // send the data
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("from_date_key",et_from_date.getText().toString().trim());
                        params.put("to_date_key",et_to_date.getText().toString().trim());
                        params.put("reason_key",et_reason.getText().toString().trim());
                        params.put("enroll_key",login.ENROLLMENT_NO);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }else
            {
                Toast.makeText(leave.this,"Please Check Your Selection ",Toast.LENGTH_LONG).show();

            }


   }
}
