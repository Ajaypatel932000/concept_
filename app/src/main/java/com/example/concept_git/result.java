package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.concept_git.result_subject.AdapterResult;
import com.example.concept_git.result_subject.GetSet;
import com.example.concept_git.show_test.ListItems_test;
import com.example.concept_git.show_test.MyAdapter_test_list;
import com.example.concept_git.test_subject.ListItems;
import com.example.concept_git.test_subject.MyAdapter_Sub;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class result extends AppCompatActivity {

    ImageView result_back;
    MaterialSpinner spinner;
    String URL = "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/getSubjects";
    String URL1 = "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/GivenTestList";
    Map<String, String> dict;
    List<String> list;
    String subject_id;
    RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<GetSet> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        result_back = findViewById(R.id.result_back);
        spinner = findViewById(R.id.spinner);

        recyclerView = findViewById(R.id.result_recyclearview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsList = new ArrayList<>();
        dict = new HashMap<>();
        list = new ArrayList<>();
        list.add(0, "--SELECT--");
        loadSubject();

        spinner.setItems(list);
        result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(result.this, dashboard.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                itemsList.clear();
                subject_id = dict.get(String.valueOf(position));
                if(position!=0)
                     loadTest(subject_id);
                Snackbar.make(view, "  p " + position, Snackbar.LENGTH_LONG).show();

            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    void loadTest(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data... ");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Test");

                    Toast.makeText(result.this, "Json Length =" + jsonArray.length(), Toast.LENGTH_LONG).show();

                    if(jsonArray.length()==0)
                    {
                        GetSet items = new GetSet("No","Test Not Found");
                        itemsList.add(items);
                        adapter = new AdapterResult(itemsList, getApplicationContext());
                        recyclerView.setAdapter(adapter);



                    }else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            GetSet items = new GetSet(object.getString("ID"), object.getString("Name"));
                            itemsList.add(items);
                        }
                        adapter = new AdapterResult(itemsList, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(result.this, "message =" + e.getMessage(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(result.this, "Volley  error " + error.getMessage()+ login.ENROLLMENT_NO+" s_id"+id, Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enroll_id", login.ENROLLMENT_NO);
                params.put("subject_id", id);
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }


    void loadSubject() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading... ");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Subjects");
                    Toast.makeText(result.this, "Json Length =" + jsonArray.length(), Toast.LENGTH_LONG).show();


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String val = String.valueOf(i + 1);
                        dict.put(val, object.getString("ID"));
                        //list1.add(i+1,object.getString("ID"));
                        list.add(i + 1, object.getString("Name"));

                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(result.this, "message =" + e.getMessage(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(result.this, "Result.java " + error.getMessage(), Toast.LENGTH_LONG).show();
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

