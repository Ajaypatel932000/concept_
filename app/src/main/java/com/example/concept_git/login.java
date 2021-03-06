package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class login extends AppCompatActivity {

   static  String USER_NAME_,ENROLLMENT_NO,Batch_Name;
    TextView pass_link;
    Button login_btn, registration;
    EditText et_name, et_password;
    RequestQueue requestQueue;
    String pass, userName, URL = "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/validateUser";
   // http://localhost:5467/PROJECT2020/aayesha.asmx/validateUser
    //http://localhost:28972/WebService_Json/aayesha.asmx/validateUser

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        pass_link = findViewById(R.id.link_id);
        login_btn = findViewById(R.id.login_id);
        et_name = findViewById(R.id.et_Email);
        et_password = findViewById(R.id.et_pass);
        registration = findViewById(R.id.reg_id);

        pass_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, phone_number.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login_fun();

            }
        });


    }

    // Moving to singUp form
    public void SignUp(View view) {
        Intent intent = new Intent(login.this, sign_up.class);
        startActivity(intent);

    }

    public void login_fun() {
        userName = et_name.getText().toString().trim();
        pass = et_password.getText().toString().trim();
        if(validateUserName()) {
            Toast.makeText(this, "name =" + userName + " password= " + pass, Toast.LENGTH_LONG).show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String user=object.getString("User");

                        if (user.equalsIgnoreCase("true")) {
                              ENROLLMENT_NO=object.getString("EnrollmentId");
                              Batch_Name=object.getString("BatchName");
                            FirebaseMessaging.getInstance().subscribeToTopic(Batch_Name);

                            USER_NAME_=et_name.getText().toString().trim();
                            Toast.makeText(login.this, "Login Success "+ENROLLMENT_NO, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(login.this,dashboard.class);

                            startActivity(intent);

                        } else if(user.equalsIgnoreCase("false")) {

                            Toast.makeText(login.this,"Please Visit Center for further Proceses",Toast.LENGTH_LONG).show();

                        }else if(user.equalsIgnoreCase("Invalid username or password"))
                        {
                            Toast.makeText(login.this, "Invalid  Password  ", Toast.LENGTH_LONG).show();

                        }

                    } catch (JSONException e) {

                        Toast.makeText(login.this,"Login Exception ="+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(login.this, "Volley Response Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                // send the data
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", userName);
                    params.put("pass", pass);
                    params.put("fcmToken",MainActivity.token);
                    return params;
                }
            };
            requestQueue.add(stringRequest);

        }
    }

    private boolean validateUserName()
    {
        if(userName.isEmpty())
        {
            et_name.setError(" UserName Requried ",getDrawable(R.drawable.ic_email));
            return false;
        }else if(pass.isEmpty())
        {
            et_password.setError("Password Can't  Empty");
            return false;
        }else
        {
            return true;
        }

    }

}



