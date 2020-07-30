package com.example.concept_git.test_subject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concept_git.Avalible_test;
import com.example.concept_git.R;

import java.util.List;

public class MyAdapter_Sub extends RecyclerView.Adapter<MyAdapter_Sub.ViewHolder> {
    List<ListItems> listItems;
    private Context context;
    public MyAdapter_Sub(List<ListItems> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v= LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_test_card,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItems items=listItems.get(position);
        holder.textViewId.setText(items.getId());
        holder.textViewName.setText(items.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Avalible_test.class);
                Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
                // you can fetch the batchid pass here
                intent.putExtra("batch_id","7");
                intent.putExtra("subject_id",items.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView textViewId,textViewName;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId=itemView.findViewById(R.id.tv1);
            textViewName=itemView.findViewById(R.id.tv2);
            relativeLayout=itemView.findViewById(R.id.relative_layout);


        }
}
}
