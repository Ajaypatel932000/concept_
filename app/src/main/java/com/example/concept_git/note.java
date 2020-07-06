package com.example.concept_git;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class note extends AppCompatActivity {

    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
       back_btn=findViewById(R.id.note_back);
       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(note.this,chapter.class);
               Intent getSub_key=getIntent();
               intent.putExtra("id_key",getSub_key.getStringExtra("sub_id"));
               startActivity(intent);
           }
       });



    }
}
