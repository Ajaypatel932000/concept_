package com.example.concept_git;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//you provide access to all the views for a data item in a view holder

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView imgView;
    TextView fromDate,toDate,message;
    public MyHolder(@NonNull View itemView) {
        super(itemView);
             // this is binding process of the raw.xml file view
        this.imgView=itemView.findViewById(R.id.img_id);
        this.fromDate=itemView.findViewById(R.id.dateTv);
        this.toDate=itemView.findViewById(R.id.date2Tv);
        this.message=itemView.findViewById(R.id.describeTV);


    }
}
