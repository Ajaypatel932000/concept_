package com.example.concept_git.show_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concept_git.Avalible_test;
import com.example.concept_git.R;
import com.example.concept_git.test_subject.ListItems;

import java.util.List;

public class MyAdapter_test_list  extends RecyclerView.Adapter<MyAdapter_test_list.ViewHolder> {

    List<ListItems_test> listItems;
    private Context context;

    public MyAdapter_test_list(List<ListItems_test> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_card,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListItems_test items=listItems.get(position);
        holder.textViewId.setText(items.getId());
        holder.textViewName.setText(items.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent=new Intent(context, com.example.concept_git.launch_test.class);
                        Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
                        intent.putExtra("test_id",items.getId());
                        intent.putExtra("subject_id", Avalible_test.subject);
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
        public TextView textViewId, textViewName;
        RelativeLayout relativeLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.tv1);
            textViewId.setVisibility(View.GONE);
            textViewName = itemView.findViewById(R.id.tv2);
            relativeLayout = itemView.findViewById(R.id.relative_layout_test);

        }
    }

}
