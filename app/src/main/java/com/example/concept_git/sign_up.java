package com.example.concept_git;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {

    ImageView back_btn, logo;
    TextView titleText;
    Button next_btn;
    RequestQueue requestQueue;
    String URL = "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/checkUserName";
    EditText e_userName, e_pass, e_confrim_pass, e_student_no, e_email;
    boolean ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // Token Generate code


        back_btn = findViewById(R.id.back_button);
        next_btn = findViewById(R.id.next_btn_id);
        titleText = findViewById(R.id.sign_up);
        logo = findViewById(R.id.logo);

        e_userName = findViewById(R.id.et_userName);
        e_email = findViewById(R.id.et_Email);
        e_pass = findViewById(R.id.et_pass);
        e_confrim_pass = findViewById(R.id.et_confirm_pass);
        e_student_no = findViewById(R.id.et_phone);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        e_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(sign_up.this,"Focus",Toast.LENGTH_LONG).show();
                } else {

                    if (!e_userName.getText().toString().trim().isEmpty()) {
                        //   Toast.makeText(sign_up.this, "Lost Focus", Toast.LENGTH_LONG).show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                     ans = object.getBoolean("UserNameSucces");
                                    if (ans) {
                                        e_userName.setError("UserName already Exits");

                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(sign_up.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(sign_up.this, "On ErrorReponse Execute " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("username_key", e_userName.getText().toString().trim());
                                return params;
                            }

                        };
                        requestQueue.add(stringRequest);


                    }

                }

            }



        });

// back to login screen code
        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this, login.class);
                startActivity(intent);
            }
        });


    }

    // sign up to next screen code
    public void nextSignUpScreen(View view) {
        if (checkEmpty() && validation() && !ans) {
            Intent intent = new Intent(sign_up.this, Second_Sign_up.class);
            intent.putExtra("name_key", e_userName.getText().toString().trim());
            intent.putExtra("email_key", e_email.getText().toString().trim());
            intent.putExtra("phone_key", e_student_no.getText().toString().trim());
            intent.putExtra("password_key", e_pass.getText().toString().trim());
            // Add transition


            Pair[] pairs = new Pair[4];

            pairs[0] = new Pair<View, String>(back_btn, "back_btn");
            pairs[1] = new Pair<View, String>(titleText, "design_text");
            pairs[2] = new Pair<View, String>(next_btn, "next_btn");
            pairs[3] = new Pair<View, String>(next_btn, "design_logo");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(sign_up.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    public boolean checkEmpty() {

        if (e_userName.getText().toString().trim().isEmpty()) {
            e_userName.setError("Field Can't be empty");
            return false;

        } else if (e_email.getText().toString().trim().isEmpty()) {

            e_email.setError("Field Can't be empty");
            return false;

        } else if (e_student_no.getText().toString().trim().isEmpty()) {

            e_student_no.setError("Field Can't be empty");
            return false;


        } else if (e_pass.getText().toString().trim().isEmpty()) {
            e_pass.setError("Field Can't be empty");
            return false;

        } else if (e_confrim_pass.getText().toString().trim().isEmpty()) {
            e_confrim_pass.setError("Field Can't be empty");
            return false;

        } else {
            return true;
        }

    }


    public boolean validation() {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(e_email.getText().toString().trim());

       Pattern pattern1;
        Matcher matcher1;
        String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        // we create pattern
        pattern1 = Pattern.compile(PASSWORD_PATTERN);
        //create object of matcher
        matcher1 = pattern1.matcher(e_pass.getText().toString().trim());

        if (!matcher.matches()) {
            e_email.setError("Invalid Email");
            return false;

        } else if (!e_student_no.getText().toString().trim().matches("[0-9]{10}")) {
            e_student_no.setError("Invalid Number");
            return false;

        }
        if (!matcher1.matches()) {
            e_pass.setError("Password length minimum: 8. Non-alphanumeric characters required: 1.");
            e_pass.getText().clear();

            return false;

        } else if (!e_pass.getText().toString().trim().equals(e_confrim_pass.getText().toString().trim())) {
            e_confrim_pass.setError("Invalid Password");
            e_confrim_pass.getText().clear();
            return false;

        } else {
            return true;
        }
    }
}




