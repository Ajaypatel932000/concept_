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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class phone_number extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText et_userName;
    Button send_btn;
    ImageView back_btn;
    String mobile_no, URL = "http://10.0.2.2:28972/WebService_Json/aayesha.asmx/checkUserName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        et_userName = findViewById(R.id.et_phone);
        send_btn = findViewById(R.id.send_btn_id);
        back_btn=findViewById(R.id.cancle_btn);

       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(phone_number.this,login.class);
               startActivity(intent);
           }
       });
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                          boolean  ans = object.getBoolean("UserNameSucces");
                            if (ans) {

                                sendUserName();
                                Intent intent = new Intent(phone_number.this, forgot_password.class);
                                startActivity(intent);



                            } else {
                                et_userName.setError("Invalid UserName");
                                Toast.makeText(phone_number.this, "User Id Not Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {


                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(phone_number.this, "Phone_number.java =" + error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                })
                {
                        protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String, String> params = new HashMap<>();
                         params.put("username_key", et_userName.getText().toString().trim());
                        return params;
                        }
                  };
                requestQueue.add(stringRequest);

            }
        });
    }


    public void sendUserName()
    {
        RequestQueue requestQueue1=Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //TODO NOTHING ONLY SEND USER NAME
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(phone_number.this, "Phone_number.java =" + error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username_key", et_userName.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest1);


    }


}



