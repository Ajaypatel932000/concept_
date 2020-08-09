package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.concept_git.test_subject.ListItems;
import com.example.concept_git.test_subject.MyAdapter_Sub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> itemsList;
    RequestQueue requestQueue;
    ImageView imageView;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        URL= "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/getSubjects";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview);
        imageView=findViewById(R.id.sub_back);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsList = new ArrayList<>();
        loadData();
       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent=new Intent(test.this,dashboard.class);
               startActivity(intent);
           }
       });
    }


    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data... ");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Subjects");
                    Toast.makeText(test.this, "Json Length =" + jsonArray.length(), Toast.LENGTH_LONG).show();


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListItems items = new ListItems(object.getString("ID"), object.getString("Name"));
                        itemsList.add(items);
                    }
                    adapter = new MyAdapter_Sub(itemsList,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(test.this, "message =" + e.getMessage(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(test.this, "Volley Response error test.java " + error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enroll_key", login.ENROLLMENT_NO);

                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
}
