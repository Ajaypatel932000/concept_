package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


public class attendance_graph extends AppCompatActivity {
    Spinner spinner;
    ImageView back_btn;
    String[] option={"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_graph);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        back_btn=findViewById(R.id.pie_back);
        spinner=findViewById(R.id.spinner);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(attendance_graph.this,attendance_card.class);
                startActivity(intent);
            }
        });

        ArrayAdapter itemsAdapter=new ArrayAdapter(attendance_graph.this,android.R.layout.simple_spinner_item,option);
        itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(itemsAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Snackbar.make(view,"Clicked"+"Position ="+position+"id = "+id,Snackbar.LENGTH_LONG).show();
               // ((TextView)parent.getChildAt(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
