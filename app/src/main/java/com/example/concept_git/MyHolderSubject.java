package com.example.concept_git;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolderSubject extends RecyclerView.ViewHolder implements View.OnClickListener {
// make it this package private;

   ItemClickListener itemClickListener; // we create ItemClicListener interface object
    TextView id_tv,name_tv;
    MyHolderSubject(@NonNull View itemView) {
        super(itemView);
      this.id_tv=itemView.findViewById(R.id.subject_id);
      this.name_tv=itemView.findViewById(R.id.subject_name);
      id_tv.setVisibility(itemView.GONE);

      itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        itemClickListener.OnItemClicListener(v,getLayoutPosition());      // call the method of interface  set the parameter
    }
    public void setItemClickListener(ItemClickListener ic)
    {
          this.itemClickListener=ic;
    }
}
