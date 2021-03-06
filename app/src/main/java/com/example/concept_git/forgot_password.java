package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class forgot_password extends AppCompatActivity implements View.OnClickListener {

    Button verify_btn,clear_btn;

    ImageView back_btn;
   PinView pinView;
   RequestQueue requestQueue;
   String URL="http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/getUsernameFromMobileNumber";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        back_btn = findViewById(R.id.back_btn);
        verify_btn = findViewById(R.id.code_id);
        pinView=findViewById(R.id.pinview);
        clear_btn=findViewById(R.id.clear);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        back_btn.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        clear_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_btn:
                Intent intent = new Intent(forgot_password.this,login.class);
                startActivity(intent);
                break;

            case R.id.code_id:
                Toast.makeText(forgot_password.this,pinView.getText().toString(),Toast.LENGTH_LONG).show();
                Intent getvalue=getIntent();

                int otp= Integer.parseInt(getvalue.getStringExtra("otp"));
              //  Toast.makeText(forgot_password.this,"OTP ="+getvalue,Toast.LENGTH_LONG).show();
               if(Integer.parseInt(pinView.getText().toString())==otp)
               {
                   Intent intent1 = new Intent(forgot_password.this, change_password.class);
                  startActivity(intent1);
               }else
               {
                   pinView.setError("Please Enter valid otp");
                   Toast.makeText(forgot_password.this,"Invalid OTP",Toast.LENGTH_LONG).show();
               }

                break;
            case R.id.clear:
                pinView.getText().clear();
                break;
            default:

                Toast.makeText(forgot_password.this, "Invalid Click", Toast.LENGTH_LONG).show();
                break;
        }
    }


}



