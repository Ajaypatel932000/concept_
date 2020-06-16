package com.example.concept_git;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyHolder2> {

   Context c;
   ArrayList<Model2> models;

    public MyAdapter2(Context c, ArrayList<Model2> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_card,null);

        return  new MyHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder2 holder, int position) {

        holder.dateTv.setText(models.get(position).getDate());
        holder.imageView.setImageResource(models.get(position).getImg());
        holder.dayTv.setText(models.get(position).getDay());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
