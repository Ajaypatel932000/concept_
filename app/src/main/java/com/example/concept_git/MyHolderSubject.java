package com.example.concept_git;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolderSubject extends RecyclerView.ViewHolder {

    TextView id_tv,name_tv;
    public MyHolderSubject(@NonNull View itemView) {
        super(itemView);
      this.id_tv=itemView.findViewById(R.id.subject_id);
      this.name_tv=itemView.findViewById(R.id.subject_name);



    }
}
