package com.example.concept_git;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;  // this is array list create  list of array   which parameter defines in our model class

// now create parameterize constructor  press alt+insert

// this constructor model class variable and Context
    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // create view variable and
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.raw, null);  // here we create dynamic card view  View


        return new MyHolder(view); // this will return our view to Holder class
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

  // this is bind the date on view holder class variable and variable select of myHolder class set the text
        // text will get from uper class contructor and models  class variable
       holder.fromDate.setText(models.get(position).getFrom());
       holder.toDate.setText(models.get(position).getTo());
       holder.message.setText(models.get(position).getDesc());
       holder.imgView.setImageResource(models.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        // it will return size of element
        return models.size();

    }
}
