package com.example.concept_git;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyHolder2 extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView dateTv,dayTv;
    public MyHolder2(@NonNull View itemView) {
        super(itemView);

        this.imageView=itemView.findViewById(R.id.img);
       this.dateTv=itemView.findViewById(R.id.dateTv);
     this.dayTv=itemView.findViewById(R.id.titleTv);
    }
}
