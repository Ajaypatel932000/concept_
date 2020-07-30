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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class new_password extends AppCompatActivity implements View.OnClickListener{

    String URL="http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/changePassword";
    RequestQueue requestQueue;
    ImageView back_btn;
    Button change_pss_btn;
    EditText et_curr,et_new,et_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        back_btn=findViewById(R.id.back_btn);
        et_curr=findViewById(R.id.curr_pass);
        et_new=findViewById(R.id.new_pass);
        et_confirm=findViewById(R.id.confirm_pass);
        change_pss_btn=findViewById(R.id.change_pass_btn_id);


         requestQueue= Volley.newRequestQueue(getApplicationContext());


         change_pss_btn.setOnClickListener(this);
         back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.back_btn:
                Intent intent=new Intent(new_password.this,dashboard.class);
                startActivity(intent);
                break;

            case R.id.change_pass_btn_id:
                  changePassword();
                  break;

                default:
                    Toast.makeText(this,"Default Execute new password",Toast.LENGTH_LONG).show();
               break;

        }

    }

    private void changePassword()
    {
        if(checkEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);

                        boolean ans = object.getBoolean("pass_key");
                        if (ans == true) {


                            Intent intent = new Intent(new_password.this, dashboard.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(new_password.this, "Password Not Change ", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(new_password.this, "JSONException Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(new_password.this, "Volley Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                // send the data
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id_key", login.USER_NAME_);
                    params.put("old_pass_key", et_curr.getText().toString().trim());
                    params.put("new_pass_key", et_new.getText().toString().trim());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }


    public boolean checkEmpty() {

        Pattern pattern1;
        Matcher matcher1;
        String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        // we create pattern
        pattern1 = Pattern.compile(PASSWORD_PATTERN);
        matcher1 = pattern1.matcher(et_new.getText().toString().trim());


        if (et_curr.getText().toString().trim().isEmpty()) {
            et_curr.setError("Field Can't be empty");
            return false;

        } else if (et_new.getText().toString().trim().isEmpty()) {

            et_new.setError("Field Can't be empty");
            return false;

        } else if (et_confirm.getText().toString().trim().isEmpty()) {

            et_confirm.setError("Field Can't be empty");
            return false;

        }else if(!matcher1.matches()){
            et_new.setError("Password length minimum: 8. Non-alphanumeric characters required: 1.");
            et_new.getText().clear();

            return false;

        }else  if(!et_new.getText().toString().trim().equals(et_confirm.getText().toString().trim())) {
            et_confirm.setError("Invalid Password");
            et_confirm.getText().clear();
            return false;
        }else {

            return true;
        }

    }


}
