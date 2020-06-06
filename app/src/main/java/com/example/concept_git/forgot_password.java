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

    Button verify_btn;
    ImageView back_btn;
   PinView pinView;
   RequestQueue requestQueue;
   String URL="http://10.0.2.2:28972/WebService_Json/aayesha.asmx/getUsernameFromMobileNumber";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        back_btn = findViewById(R.id.back_btn);
        verify_btn = findViewById(R.id.code_id);
        pinView=findViewById(R.id.pinview);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        back_btn.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
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
  //              sendMessage();
                Intent intent1 = new Intent(forgot_password.this, change_password.class);
                startActivity(intent1);

                break;
            default:

                Toast.makeText(forgot_password.this, "Invalid Click", Toast.LENGTH_LONG).show();
                break;
        }
    }


 /*   public void sendMessage()
    {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    double otp = object.getDouble("otp_key");

                    if (otp == 0222) {
                        Intent intent = new Intent(forgot_password.this, change_password.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(forgot_password.this,"error ="+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            //protected Map<String,String> getParams() throws AuthFailureError
            //{
               // Map<String,String> params=new HashMap<>();
                //params.put("username_key",name);
                //return params;
            //}

        };
        requestQueue.add(stringRequest);
    }
*/
}




