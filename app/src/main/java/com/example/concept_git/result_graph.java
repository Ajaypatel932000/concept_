package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class result_graph extends AppCompatActivity {
    ImageView back_btn;
    RequestQueue requestQueue;
    PieChart chart;
    String URL = "http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/DisplayAllOverResult", test_id;
    int[] color = new int[]{Color.parseColor("#4563EE"), Color.parseColor("#5570ED"), Color.parseColor("#3A5AE8")};
    TextView textViewObtain,textViewTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_graph);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        back_btn = findViewById(R.id.chart_back);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //",,,,,#6495ed,,#4682b4,#0abab5,

        textViewObtain=findViewById(R.id.obtain_id);
        textViewTotal=findViewById(R.id.total_id);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(result_graph.this, result.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        test_id = intent.getStringExtra("test_id");

        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(15f);
        chart.setEntryLabelColor(Color.WHITE);
//        chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(15f);

        loadTestMarksDetails();
    }

    void loadTestMarksDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data... ");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object1 = jsonObject.getJSONObject("Test_Result");


                    String total_mark = object1.getString("Total_Marks");
                    String correct = object1.getString("Correct_Answered");
                    String incorrect = object1.getString("Incorrect_Answered");
                    String skipped = object1.getString("Skipped");
                    String mark = object1.getString("Obtained_Marks");

                    textViewTotal.setText(total_mark);
                    textViewObtain.setText(mark);
                    ArrayList<PieEntry> entries = new ArrayList<>();
                    entries.add(new PieEntry((float) Float.valueOf(correct), "Correct"));
                    entries.add(new PieEntry((float) Float.valueOf(incorrect), "Incorrect"));
                    entries.add(new PieEntry((float) Float.valueOf(skipped), "Skipped"));
                    PieDataSet dataSet = new PieDataSet(entries, "Test");

                    dataSet.setDrawIcons(false);

                    dataSet.setSliceSpace(5f);
                    dataSet.setIconsOffset(new MPPointF(0, 40));
                    dataSet.setSelectionShift(10f);
                    dataSet.setColors(color);

                    PieData data = new PieData(dataSet);
                    data.setValueFormatter(new PercentFormatter(chart));
                    data.setValueTextSize(20f);

                    data.setValueTextColor(Color.WHITE);
                    // data.setValueTypeface();

                    chart.setCenterText("Result");
                    chart.setCenterTextSize(15f);
                    chart.setCenterTextColor(Color.BLACK);
                    chart.setData(data);

                    // undo all highlights
                    chart.highlightValues(null);

                    chart.invalidate();
                    Toast.makeText(result_graph.this, "mark =" + total_mark, Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(result_graph.this, "message =" + e.getMessage(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(result_graph.this, "Volley  error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enroll_id", login.ENROLLMENT_NO);
                params.put("test_id", test_id);
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}
