package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.example.concept_git.show_test.ListItems_test;
import com.example.concept_git.show_test.MyAdapter_test_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Avalible_test extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems_test> itemsList;
    RequestQueue requestQueue;
   public  static String batch,subject;
    ImageView back;
    String URL = "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/getTestList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalible_test);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        back=findViewById(R.id.test_back);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview_test);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsList = new ArrayList<>();
        Intent data=getIntent();
        //batch=data.getStringExtra("batch_id");
        subject=data.getStringExtra("subject_id");
        Toast.makeText(this, "subject_id "+data.getStringExtra("subject_id"),Toast.LENGTH_LONG).show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Avalible_test.this,test.class);
                startActivity(intent);

            }
        });


        loadTest();
    }
    void loadTest() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data... ");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Test");
                    Toast.makeText(Avalible_test.this, "Json Length =" + jsonArray.length(), Toast.LENGTH_LONG).show();


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListItems_test items = new ListItems_test(object.getString("ID"), object.getString("Name"));
                        itemsList.add(items);
                    }
                    adapter = new MyAdapter_test_list(itemsList, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(Avalible_test.this, "message =" + e.getMessage(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(Avalible_test.this, "Volley  error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enroll_id",login.ENROLLMENT_NO);
                params.put("subject_id",subject);
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
    }
