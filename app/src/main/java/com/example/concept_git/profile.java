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
import android.widget.ImageView;
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
    ImageView select_img;
    private  Bitmap bitmap;
    private final int Image_Request=1;
    EditText et_name,et_email,et_number;
    Button update_btn;
    private  final int IMG_REQUEST=1;


    String URL="http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/getProfile";
    String img_path="http://10.0.2.2:5467/PROJECT2020/App_Themes/Theme1/assets/images/";
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
        select_img=findViewById(R.id.select_img_id);

        requestQueue= Volley.newRequestQueue(getApplicationContext());


        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });



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
                    String  img_=object.getString("i");
                    if(img_!=null)
                       new DownloadImageTask(img_btn).execute(img_path+img_);
                   else
                       img_btn.setImageResource(R.drawable.ic_person);

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
                         final String img=ImageToString(bitmap);
                         StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/profileUpdate", new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                 try {
                                     JSONObject object = new JSONObject(response);
                                     et_name.setText(object.getString("n"));
                                     et_email.setText(object.getString("e"));
                                     et_number.setText(object.getString("m"));

                                      new DownloadImageTask(img_btn).execute(img_path+object.getString("i"));

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
                                 params.put("img",img);
                                 params.put("username_key", login.USER_NAME_);
                                 return params;
                             }


                         };
                         requestQueue.add(stringRequest);
                     }
                 }

             });


    }
    private void selectImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            try {

                Uri path = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                img_btn.setImageBitmap(bitmap);
                Toast.makeText(profile.this,"Path ="+path+" Bitmap  ="+bitmap,Toast.LENGTH_LONG).show();

            }catch(IOException e)
            {
                Toast.makeText(profile.this,"Error "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }


    private String ImageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgeByte=byteArrayOutputStream.toByteArray();

        Toast.makeText(profile.this," String ="+imgeByte,Toast.LENGTH_LONG).show();

        return Base64.encodeToString(imgeByte,Base64.DEFAULT);

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
