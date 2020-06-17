package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class leave_recycle_view extends AppCompatActivity {

    ImageView back_btn;
    public String from,message,to,URL="http://10.0.2.2:5467/PROJECT2020/aayesha.asmx/leaveApprovement";
    boolean status;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_recycle_view);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

         // first create leave_recycle.xml than raw.xml
         // second we create three class
        // 1) Model it contain the variable and gatter and setter method
        // 2) Second create MyHolder class it will  bind the xml controls;
        // 3)  Third MyAdapter Class which create layout and bind the data on that layout
        back_btn=findViewById(R.id.cancle_btn);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// Recycle view in the form of liner_layout
        myAdapter=new MyAdapter(this,getMyList());
        recyclerView.setAdapter(myAdapter);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(leave_recycle_view.this,dashboard.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Model> getMyList()
    {
      final    ArrayList<Model> models= new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try{

                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("leave_root");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        Log.d("Tag",String.valueOf(jsonArray.length()));
                        JSONObject object=jsonArray.getJSONObject(i);

                        from=object.getString("from");
                        message=object.getString("reason");
                        status=Boolean.valueOf(object.getString("status"));
                        to=object.getString("to");

                              // here create model object and setter the data into setter method
                        Model m1=new Model();
                        m1.setFrom (from);
                        m1.setDesc(message);
                        m1.setTo(to);

                        if(status) {
                            m1.setImg(R.drawable.ic_done_black_24dp);
                        }else
                        {
                            m1.setImg(R.drawable.ic_clear);
                        }
                        models.add(m1);

                        Log.d("FROM",from);
                        Log.d("reason",message);
                        Log.d("status",String.valueOf(status));
                        Log.d("To",to);
                        Log.d("To",to);
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

                Toast.makeText(leave_recycle_view.this,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enroll_key", login.ENROLLMENT_NO);
                return params;
            }


        };
        requestQueue.add(stringRequest);


        Log.d("TAG","This will not done");
        return models;


    }
}
