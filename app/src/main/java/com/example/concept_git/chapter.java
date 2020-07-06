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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chapter extends AppCompatActivity {
    ProgressDialog progressDialog;
    ImageView back;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    MyAdapterChapter myAdapter2;
    public static String id;
    String ID,NAME, URL = "http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/getChapters";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        back=findViewById(R.id.chp_back);
        recyclerView = findViewById(R.id.recycleView_chapter);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chapter.this,subject.class);
                startActivity(intent);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter2=new MyAdapterChapter(this,getMyList());
        recyclerView.setAdapter(myAdapter2);



        Intent data=getIntent();
        Toast.makeText(chapter.this,"id ="+data.getStringExtra("id_key")+" name ="+data.getStringExtra("name_key"),Toast.LENGTH_LONG).show();
        id=data.getStringExtra("id_key");

    }
    private ArrayList<ModelChapter> getMyList()
    {
        final   ArrayList<ModelChapter> model=new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {


                try{

                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("Chapters");
                    Toast.makeText(chapter.this,"Json Length ="+jsonArray.length(),Toast.LENGTH_LONG).show();
                    if(jsonArray.length()!=0) {
                        for (int i = 0; i < jsonArray.length(); i++)
                        {



                            Log.d("Tag", String.valueOf(jsonArray.length()));
                            JSONObject object = jsonArray.getJSONObject(i);

                            ID = object.getString("ID");
                            NAME= object.getString("Name");

                            ModelChapter m=new ModelChapter();
                            m.setId(ID);
                            m.setName(NAME);
                            model.add(m);

                            Log.d("ID =", ID);
                            Log.d("NAME =", NAME);


                        }


                    }else
                    {
                        ModelChapter m = new ModelChapter();
                        m.setName("No Record Found");
                        model.add(m);

                    }
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText(chapter.this,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("subject_key",id);


                return params;
            }

        };
        requestQueue.add(stringRequest);
        return model;
    }

    // first we create interface of itemClicklisner
    //now gointo my MyHolderChapter class
}
