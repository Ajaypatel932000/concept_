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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class login extends AppCompatActivity {

    TextView pass_link;
    Button login_btn,registration;
    TextInputEditText et_name,et_password;
    RequestQueue requestQueue;
    String pass,userName,URL="http://10.0.2.2:7990/WebService_Json/aayesha.asmx/validateUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(MyActionBar.setColor());

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        pass_link=findViewById(R.id.text_view);
        login_btn=findViewById(R.id.login_id);
        et_name=findViewById(R.id.username_tv1);
        et_password=findViewById(R.id.pass_tv2);
        registration=findViewById(R.id.regi_id);

         pass_link.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(login.this,forgot_password.class);
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
    public void SignUp(View view)
    {
        Intent intent=new Intent(login.this,sign_up.class);
        startActivity(intent);

    }

    public void login_fun() {
          userName = et_name.getText().toString().trim();
          pass = et_password.getText().toString().trim();

        Toast.makeText(this,"name ="+userName+" password= "+pass,Toast.LENGTH_LONG).show();
       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean ans=object.getBoolean("Sucess");
                      if(ans==true)
                      {
                          Toast.makeText(login.this,"Done ",Toast.LENGTH_LONG).show();
                          Intent intent=new Intent(login.this,sign_up.class);
                          startActivity(intent);

                      }else
                      {
                          Toast.makeText(login.this,"False ",Toast.LENGTH_LONG).show();
                      }

                }
                catch(JSONException e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                   Toast.makeText(login.this,"Error ="+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
        // send the data
        @Override
        protected Map<String, String> getParams () throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("id", userName);
            params.put("pass",pass);
            return params;
        }
    };
        requestQueue.add(stringRequest);;

                // send the data



    }



}

