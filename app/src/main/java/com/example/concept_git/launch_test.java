package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class launch_test extends AppCompatActivity implements View.OnClickListener {
    Button nextBtn,prvBtn, showIndexBtn;
    TextView textView, textViewAns,textViewQuestionId,textViewMark;
    RadioGroup radioGroup;
    String selectedAns ="", rightAns;
    int id, i = 0,optionId=-1;
    String markofQuestion="";

    String URL1="http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/acceptTestValues",URL = "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/sendQuestionDetails";
    RequestQueue requestQueue;
    Map<Integer,Integer> dictionary;
    Map<Integer,Integer> marks;
    Map<String,String> QuestionAns;
    String test_id,QuestionId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_test);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        final String sub=intent.getStringExtra("subject_id");
       test_id=intent.getStringExtra("test_id");

        textViewMark=findViewById(R.id.mark_id);
        textViewQuestionId=findViewById(R.id.question_id);
        prvBtn = findViewById(R.id.button4);
        showIndexBtn = findViewById(R.id.button5);
        nextBtn = findViewById(R.id.button);
        textView = findViewById(R.id.text);
        textViewAns = findViewById(R.id.ans_id);
        radioGroup = findViewById(R.id.radioGroup);
        dictionary = new HashMap<Integer, Integer>();
        marks=new HashMap<Integer, Integer>();
        QuestionAns=new HashMap<>();
        textViewQuestionId.setVisibility(View.GONE);
        textViewAns.setVisibility(View.GONE);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        showIndexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(launch_test.this);
        alertDialog2.setCancelable(false);
        // Setting Dialog Title
        alertDialog2.setTitle("Start Test...");

       // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want give test? " + intent.getStringExtra("subject_id"));

         // Setting Icon to Dialog
        //alertDialog2.setIcon(R.drawable.delete);

       // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();


                        showTest();


                    }
                });

        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                        Intent intent = new Intent(launch_test.this, Avalible_test.class);
                        intent.putExtra("enroll_id",login.ENROLLMENT_NO);
                        intent.putExtra("subject_id",sub);
                        startActivity(intent);

                    }
                });

// Showing Alert Dialog
        alertDialog2.show();

        prvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOption();
                setMarks();
                selectedAns="";
                QuestionId="";
                markofQuestion="";
                i--;
                if(dictionary.containsKey(i)){
                    optionId=dictionary.get(i);
                }else
                {
                    optionId=-1;

                }
                showTest();


            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveOption();
                setMarks();
                selectedAns="";
                QuestionId="";
                markofQuestion="";
                i++;
                if(dictionary.containsKey(i)){
                    optionId=dictionary.get(i);
                }else
                {
                    optionId=-1;
                }
                showTest();

            }
        });





    }


    void addRadioButton(String[] optionName, int len) {
        radioGroup.removeAllViews();
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        for (int j = 0; j <len; j++) {

            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(j);//View.generateViewId()
            radioButton.setText(optionName[j]);//+ radioButton.getId()
            radioButton.setTextColor(Color.BLACK);
            radioButton.setTextSize(15);
            radioButton.setOnClickListener(this);
            radioButton.setChecked(j==optionId);
            radioGroup.addView(radioButton);

        }

    }
    void  setMarks()
    {
        int ans;
        try {
            if(!selectedAns.isEmpty()) {
                if (rightAns.equals(selectedAns)) {
                    ans = Integer.parseInt(markofQuestion);

                } else {
                    ans = 0;
                }
                if(marks.containsKey(i))
                {
                    marks.remove(i);
                    marks.put(i,ans);
                }else
                {
                    marks.put(i,ans);
                }
            }
        }catch (NullPointerException e)
        {
            Toast.makeText(this,"null pointer "+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    void saveOption()
    {
        try {
            if (!selectedAns.isEmpty()) {
                if (dictionary.containsKey(i)) { //dict{0:1,1:0}
                    dictionary.remove(i);
                    dictionary.put(i, id);
                    QuestionAns.remove(QuestionId);
                    QuestionAns.put(QuestionId, String.valueOf(id));

                } else {
                    dictionary.put(i, id);
                    QuestionAns.put(QuestionId, String.valueOf(id));
                }



            } else
            {
                optionId = -1;
                QuestionId="";
            }
        }catch (NullPointerException e)
        {
            Toast.makeText(this,"null"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        id = v.getId();

        Log.d("id", String.valueOf(id));
        Log.d("TextAns", ((RadioButton) v).getText().toString());


        selectedAns = ((RadioButton) v).getText().toString();
        Log.d("rightAns", textViewAns.getText().toString());
        rightAns = textViewAns.getText().toString();
        QuestionId= textViewQuestionId.getText().toString().trim();
        markofQuestion=textViewMark.getText().toString().trim();


    }

    void showTest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Test");

                    Toast.makeText(launch_test.this, "Json Length =" + jsonArray.length(), Toast.LENGTH_LONG).show();
                    if (jsonArray.length() != 0) {

                        Log.d("len", String.valueOf(jsonArray.length()));
                        if (i < jsonArray.length()) {
                            if (i == 0) {
                                prvBtn.setVisibility(View.GONE);
                            } else {
                                prvBtn.setVisibility(View.VISIBLE);
                            }

                            if (i == jsonArray.length() - 1) {
                                nextBtn.setVisibility(View.GONE);
                            } else {
                                nextBtn.setVisibility(View.VISIBLE);
                            }
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONArray question_id=object.getJSONArray("QuestionId");
                            textViewQuestionId.setText(question_id.get(0).toString());

                            JSONArray question = object.getJSONArray("Question");
                            textView.setText((i+1)+"  "+question.get(0) + "\n");

                            JSONArray mark = object.getJSONArray("Marks");
                            textViewMark.setText(mark.get(0).toString());
                            JSONArray rightOption = object.getJSONArray("CorrectAnswer");
                            textViewAns.setText(String.valueOf(rightOption.get(0)));
                            JSONArray option = object.getJSONArray("Option");
                            int len = option.length();
                            String[] stringArray; // Array Declared
                            stringArray = new String[len];
                            for (int j = 0; j < option.length(); j++) {

                                // Log.d("Tag", String.valueOf(option.get(j)));
                                String val = (String) option.get(j);
                                stringArray[j] = val;


                            }
                            addRadioButton(stringArray,len);
                            Log.d("i", String.valueOf(i));
                        } else {
                            // hide the next button
                            Toast.makeText(launch_test.this, "JsonLenght =" + jsonArray.length() + " i=" + i, Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (Exception e) {
                    Toast.makeText(launch_test.this, "Exception =" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                Toast.makeText(launch_test.this, "Response", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(launch_test.this, "Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("test_id",test_id);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    void showResult() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(launch_test.this);
        alertDialog.setTitle("Are you sure ?");
        alertDialog.setMessage("Submit Test");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                saveOption();
                setMarks();
                int total=0;
                for ( Map.Entry<Integer, Integer> entry : marks.entrySet()) {
                    Integer key = entry.getKey();
                    Integer tab =entry.getValue();


                    total+=tab;
                    Log.i("totalMarks", String.valueOf(entry.getValue()));

                    // do something with key and/or tab
                }
                Log.i("total", String.valueOf(total));
                QuestionAns.put("enroll_id",login.ENROLLMENT_NO);
                QuestionAns.put("test_id",test_id);//change here
                QuestionAns.put("result", String.valueOf(total));
                final JSONObject paramJson = new JSONObject();

                for ( Map.Entry<String,String> entry : QuestionAns.entrySet()) {
                    String key = entry.getKey();
                    String value =entry.getValue();


                    try {
                        paramJson.put(key,value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(launch_test.this,"Exception in json",Toast.LENGTH_LONG).show();
                    }


                    Log.i("QuestionAns",entry.getKey()+" = "+entry.getValue());

                    // do something with key and/or tab
                }



                StringRequest stringRequest1=new StringRequest(Request.Method.POST,URL1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {
                                JSONObject jsonObject = new JSONObject(response);

                                String val = jsonObject.getString("Success");
                                if ("true".equalsIgnoreCase(val)) {
                                    Intent intent = new Intent(launch_test.this, dashboard.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(launch_test.this, "Invalid Success", Toast.LENGTH_LONG).show();
                                }
                            }else
                            {
                                Toast.makeText(launch_test.this, "Null Response", Toast.LENGTH_LONG).show();
                            }
                            }catch(JSONException e)
                            {
                                Toast.makeText(launch_test.this, " Exception =" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(launch_test.this," Exception ="+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("test_answered",paramJson.toString());

                        return params;

                    }

                };

                 requestQueue.add(stringRequest1);

            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        alertDialog.show();

    }
}
