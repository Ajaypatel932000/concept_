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
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.concept_git.notification_list.NotiListItems;
import com.example.concept_git.notification_list.NotificationAdapter;
import com.example.concept_git.show_test.ListItems_test;
import com.example.concept_git.show_test.MyAdapter_test_list;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class notification extends AppCompatActivity {
    MaterialSpinner spinner;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<NotiListItems> notiListItems;
    RequestQueue requestQueue;
    ImageView imageView;
    String URl="http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/ShowAllNotification";
    private static final String[] MONTHS_NAME = {
            "--SELECT--",
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };
    // Note: here we use test_card.xml file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems(MONTHS_NAME);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview_notification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageView=findViewById(R.id.noti_back);

        notiListItems = new ArrayList<>();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(notification.this,dashboard.class);
                startActivity(intent);

            }
        });

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item+"p "+position, Snackbar.LENGTH_LONG).show();
               notiListItems.clear();
              loadNotification(position);
            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });

    }
    void loadNotification(final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data... ");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("Success");
                    JSONObject object=jsonArray.getJSONObject(0);

                    Toast.makeText(notification.this, "Json Length =" + jsonArray.length(), Toast.LENGTH_LONG).show();
                        if(object.getString("date").equalsIgnoreCase("false") && object.getString("message").equalsIgnoreCase("false")) {
                            Toast.makeText(notification.this,"Notification false",Toast.LENGTH_LONG).show();
                            NotiListItems items = new NotiListItems("No", "Data found");
                            notiListItems.add(items);
                            adapter = new NotificationAdapter(notiListItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);


                        }else {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object1 = jsonArray.getJSONObject(i);
                                NotiListItems items = new NotiListItems(object1.getString("date"), object1.getString("message"));
                                notiListItems.add(items);

                            }

                            adapter = new NotificationAdapter(notiListItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(notification.this, "message =" + e.getMessage(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(notification.this, "Volley  error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enroll_id",login.ENROLLMENT_NO);
                params.put("month", String.valueOf(position));
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}

