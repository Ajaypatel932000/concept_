package com.example.concept_git.result_subject;

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
import com.example.concept_git.result_graph;
import com.example.concept_git.test_subject.ListItems;
import com.example.concept_git.test_subject.MyAdapter_Sub;

import java.util.List;

public class AdapterResult extends RecyclerView.Adapter<AdapterResult.ViewHolder> {
    List<GetSet> list;
    private Context context;

    public AdapterResult(List<GetSet> list, Context context) {
        this.list = list;
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

        final GetSet items=list.get(position);
        holder.textViewId.setText(items.getId());
        holder.textViewName.setText(items.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,result_graph.class);
               // Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
                // you can fetch the batchid pass here
                intent.putExtra("test_id",items.getId());
                //intent.putExtra("subject_id",items.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewId, textViewName;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.tv1);
            textViewId.setVisibility(View.GONE);
            textViewName = itemView.findViewById(R.id.tv2);
            relativeLayout = itemView.findViewById(R.id.relative_layout);

        }
    }
}
