package com.example.concept_git;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profile extends AppCompatActivity {
    CircularImageView img_btn;
    private  Bitmap bitmap;
    private final int Image_Request=1;
    EditText et_name,et_email,et_number;
    Button update_btn;
    String URL="http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/getProfile";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        update_btn=findViewById(R.id.update_btn_id);
        et_name=findViewById(R.id.profile_et_userName);
        et_email=findViewById(R.id.profile_et_Email);
        et_number=findViewById(R.id.profile_et_phone);
        img_btn=findViewById(R.id.profile_img);


        requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONArray jsonArray = new JSONArray(response);
                    JSONObject object=new JSONObject(response);
                    //JSONObject object1=object.getJSONObject("Success");
                   et_name.setText(object.getString("n"));
                    et_email.setText(object.getString("e"));
                    et_number.setText(object.getString("m"));
                  //  String  img_=object.getString("i");


                } catch (JSONException e) {
                    Toast.makeText(profile.this,"profile json exception ",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(profile.this,"profile Error Response= "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("username_key",login.USER_NAME_);
                return params;
            }



        };
        requestQueue.add(stringRequest);


             update_btn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (checkEmpty() && validation()) {
                         StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/profileUpdate", new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                 try {
                                     JSONObject object = new JSONObject(response);
                                     et_name.setText(object.getString("n"));
                                     et_email.setText(object.getString("e"));
                                     et_number.setText(object.getString("m"));


                                     Toast.makeText(profile.this, "Profile Updated ", Toast.LENGTH_LONG).show();

                                 } catch (JSONException e) {
                                     Toast.makeText(profile.this, "profile json exception ", Toast.LENGTH_LONG).show();
                                 }
                             }
                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {

                                 Toast.makeText(profile.this, "profile Error Response= " + error.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }) {
                             protected Map<String, String> getParams() throws AuthFailureError {
                                 Map<String, String> params = new HashMap<>();
                                 params.put("email_key", et_email.getText().toString().trim());
                                 params.put("mobile_key", et_number.getText().toString().trim());
                                 params.put("name_key", et_name.getText().toString().trim());
                                 params.put("username_key", login.USER_NAME_);
                                 return params;
                             }


                         };
                         requestQueue.add(stringRequest);
                     }
                 }

             });


    }

    public boolean checkEmpty() {

        if (et_name.getText().toString().trim().isEmpty()) {
            et_name.setError("Field Can't be empty");
            return false;

        } else if (et_email.getText().toString().trim().isEmpty()) {

            et_email.setError("Field Can't be empty");
            return false;

        } else if (et_number.getText().toString().trim().isEmpty()) {

            et_number.setError("Field Can't be empty");
            return false;

        } else
            {
            return true;
        }

    }
    public boolean validation()
    {
        Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(et_email.getText().toString().trim());



        if(!et_name.getText().toString().trim().matches("[a-zA-Z ]+"))
        {
            et_name.setError("Invalid Name");
            return false;

        }else if(!et_number.getText().toString().trim().matches("[0-9]{10}"))
        {
            et_number.setError("Invalid Number");
            return false;
        }else if(!matcher.matches())
        {
            et_email.setError("Invalid Email ");
            return false;
        }else {
            return true;
        }
    }
}
