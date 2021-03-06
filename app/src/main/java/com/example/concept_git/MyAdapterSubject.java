package com.example.concept_git;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterSubject extends RecyclerView.Adapter<MyHolderSubject> {
   Context c;
   ArrayList<Subject_Model> models;

    public MyAdapterSubject(Context c, ArrayList<Subject_Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolderSubject onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card,null);

        return new  MyHolderSubject(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderSubject holder, int position) {


        holder.id_tv.setText(models.get(position).getId());
        holder.name_tv.setText(models.get(position).getName());



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClicListener(View v, int position) {

                String id=models.get(position).getId();
                String nm=models.get(position).getName();
                Intent intent=new Intent(c,chapter.class);

                intent.putExtra("id_key",id);
                intent.putExtra("name_key",nm);
                c.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
